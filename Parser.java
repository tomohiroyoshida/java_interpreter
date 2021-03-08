class Parser {
  private Lexer lex; // 字句解析した結果
  private int tokenType; // 先読みしたトークンの種類

  // トークンの先読み+tokenTypeを上書きする(本では"getToken()")
  private void getNextToken() {
    if (lex.advance()) {
      tokenType = lex.token();
    } else {
      tokenType = TokenType.EOS; // 次のトークンが存在しない時はEOSを設定
    }
  }

  /** 構文木「プログラム」を返す */
  public JTCode parse(Lexer lexer) {
    JTCode code = null;
    lex = lexer;
    getNextToken(); // あらかじめトークンを先読みする
    try {
      code = program(); // 1
    } catch (Exception e) {
      e.printStackTrace();
    }
    return code;
  }

  /**
   * simpleExpr()を返す
   * ";"がきたら終わり
   */
  private JTCode program() throws Exception {
    JTCode code = expr(); //2
    if (code != null) {
      switch (tokenType) {
      case ';':
        break;
      default:
        throw new Exception("文法エラーです");
      }
    }
    return code;
  }

  /**
   * 「単純式」を返す
   */
  private JTCode expr() throws Exception {
    JTCode code = simpleExpr(); // 3
    switch (tokenType) {
    case '<':
    case '>':
    case TokenType.EQ: // '=='
    case TokenType.NE: // '!='
    case TokenType.LE: // '<='
    case TokenType.GE: // '>='
      code = expr2(code);
      break;
    }
    return code;
  }

  /* '==' '!=' '<=' '>='の処理 */
  private JTBinExpr expr2(JTCode code) throws Exception {
    JTBinExpr result = null;
    while ((tokenType == '<') || (tokenType == '>') || (tokenType == TokenType.EQ) || (tokenType == TokenType.NE)
        || (tokenType == TokenType.LE) || (tokenType == TokenType.GE)) {
      int op = tokenType;
      getNextToken();
      JTCode code2 = simpleExpr();
      if (result == null) {
        result = new JTBinExpr(op, code, code2);
      } else {
        result = new JTBinExpr(op, result, code2);
      }
    }
    return result;
  }

  /**
   * 「項(term)」と「 "+" "-"」を読み込む
   * トークンの集合を構文木にした「式」を返す
   */
  private JTCode simpleExpr() throws Exception {
    JTCode code = term(); // 4
    switch (tokenType) {
    case '+':
    case '-':
    case TokenType.OR:
      code = simpleExpr2(code);
      break;
    }
    return code;
  }

  /**
   *  "[('+'|'-') 項]*" の処理。('+'|'-' 項)を0回以上繰り返し読み込む)
   * @param code
   * @return
   * @throws Exception
   */
  private JTBinExpr simpleExpr2(JTCode code) throws Exception {
    JTBinExpr result = null;
    while ((tokenType == '+') || (tokenType == '-') || (tokenType == TokenType.OR)) {
      int op = tokenType;
      getNextToken();
      JTCode code2 = term();
      if (result == null) {
        result = new JTBinExpr(op, code, code2);
      } else {
        result = new JTBinExpr(op, result, code2);
      }
    }
    return result;
  }

  /**
   * 「因子(factor)」と「"*" "/"」を読み込む
   * @return 「項」を返す
   * @throws Exception
   */
  private JTCode term() throws Exception {
    JTCode code = factor(); // 5
    switch (tokenType) {
    case ('*'):
    case ('/'):
    case TokenType.AND: // '&&'
      code = term2(code);
      break;
    }
    return code;
  }

  /**
   * "[('*'|'/') 項]*" の処理。('*'|'/' 項)を0回以上繰り返し読み込む)
   * @param JTCode
   * @return
   */
  private JTCode term2(JTCode code) throws Exception {
    JTBinExpr result = null;
    while ((tokenType == '*') || (tokenType == '/') || (tokenType == TokenType.AND)) {
      int operator = tokenType;
      getNextToken();
      JTCode code2 = term();
      if (result == null) {
        result = new JTBinExpr(operator, code, code2);
      } else {
        result = new JTBinExpr(operator, result, code2);
      }
    }
    return result;
  }

  /**
   * 文の終わりか数値かを読み込む
   * @return 「因子」を返す
   * @throws Exception
   */
  private JTCode factor() throws Exception { // 6
    JTCode code = null;
    switch (tokenType) {
    case TokenType.EOS:
      break;
    case TokenType.INT:
      code = new JTInt((Integer) lex.value());
      getNextToken();
      break;
    case '-':
      getNextToken();
      code = new JTMinus(factor());
      break;
    case '(':
      getNextToken();
      code = simpleExpr();
      if (tokenType != ')') {
        throw new Exception("文法エラー: 対応するカッコがありません。");
      }
      getNextToken();
      break;
    case TokenType.SYMBOL:
      JTSymbol sym = (JTSymbol) lex.value();
      getNextToken();
      if (tokenType == '=') {
        getNextToken();
        code = new JTAssign(sym, simpleExpr());
      } else {
        code = sym;
      }
      break;
    case TokenType.STRING:
      code = new JTString((String) lex.value());
      getNextToken();
      break;
    case TokenType.TRUE:
      code = JTBool.True;
      getNextToken();
      break;
    case TokenType.FALSE:
      code = JTBool.False;
      getNextToken();
      break;
    case '!':
      getNextToken();
      code = new JTNot(factor());
      break;
    default:
      throw new Exception("文法エラーです");
    }
    return code;
  }
}
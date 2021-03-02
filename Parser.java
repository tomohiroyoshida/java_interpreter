class Parser {
  private Lexer lex; // 字句解析した結果
  private int tokenType; // 先読みしたトークンの種類

  // トークンの先読み+ tokenType を上書きする(本では"getToken()")
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
   * expr()を返す
   * ";"がきたら終わり
   */
  private JTCode program() throws Exception {
    JTCode code = expr(); //2
    if (code != null) {
      switch (tokenType) {
        case ';': // ";" がきたら文の終わり
          break;
        default:
          throw new Exception("文法エラーです");
      }
    }
    return code;
  }

  /**
   * 「項(term)」と「 "+" "-"」を読み込む
   * トークンの集合を構文木にした「式」を返す
   */
  private JTCode expr() throws Exception {
    JTCode code = term(); // 3
    switch (tokenType) {
      case '+':
      case '-':
        code = expr2(code);
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
  private JTBinExpr expr2(JTCode code) throws Exception {
    JTBinExpr result = null;
    while ((tokenType == '+') || (tokenType == '-')) {
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
    JTCode code = factor(); // 4
    switch (tokenType) {
      case ('*'):
      case ('/'):
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
    while ((tokenType == '*') || (tokenType == '/')) {
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
  private JTCode factor() throws Exception { // 5
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
        code = expr();
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
          code = new JTAssign(sym, expr());
        } else {
          code = sym;
        }
        break;
      default:
        throw new Exception("文法エラーです");
    }
    return code;
  }
}
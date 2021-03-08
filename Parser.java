import java.util.ArrayList;

class Parser {
  private Lexer lex; // 字句解析した結果
  private int tokenType; // 先読みしたトークンの種類

  // トークンの先読み+tokenTypeを上書きする(本では"getToken()")
  private void getNextToken() {
    if (lex.advance()) {
      tokenType = lex.token();
      System.out.println("tokenType: " + tokenType);
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
   * を返す
   * ";"がきたら終わり
   */
  private JTCode program() throws Exception {
    JTCode code = stmt(); //2
    if (code != null) {
      switch (tokenType) {
      case ';':
        break;
      default:
        throw new Exception("program() で文法エラーですわよ！");
      }
    }
    return code;
  }

  /**
   * 「文」(statement) を返す
   */
  private JTCode stmt() throws Exception {
    JTCode code = null;
    switch (tokenType) {
    case TokenType.IF:
      code = if_stmt();
      break;
    case TokenType.WHILE:
      code = while_stmt();
      break;
    case '{':
      code = block();
      break;
    default:
      code = expr();
      break;
    }
    return code;
  }

  /**
   * 「if文」(if_statement)を返す
   */
  private JTCode if_stmt() throws Exception {
    getNextToken(); // 'if' 自体をスキップ
    if (tokenType != '(')
      throw new Exception("文法エラーです (");
    getNextToken(); // '(' 自体をスキップ
    JTCode cond = expr();
    if (tokenType != ')')
      throw new Exception("文法エラーです )");
    getNextToken(); // ')' 自体をスキップ
    JTCode st1 = stmt();
    JTCode st2 = null;
    // もし else があれば
    if (tokenType == TokenType.ELSE) {
      getNextToken(); // 'else' 自体をスキップ
      st2 = stmt();
    }
    return new JTIf(cond, st1, st2);
  }

  /**
   * while
   */
  private JTCode while_stmt() throws Exception {
    getNextToken();
    if (tokenType != '(')
      throw new Exception("while の次に'('がありません");
    getNextToken();
    JTCode cond = expr();
    if (tokenType != ')')
      throw new Exception("while の次に')'がありません");
    JTCode st = stmt();
    return new JTWhile(cond, st);
  }

  private JTCode block() throws Exception {
    ArrayList<JTCode> list = null;
    getNextToken();
    while (tokenType != '}') {
      JTCode c = stmt();
      if (tokenType != ';')
        throw new Exception("block() で文法エラーですわよ！");
      getNextToken(); // skip ';'
      if (list == null)
        list = new ArrayList<JTCode>();
      list.add(c);
    }
    getNextToken(); // skip '}'
    return new JTBlock(list);
  }

  /**
   * 「式」を返す
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

  /**
   * 式
   * '==' '!=' '<=' '>='の処理
   */
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
   * 「単純式」と「 "+" "-"」を読み込む
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
      throw new Exception("factor() で文法エラーです！！");
    }
    return code;
  }
}
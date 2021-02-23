class Parser {
  private Lexer lex;

  // expr()を返すだけ
  private JTCode program() throws Exception {
    return expr();
  }

  // 数値を構文木にしたものを返す
  private JTCode expr() throws Exception {
    JTCode code = null;
    if (lex.advance()) {
      int token = lex.token();
      switch (token) {
        case TokenType.INT: // token が数値ならOK
          code = new JTInt((Integer) lex.value());
          break;
        default:
          throw new Exception("文法エラーです");
      }
    }
    return code;
  }

  //  構文木を返す
  public JTCode parse(Lexer lexer) {
    JTCode code = null;
    lex = lexer;

    try {
      code = program();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return code;
  }
}
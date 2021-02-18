package chap_2;

import java.io.Reader;

public class Lexer {
  private LexerReader reader;
  private int tok;
  private Object val;

  /**
   * reader はトークンの読み込み元
   */
  public Lexer(Reader r) {
    reader = new LexerReader(r);
  }

  private void lexDigit() throws Exception {
    int num = 0;
    while (true) {
      int c = reader.read();
      if (c < 0)
        break;
      if (!Character.isDigit((char) c)) {
        reader.unread(c);
        break;
      }
      num = (num * 10) + (c - '0');
    }
    val = Integer.valueOf(num);
  }

  private void skipWhiteSpace() throws Exception {
    int c = reader.read();
    while ((c != -1) && Character.isWhitespace((char) c)) {
      c = reader.read();
    }
    reader.unread(c);
  }

  /**
   * 次のトークンへ進む
   * 返り値: 次のトークンがあればtrue なければ false
   */
  public boolean advance() {
    try {
      skipWhiteSpace();
      int c = reader.read();
      if (c < 0)
        return false;
      if (Character.isDigit((char) c)) {
        reader.unread(c);
        lexDigit();
        tok = TokenType.INT;
      } else {
        throw new Exception("数字じゃないです！");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 現在のトークンの種類を返す
   */
  public int token() {
    return tok;

  }

  /**
   * 現在のトークンの値を返す
   */
  public Object value() {
    return val;
  }
}

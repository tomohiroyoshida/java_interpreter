import java.io.Reader;

public class Lexer {
  private LexerReader reader;
  private int tokenType;
  private Object val;

  /**
   * reader はトークンの読み込み元
   */
  public Lexer(Reader r) {
    reader = new LexerReader(r);
  }

  // 入力された数値を読み込む
  private void lexDigit() throws Exception {
    int num = 0;
    while (true) {
      int c = reader.read();
      if (c < 0)
        break;
      // 数値でないならば
      if (!Character.isDigit((char) c)) {
        reader.unread(c);
        break;
      }
      // 数値ならば桁を一つ増やしてcを足す。(c - '0')は string -> number 型への変換
      num = (num * 10) + (c - '0');
    }
    val = Integer.valueOf(num);
  }

  // シンボルを読み込む
  private void lexSymbol() throws Exception {
    tokenType = TokenType.SYMBOL;
    StringBuffer buf = new StringBuffer();
    while (true) {
      int c = reader.read();
      if (c < 0)
        throw new Exception("ファイルの終わりに達しました。");
      if (!Character.isJavaIdentifierPart(c)) {
        reader.unread(c);
        break;
      }
      buf.append((char) c);
    }
    String s = buf.toString();
    val = JTSymbol.intern(s);
  }

  // 空白をスキップ
  private void skipWhiteSpace() throws Exception {
    int c = reader.read();
    // 入力の最後尾ではないかつ空白文字の場合
    while ((c != -1) && Character.isWhitespace((char) c))
      c = reader.read();
    reader.unread(c);
  }

  // 1行コメントをスキップ
  public void skipLineComment() throws Exception {
    int c;
    // 次の文字が改行文字('\n')、つまり行末になるまでスキップし続ける
    while ((c = reader.read()) != '\n') {
      if (c < 0)
        throw new Exception("コメント中にファイルの終端に達しました。");
    }
    reader.unread(c);
  }

  // 複数行コメントをスキップ
  public void skipComment() throws Exception {
    int c = '\n';
    while (true) {
      c = reader.read();
      if (c < 0)
        throw new Exception("コメント中にファイルの終端に達しました。");
      if (c == '*') {
        c = reader.read();
        if (c == '/')
          break;
      }
    }
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
      switch (c) {
        // c が "; + - * /" の時は文字コードがそのままトークンの種類を表す
        case ';':
        case '+':
        case '-':
        case '*':
        case '(':
        case ')':
        case '=':
          tokenType = c;
          break;
        case '/':
          c = reader.read();
          // 次の文字が「/」の場合は１行コメントとして読み飛ばす
          if (c == '/') {
            skipLineComment();
            return advance();
          } else if (c == '*') {
            skipComment();
            return advance();
          }
          // それ以外は普通の演算子「/」として処理する
          else {
            reader.unread(c);
            tokenType = '=';
          }
          break;
        default:
          // c が数値の時はトークンの種類が "TokenType.INT"(257)
          if (Character.isDigit((char) c)) {
            reader.unread(c);
            lexDigit();
            tokenType = TokenType.INT;
          }
          // c がシンボルの時
          else if (Character.isJavaIdentifierStart((char) c)) {
            reader.unread(c);
            lexSymbol();
          } else
            throw new Exception("数字じゃない、または文字が正しくないです！");
          break;
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
    return tokenType;
  }

  /**
   * 現在のトークンの値を返す
   */
  public Object value() {
    return val;
  }
}

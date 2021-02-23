
import java.io.IOException;
import java.io.Reader;

class LexerReader {
  private Reader reader;
  private boolean unget_p = false;
  private int ch;

  public LexerReader(Reader r) {
    reader = r;
  }

  public int read() throws IOException {
    if (unget_p) {
      unget_p = false;
    } else {
      ch = reader.read();
    }
    return ch;
  }

  public void unread(int c) {
    unget_p = true;
  }
}

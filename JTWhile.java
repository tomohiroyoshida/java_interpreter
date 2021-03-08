/** while クラス */
public class JTWhile extends JTCode {
  private JTCode cond;
  private JTCode body;

  public JTWhile(JTCode code1, JTCode code2) {
    cond = code1;
    body = code2;
  }

  public JTCode run() throws Exception {
    JTCode c = null;
    while (cond.run() != JTBool.False) {
      c = body.run();
    }
    return c;
  }
}

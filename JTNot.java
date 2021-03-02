public class JTNot extends JTCode {
  private JTCode code;

  public JTNot(JTCode c) {
    code = c;
  }

  public JTCode run() throws Exception {
    JTCode c = code.run();
    if (c.getClass() != JTBool.class)
      throw new Exception("真偽値以外のものに単行演算子 '!' を適用しようとしました！");
    JTBool p = (JTBool) c;
    // 否定した値を返す
    if (p.isTrue()) {
      return JTBool.False;
    } else {
      return JTBool.True;
    }
  }

}

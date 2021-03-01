public class JTMinus extends JTCode {
  private JTCode code;

  public JTMinus(JTCode c) {
    code = c;
  }

  public JTCode run() throws Exception {
    JTCode c = code.run();
    if (c.getClass() != JTInt.class) {
      throw new Exception("数値以外のものに単項演算子 - を適用しようとしました。");
    }
    JTInt i = (JTInt) c;
    return new JTInt(-i.getValue());
  }
}

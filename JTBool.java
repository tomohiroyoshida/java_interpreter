/** 真偽値を生成するクラス */
public class JTBool extends JTCode {
  private boolean p;
  // 真偽を表すオブジェクト
  public static JTBool True;
  public static JTBool False;

  static {
    True = new JTBool(true);
    False = new JTBool(false);
  }

  private JTBool(boolean b) {
    p = b;
  }

  public JTCode and(JTCode code2) throws Exception {
    if (p) {
      JTCode c2 = code2.run();
      if (c2 == JTBool.True)
        return JTBool.True;
      else
        return JTBool.False;
    } else {
      return JTBool.False;
    }
  }

  public JTCode or(JTCode code2) throws Exception {
    if (p)
      return JTBool.True;
    else {
      JTCode c2 = code2.run();
      if (c2 == JTBool.True)
        return JTBool.True;
      else
        return JTBool.False;
    }
  }

  public String toString() {
    return Boolean.toString(p);
  }

  public boolean isTrue() {
    return p;
  }
}

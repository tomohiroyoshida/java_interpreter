/** 真偽値を生成するクラス */
public class JTBool extends JTCode {
  private boolean bool;
  // 真偽を表すオブジェクト
  public static JTBool True;
  public static JTBool False;

  static {
    True = new JTBool(true);
    False = new JTBool(false);
  }

  private JTBool(boolean b) {
    bool = b;
  }

  public String toString() {
    return Boolean.toString(bool);
  }

  public boolean isTrue() {
    return bool;
  }
}

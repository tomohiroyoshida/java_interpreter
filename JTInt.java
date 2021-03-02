/** 数値の構文木を表すクラス */
public class JTInt extends JTCode {
  private int value; // 数値

  public JTInt(Integer integer) {
    value = integer.intValue();
  }

  public JTInt(int i) {
    value = i;
  }

  /** 記憶している数値(value)を返す */
  public int getValue() {
    return value;
  }

  /** 数値(value)を文字列に変換したものを返す */
  public String toString() {
    return Integer.toString(value);
  }

  /** 足し算 */
  public JTCode add(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class) {
      throw new Exception("数値以外のものをとしました。");
    }
    JTInt i = (JTInt) code; // JTCode -> JTInt 型にキャストする
    result = new JTInt(value + i.getValue());
    return result;
  }

  /** 引き算 */
  public JTCode sub(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class) {
      throw new Exception("数値以外のものを引こうとしました。");
    }
    JTInt i = (JTInt) code;
    result = new JTInt(value - i.getValue());
    return result;
  }

  /** 掛け算 */
  public JTCode multiply(JTCode code) throws Exception {
    JTCode result = null;
    // code が数値でなければエラー
    if (code.getClass() != JTInt.class) {
      throw new Exception("数値以外のものを掛けようとしました。");
    }
    JTInt i = (JTInt) code;
    result = new JTInt(value * i.getValue());
    return result;
  }

  /** 割り算 */
  public JTCode divide(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class) {
      throw new Exception("数値以外のものを割ろうとしました。");
    }
    JTInt i = (JTInt) code;
    result = new JTInt(value / i.getValue());
    return result;
  }

  public JTCode less(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class)
      throw new Exception("数値以外のものを比較しようとしました。");
    JTInt i = (JTInt) code;
    if (value < i.getValue()) {
      result = JTBool.True;
    } else {
      result = JTBool.False;
    }
    return result;
  }

  public JTCode le(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class)
      throw new Exception("数値以外のものを比較しようとしました。");
    JTInt i = (JTInt) code;
    if (value <= i.getValue()) {
      result = JTBool.True;
    } else {
      result = JTBool.False;
    }
    return result;
  }

  public JTCode greater(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class)
      throw new Exception("数値以外のものを比較しようとしました。");
    JTInt i = (JTInt) code;
    if (value > i.getValue()) {
      result = JTBool.True;
    } else {
      result = JTBool.False;
    }
    return result;
  }

  public JTCode ge(JTCode code) throws Exception {
    JTCode result = null;
    if (code.getClass() != JTInt.class)
      throw new Exception("数値以外のものを比較しようとしました。");
    JTInt i = (JTInt) code;
    if (value >= i.getValue()) {
      result = JTBool.True;
    } else {
      result = JTBool.False;
    }
    return result;
  }

  public boolean equals(JTCode code) throws Exception {
    // JTCode result = null;
    if (code.getClass() != JTInt.class)
      return false;
    JTInt i = (JTInt) code;
    return value == i.getValue();
  }

}

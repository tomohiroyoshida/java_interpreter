public class JTInt extends JTCode {
  private int value;

  public JTInt(Integer integer) {
    value = integer.intValue();
  }

  public int getValue() {
    return value;
  }

  public String toString() {
    return Integer.toString(value);
  }
}

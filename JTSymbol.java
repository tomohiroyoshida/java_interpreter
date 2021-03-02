import java.util.Hashtable;

public class JTSymbol extends JTCode {
  private static Hashtable table = new Hashtable(); // すでに作成済みのシンボル
  private String name;

  private JTSymbol(String s) {
    name = s;
    table.put(s, this); // 作成したシンボルは記憶しておく
  }

  /**
   * 1. すでに存在しているシンボルであれば記録していたものを返す
   * 2. 存在していなければ新しい井シンボルを作成したものを返す
  */
  public static JTSymbol intern(String s) {
    if (table.containsKey(s)) {
      return (JTSymbol) table.get(s);
    } else {
      return new JTSymbol(s);
    }
  }

  /** シンボルが指す変数の値を返す */
  public JTCode run() throws Exception {
    JTCode c = JoyToy.getSymbolValue(this);
    if (c == null) {
      throw new Exception("シンボル" + name + "は定義されていません。");
    }
    return c;
  }

  /** シンボルの名前(name)を返す */
  public String toString() {
    return name;
  }
}

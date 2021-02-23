public abstract class JTCode {
  /**
   * run()メソッドは実際の計算を行うメソッド。
   * クラスJTCodeでは単にthisを返すようにしておきます。
   * こうしておけば、後で出てくる数値を表すクラスなど、計算を必要としないいくつかのクラスで使い回せて便利です。
   */
  public JTCode run() throws Exception {
    return this;
  }
}
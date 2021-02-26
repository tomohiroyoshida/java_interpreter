/** 構文木を表す抽象クラス */
public abstract class JTCode {
  /**
   * run()メソッドは実際の計算を行うメソッド。
   * クラスJTCodeでは単にthisを返すようにしておきます。
   * こうしておけば、後で出てくる数値を表すクラスなど、計算を必要としないいくつかのクラスで使い回せて便利です。
   */
  public JTCode run() throws Exception {
    return this;
  }

  /**
   * これらのメソッドはクラス JTInt のためにあるため、
   * クラス JTCode で呼ぶとエラーを投げるようにする。
   */
  public JTCode add(JTCode code) throws Exception {
    throw new Exception("このオブジェクトに演算子'+'は使えません");
  }

  public JTCode sub(JTCode code) throws Exception {
    throw new Exception("このオブジェクトに演算子'-'は使えません");
  }

  public JTCode multiply(JTCode code) throws Exception {
    throw new Exception("このオブジェクトに演算子'*'は使えません");
  }

  public JTCode divide(JTCode code) throws Exception {
    throw new Exception("このオブジェクトに演算子'/'は使えません");
  }
}
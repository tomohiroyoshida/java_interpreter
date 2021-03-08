/**
 * シンボルへ式の割り当て(assign)を行うクラス
 * 代入演算子'='も二項演算子と考える
 * */
public class JTAssign extends JTBinExpr {
  public JTAssign(JTSymbol symbol, JTCode code) {
    super('=', symbol, code);
    /** ↑ここでやってること↑
    int op = '=';
    JTCode code1 = symbol;
    JTCode code2 = code;
    */
  }

  // 割り当て
  public JTCode run() throws Exception {
    JTSymbol sym = (JTSymbol) code1;
    JTCode c = code2.run();
    JoyToy.set(sym, c);
    return c;
  }
}

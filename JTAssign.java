/**
 * シンボルへ式の割り当て(assign)を行うクラス
 * 代入演算子'='も二項演算子と考える
 * */
public class JTAssign extends JTBinExpr {
  public JTAssign(JTSymbol symbol, JTCode code) {
    super('=', symbol, code);
    /** ↑ここでやってること↑
    int op = '=';
    JTCode codeLeft = symbol;
    JTCode codeRight = code;
    */
  }

  public JTCode run() throws Exception {
    JTSymbol sym = (JTSymbol) codeLeft;
    JTCode c = codeRight.run();
    JoyToy.set(sym, c);
    return c;
  }
}

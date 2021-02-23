/**
 * 加減乗除などの二項演算子のためのクラス
 * 「二項演算子」は'+'のような、両側に式や項が必要な演算子
 */
public class JTBinExpr extends JTCode {
  private int op; // 演算子(operator)の種類
  protected JTCode codeLeft; //左側の式
  protected JTCode codeRight; //右側の式

  public JTBinExpr(int operator, JTCode left, JTCode right) {
    op = operator;
    codeLeft = left;
    codeRight = right;
  }

  public JTCode run() throws Exception {
    JTCode left = codeLeft.run();
    JTCode right = codeRight.run();
    JTCode result = null;
    switch (op) {
      case '+':
        result = left.add(right);
        break;
      case '-':
        result = left.sub(right);
        break;
      case '*':
        result = left.multiply(right);
        break;
      case '/':
        result = left.divide(right);
    }
    return result;
  }

}

/**
 * 加減乗除などの二項演算子のためのクラス
 * 「二項演算子」は'+'のような、両側に式や項が必要な演算子
 */
public class JTBinExpr extends JTCode {
  private int op; // 演算子(operator)の種類
  protected JTCode code1; //左側の式
  protected JTCode code2; //右側の式

  public JTBinExpr(int operator, JTCode c1, JTCode c2) {
    op = operator;
    code1 = c1;
    code2 = c2;
  }

  public JTCode run() throws Exception {
    JTCode result = null;
    JTCode c1 = code1.run();

    if (op == TokenType.AND) {
      result = c1.add(code2);
    } else if (op == TokenType.OR) {
      result = c1.or(code2);
    } else {
      JTCode c2 = code2.run();
      switch (op) {
        case '+':
          result = c1.add(c2);
          break;
        case '-':
          result = c1.sub(c2);
          break;
        case '*':
          result = c1.multiply(c2);
          break;
        case '/':
          result = c1.divide(c2);
        case '<':
          result = c1.less(c2);
          break;
        case '>':
          result = c1.greater(c2);
          break;
        case TokenType.LE:
          result = c1.le(c2);
          break;
        case TokenType.GE:
          result = c1.ge(c2);
          break;
        case TokenType.EQ:
          if (c1.equals(c2)) {
            result = JTBool.True;
          } else {
            result = JTBool.False;
          }
        case TokenType.NE:
          if (c1.equals(c2)) {
            result = JTBool.False;
          } else {
            result = JTBool.True;
          }
      }
    }
    return result;
  }

}

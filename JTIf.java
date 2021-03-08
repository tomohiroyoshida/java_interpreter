/** if クラス */
public class JTIf extends JTCode {
  private JTCode cond; // 条件式
  private JTCode then_body; // 条件式が真のとき実行する文
  private JTCode else_body; // 条件式が偽のとき実行する文

  public JTIf(JTCode code1, JTCode code2, JTCode code3) {
    cond = code1;
    then_body = code2;
    else_body = code3;
  }

  public JTCode run() throws Exception {
    JTCode c;
    JTCode p = cond.run();
    if (p != JTBool.False)
      c = then_body;
    else
      c = else_body;
    if (c != null)
      c = c.run();
    return c;
  }
}

/** ブロック{}  */

import java.util.ArrayList;
import java.util.Iterator;

public class JTBlock extends JTCode {
  ArrayList<JTCode> list;

  JTBlock(ArrayList<JTCode> l) {
    list = l;
  }

  public JTCode run() throws Exception {
    JTCode code = JTBool.True;
    if (list != null) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        JTCode c = (JTCode) it.next();
        code = c.run();
      }
    }
    return code;
  }
}

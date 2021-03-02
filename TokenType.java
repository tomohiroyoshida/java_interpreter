public class TokenType {
  public static final int EOS = -1; // 文の終わり(End Of Sentence)を表す
  public static final int INT = 257; // 数値(int)型を表す
  public static final int SYMBOL = 258; // シンボル型を表す
  public static final int STRING = 259; // 文字列型を表す
  public static final int TRUE = 260; // 「真」を表す
  public static final int FALSE = 261; // 「偽」を表す
  public static final int EQ = 262; // '=='
  public static final int NE = 263; // '!='
  public static final int LE = 264; // '<='
  public static final int GE = 265; // '>='
  public static final int AND = 266; // '&&'
  public static final int OR = 267; // '||'
}

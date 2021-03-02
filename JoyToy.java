import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;

public class JoyToy {
  static void usage() {
    System.out.println("usage: Java JoyToy [source_filename]");
  }

  // 変数を記憶しておくHashtable
  public static Hashtable Globals = new Hashtable();

  // シンボルが存在するかどうかのフラグ
  public static boolean hasSymbol(JTSymbol sym) {
    return Globals.contains(sym);
  }

  // 変数の値を取り出す
  public static JTCode getSymbolValue(JTSymbol sym) {
    return (JTCode) Globals.get(sym);
  }

  // 変数に値をセットする
  public static void set(JTSymbol sym, JTCode code) {
    Globals.put(sym, code);
    // int i = 'i';
    // char cha = 'f';
    // // i = cha;
    // i = cha;
    // cha = i;
  }

  public static void main(String[] args) {
    boolean interactive = false; // 標準出力から読み込んでいるときは true;

    // 引数の数がおかしいときは使い方を表示して終了
    if (args.length >= 2) {
      usage();
      return;
    }
    try {
      BufferedReader in;

      // 引数がない時は、標準入力から読み込む
      if (args.length == 0) {
        in = new BufferedReader(new InputStreamReader(System.in));
        interactive = true;
      }
      // 引数で指定されたファイルから読み込む
      else {
        in = new BufferedReader(new FileReader(args[0]));
      }

      Lexer lex = new Lexer(in);
      Parser parser = new Parser();
      while (true) {
        //  標準出力から読み込んでいるときはプロンプトを表示
        if (interactive) {
          System.out.print("入力してね: ");
        }
        // 構文解析する
        JTCode code = (JTCode) parser.parse(lex);
        // プログラムの終わり
        if (code == null)
          break;
        System.out.println("結果：" + code.run().toString()); // 結果を文字列で表示
      }
      // 使い終わったストリームは閉じる
      in.close();
    } catch (FileNotFoundException e) {
      // エラー処理
      if (args.length > 0) {
        System.out.println("can't open file '" + args[0] + "'");
      } else {
        System.out.println("can't open file");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
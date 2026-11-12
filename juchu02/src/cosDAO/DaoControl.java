package cosDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cosDataPack.Juchu;
import cosDataPack.Kokyaku;
import cosDataPack.Shohin;
import cosDataPack.ShohinCategory;

/**
 * Daoコントロールクラス。
 * コントロール系クラスからDBをアクセスする際に呼び出されるクラス。
 * DBアクセスに関する全ての処理は、一旦Daoコントロールが受け付け、ここから各処理のDaoへ振り分ける。
 * @version 1.0
 */
public class DaoControl {
	/** DB接続Connectionオブジェクト */
	private static Connection con = null;
	/** DB接続用クラスのオブジェクト */
	private DaoConnectionManager manager;

	/**
	 * 引数なしコンストラクタ。
	 * DB接続用クラスのオブジェクトでDBのコネクション接続を行う
	 */
	public DaoControl() {
		super();
		try {
			//DB接続用オブジェクトでDBに接続する
			manager = DaoConnectionManager.getConnectionManager();
			//コネクションの取得
			con = manager.getConnection();
		} catch (ClassNotFoundException e) {
			System.out.println("ドライバーの登録エラー");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("getConnection データベースコネクションエラー");
			e.printStackTrace();
		}
	}

	/**
	 * DBコネクションのクローズをする。
	 */
	public void connectionClose() {
		try {
			if (!con.isClosed())
				//Connectionの解放
				con.close();
		} catch (SQLException e) {
			System.out.println("close データベース切断エラー");
			e.printStackTrace();
		}
	}

	/**
	 * 顧客情報を顧客テーブルに登録する。
	 * @param data 顧客情報
	 * @return 顧客情報
	 */
	public Kokyaku insertData(Kokyaku data) {
		DaoKokyaku daoK = new DaoHoujin(con); //顧客Dao
		//引数のdataを渡して、テーブルにインサートする
		data = daoK.insertData(data);
		return data;
	}

	/**
	 * 顧客情報を取得する。
	 * @param kokyakuCD 顧客コード
	 * @param password パスワード
	 * @param className クラス名
	 * @return 顧客情報
	 */
	public Kokyaku selectKokyaku(
			String kokyakuCD,
			String password,
			String className) {
		DaoKokyaku daoK = new DaoHoujin(con); //顧客Dao
		//引数の顧客コードとパスワードを渡して、テーブルからセレクトする
		return daoK.selectData(kokyakuCD, password);
	}

	/**
	 * 県コードと県名のマッピングリストを取得する。
	 * @return 県コードと県名のマッピングリスト
	 */
	public Map<String, String> getPrefecture() {
		//県コードと県名のマップ表
		Map<String, String> kenMap = new TreeMap<String, String>();
		kenMap.put( "01", "北海道" );
		kenMap.put( "02", "青森県" );
		kenMap.put( "03", "岩手県" );
		kenMap.put( "04", "宮城県" );
		kenMap.put( "05", "秋田県" );
		kenMap.put( "06", "山形県" );
		kenMap.put( "07", "福島県" );
		kenMap.put( "08", "茨城県" );
		kenMap.put( "09", "栃木県" );
		kenMap.put( "10", "群馬県" );
		kenMap.put( "11", "埼玉県" );
		kenMap.put( "12", "千葉県" );
		kenMap.put( "13", "東京都" );
		kenMap.put( "14", "神奈川県" );
		kenMap.put( "15", "新潟県" );
		kenMap.put( "16", "富山県" );
		kenMap.put( "17", "石川県" );
		kenMap.put( "18", "福井県" );
		kenMap.put( "19", "山梨県" );
		kenMap.put( "20", "長野県" );
		kenMap.put( "21", "岐阜県" );
		kenMap.put( "22", "静岡県" );
		kenMap.put( "23", "愛知県" );
		kenMap.put( "24", "三重県" );
		kenMap.put( "25", "滋賀県" );
		kenMap.put( "26", "京都府" );
		kenMap.put( "27", "大阪府" );
		kenMap.put( "28", "兵庫県" );
		kenMap.put( "29", "奈良県" );
		kenMap.put( "30", "和歌山県" );
		kenMap.put( "31", "鳥取県" );
		kenMap.put( "32", "島根県" );
		kenMap.put( "33", "岡山県" );
		kenMap.put( "34", "広島県" );
		kenMap.put( "35", "山口県" );
		kenMap.put( "36", "徳島県" );
		kenMap.put( "37", "香川県" );
		kenMap.put( "38", "愛媛県" );
		kenMap.put( "39", "高知県" );
		kenMap.put( "40", "福岡県" );
		kenMap.put( "41", "佐賀県" );
		kenMap.put( "42", "長崎県" );
		kenMap.put( "43", "熊本県" );
		kenMap.put( "44", "大分県" );
		kenMap.put( "45", "宮崎県" );
		kenMap.put( "46", "鹿児島県" );
		kenMap.put( "47", "沖縄県" );
		//マッピングリストをリターン
		return kenMap;
	}

	/**
	 * 商品カテゴリリストを取得する。
	 * @return 商品カテゴリリスト
	 */
	public static ArrayList<ShohinCategory> selectShohinCategory() {
		ArrayList<ShohinCategory> list; //リストオブジェクト
		//商品DAOの作成
		DaoShohin daoS = new DaoShohin(con);
		//商品DAOより商品カテゴリリストを取得
		list = daoS.selectData();
		return list;
	}

	/**
	 * カテゴリ別商品リストを取得する。
	 * @param code 商品カテゴリコード
	 * @param eigyoshoCD 営業所コード
	 * @return カテゴリ別商品リスト
	 */
	public static ArrayList<Shohin> selectShohin(String code,
			String eigyoshoCD) {
		ArrayList<Shohin> list = null; //リストオブジェクト
		DaoShohin daoS = null; //商品DAO
		if (code.length() == 4) {
			//コードが４ケタ⇒商品サブカテゴリコード
			//商品サブカテゴリ別商品リスト作成
			//商品Daoを作成する
			daoS = new DaoShohin(con);
		} else if (code.length() == 14) {
			//コードが１４ケタ⇒商品コード
			//商品データ取得（1件）
			if (code.substring(0, 2).equals("01")) {
				//頭２ケタが”01”⇒文具テーブルをアクセスするDaoBunguを作成
				daoS = new DaoBungu(con);
			} else if (code.substring(0, 2).equals("02")) {
				//頭２ケタが”02”⇒書籍テーブルをアクセスするDaoBookを作成
				daoS = new DaoBook(con);
			}
		}
		//selectData()を実行して商品リストを取得する
		list = daoS.selectData(code, eigyoshoCD);
		//リストをリターンする
		return list;
	}

	/**
	 * 受注データを登録する。
	 * @param data 受注デー タ
	 * @return 受注データ
	 */
	public static Juchu insertJuchu(Juchu data) {
		DaoJuchu daoJ = new DaoJuchu(con); //受注Dao
		//受注Daoのインサートメソッド呼び出し
		data = daoJ.insertData(data);
		return data;
	}
}
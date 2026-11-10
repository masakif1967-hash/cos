package cosDAO;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Dao接続マネージャクラス。
 * DBのコネクション接続をするクラス。DB接続に必要なパラメタの設定やDB接続関連の処理を行う。
 * @version 1.0
 */
public class DaoConnectionManager {
	/** DBのURL */
	private static final String URL = "jdbc:h2:tcp://localhost/~/coscommon01";
	/** ユーザー名 */
	private static final String USER = "sa";
	/** パスワード */
	private static final String PASSWORD = "pass";
	/** このクラスのオブジェクト */
	private static DaoConnectionManager instance = null;

	/**
	 * 引数なしコンストラクタ。
	 * @throws ClassNotFoundException クラスロード例外
	 */
	private DaoConnectionManager()
			throws ClassNotFoundException {
	}

	/**
	 * 自オブジェクト（DaoConnectionManager）を生成する。
	 * 当該クラスのオブジェクトを生成するメソッドをクラスメソッドとして定義する。
	 * 利用する側はこのメソッドを呼び出すことで、ドライバの登録をする。
	 * @throws ClassNotFoundException クラスロード例外
	 * @return Dao接続マネージャインスタンス
	 */
	public static DaoConnectionManager getConnectionManager()
			throws ClassNotFoundException {
		if (instance == null) {
			//当クラスのオブジェクトを生成することで、
			//コンストラクタが呼び出され、ドライバが登録される
			instance = new DaoConnectionManager();
		}
		return instance;
	}

	/**
	 * DBに接続する。
	 * DB接続に必要な、URL、ユーザー、パスワードはここで設定する。
	 * @throws SQLException SQL例外
	 * @return DB接続コネクション
	 */
	public synchronized java.sql.Connection getConnection()
			throws SQLException {
		//データベース接続
		java.sql.Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
		//コネクションを返す
		return con;
	}
}

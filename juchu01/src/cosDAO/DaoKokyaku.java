package cosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cosDataPack.Kokyaku;

/**
 * 顧客Daoクラス。
 * 顧客テーブルをアクセスするDAOのスーパークラス。
 * @version 1.0
 */
public abstract class DaoKokyaku {
	/** DB接続コネクション */
	protected Connection con;
	/** SQL送信用Statement */
	protected Statement stmt = null;

	/**
	 * 引数なしコンストラクタ。
	 */
	public DaoKokyaku() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * コネクションをセットする。
	 * @param con DB接続コネクション
	 */
	public DaoKokyaku(Connection con) {
		//コネクションをセットする
		this.con = con;
	}

	/**
	 * DB接続コネクションを取得する。
	 * @return DB接続コネクション
	 */
	public Connection getCon() {
		return con;
	}

	/**
	 * DB接続コネクションを設定する。
	 * @param con DB接続コネクション
	 */
	public void setCon(Connection con) {
		this.con = con;
	}

	/**
	 * SQL送信用Statementを取得する。
	 * @return SQL送信用Statement
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * SQL送信用Statementを設定する。
	 * @param stmt SQL送信用Statement
	 */
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	/**
	 * Statementを作成する。
	 */
	public void creStatement() {
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("CreateStatement エラー");
			e.printStackTrace();
		}
	}

	/**
	 * Statementをクローズする。
	 */
	public void closeStatement() {
		try {
			stmt.close();
		} catch (SQLException e) {
			System.out.println("CloseStatement エラー");
			e.printStackTrace();
		}
	}

	/**
	 * 現在の顧客コードを取得する。
	 * @param tableName テーブル名
	 * @return 現在の顧客コード
	 */
	protected String getKokyakuCD(String tableName) {
		String query; //SQL格納の文字列
		String curCD = null; //現在の顧客コード
		ResultSet rs = null; //ResultSet
		//CTR_CURCODE_TABLEから顧客コードの現在値を取得するSQL文
		query = "SELECT * FROM CTR_CURCODE_TABLE WHERE DATANAME = '" + tableName + "'";
		try {
			//SQLの実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//現在のコードを取得する
				curCD = rs.getString("CURCODE");
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//顧客コードをリターンする
		return curCD;
	}

	/**
	 * 県コードから営業所コードを取得する。
	 * @param kenCD 県コード
	 * @return 営業所コード
	 */
	protected String getEigyoushoCD(String kenCD) {
		String query; //SQL格納の文字列
		String eigyoushoCD = null; //営業所コード
		ResultSet rs = null; //ResultSet
		//県コードで営業所コードを取得するSQL
		query = "SELECT * FROM SHUKKACHIKU_TABLE WHERE KENCD = '" + kenCD + "'";
		try {
			//SQLの実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//営業所コードを取得する
				eigyoushoCD = rs.getString("EIGYOUSHOCD");
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//営業所コードをリターンする
		return eigyoushoCD;
	}

	/**
	 * 同じ顧客コードとパスワードが登録されているかチェックする。
	 * @param kokyakuCD 顧客コード
	 * @param password パスワード
	 * @return 同じ顧客コードとパスワードがあれば、trueを返す。なければ、falseを返す 。
	 */
	protected boolean checkCDAndPass(String kokyakuCD,
			String password) {
		String query; //SQL文格納文字列
		ResultSet rs = null; //ResultSet
		//顧客コードとパスワードが同じデータを抽出するSQL文
		query = "SELECT * FROM KOKYAKUPASS_TABLE WHERE KOKYAKUCD = '" + kokyakuCD + "' AND PASSWORD = '" + password
				+ "'";
		try {
			//SQLの実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//オブジェクトを取得できれば、trueを返す
				return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//同じ顧客コードとパスワードがなければ、falseを返す
		return false;
	}

	/**
	 * 郵便番号を分割する。
	 * @param num 郵便番号
	 * @return 分割した郵便番号
	 */
	protected String[] zipSplit(String num) {
		String[] data = new String[2]; //分割した郵便番号を格納する配列
		//上3文字を取得して格納
		data[0] = new String(num.substring(0, 3));
		//下4文字を取得して格納
		data[1] = new String(num.substring(4));
		//格納された配列を返す
		return data;
	}

	/**
	 * 電話番号、FAX番号を分割する。
	 * @param num 電話番号、FAX番号
	 * @return 分割した電話番号、FAX番号
	 */
	protected String[] telSplit(String num) {
		String[] data = new String[3]; //分割したTEL、FAXを格納する配列
		int frm = 0; //from（初期値：先頭）
		int end; //end
		//先頭から最初のハイフンをendに入れる
		end = num.indexOf('-', frm);
		//先頭からend（最初のハイフン）までで分割する
		data[0] = new String(num.substring(frm, end));
		//最初のハイフンの次の文字をfromに入れる
		frm = end + 1;
		//2つ目のハイフンをendに入れる
		end = num.indexOf('-', frm);
		//最初のハイフンの次の文字から2つ目のハイフンまでで分割する
		data[1] = new String(num.substring(frm, end));
		//2つ目のハイフンの次の文字をfromに入れる
		frm = end + 1;
		//2つ目のハイフン以降を分割する
		data[2] = new String(num.substring(frm));
		//分割された配列を返す
		return data;
	}

	/**
	 * 顧客情報を登録する。
	 * @param data 顧客情報
	 * @return 顧客情報
	 */
	abstract public Kokyaku insertData(Kokyaku data);

	/**
	 * 同じメールアドレスが登録されていないかチェックする。
	 * @param mail メールアドレス
	 * @return 同じメールアドレスが登録されていれば、trueを返す。登録されていなければ、falseを返す。
	 */
	abstract protected boolean checkMail(String mail);

	/**
	 * 顧客情報を取得する。
	 * @param kokyakuCD 顧客コード
	 * @param password パスワード
	 * @return 顧客情報
	 */
	abstract public Kokyaku selectData(String kokyakuCD, String password);
}

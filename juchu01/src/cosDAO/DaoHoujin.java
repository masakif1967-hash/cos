package cosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cosDataPack.Houjin;
import cosDataPack.Kokyaku;

/**
 * 顧客法人Daoクラス。
 * 顧客法人データをアクセスするクラス。主にKOKYAKUHOUJIN_TABLEをアクセス。
 * @version 1.0
 */
public class DaoHoujin extends DaoKokyaku {
	/** 顧客法人の与信限度額 */
	private final int YOSHIN_HOUJIN = 3000000;

	/**
	 * 引数なしコンストラクタ。
	 */
	public DaoHoujin() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * @param con DB接続コネクション
	 */
	public DaoHoujin(Connection con) {
		//コネクションをセットする
		super(con);
	}

	/**
	 * 顧客情報を登録する。
	 * @param data 顧客情報
	 */
	public Kokyaku insertData(Kokyaku data) {
		String kokyakuCD; //顧客コード
		String kokyakuName; //顧客名
		String adress; //住所
		String zip; //郵便番号
		String tell; //TEL
		String fax; //FAX
		String tourokuD; //ユーザー登録日
		String mail; //メールアドレス
		int yoshin = 0; //与信限度額
		String eigyoushoCD; //営業所コード
		int nextKokyakuCD; //次回登録時の顧客コード
		String[] query = new String[3]; //SQL文を格納する配列
		//statementのcreate
		creStatement();
		try {
			//自動コミットの解除
			con.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("setAutoCommit エラー");
			e.printStackTrace();
		}
		//コントロールテーブルから顧客コードを取得
		kokyakuCD = getKokyakuCD("KOKYAKUHOUJIN_TABLE");
		//次の顧客コードを設定
		nextKokyakuCD = Integer.parseInt(kokyakuCD) + 1;
		//県コードから担当営業所コードを取得
		eigyoushoCD = getEigyoushoCD(data.getKenCD());
		//住所（県名＋住所１）の設定
		adress = data.getKenName() + data.getAddress1();
		//郵便番号（郵便番号１－郵便番号２）の設定
		zip = data.getZip1() + '-' + data.getZip2();
		//電話番号（電話番号１－電話番号２－電話番号３）の設定
		tell = data.getTel1() + '-' + data.getTel2() + '-' + data.getTel3();
		//FAX番号（FAX番号１－FAX番号２－FAX番号３）設定
		fax = data.getFax1() + '-' + data.getFax2() + '-' + data.getFax3();
		//登録日の設定（処理日を取得）
		tourokuD = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//顧客名の設定
		kokyakuName = data.getKokyakuName();
		//メールアドレスの設定
		mail = data.getMail();
		//与信限度額の設定
		yoshin = YOSHIN_HOUJIN;
		//既に登録済みの場合、顧客エラーフラグに「１」をセット
		if (checkMail(mail)) data.setKokyakuErrFLG(1);
		//SQL文の作成
		//顧客法人テーブルINSERT文のVALUES部分の作成
		String values1 = "'" + kokyakuCD + "', '" + kokyakuName + "', '" + data.getKokyakuKana()
				+ "', '" + zip + "', '" + data.getKenCD() + "', '" + adress + "', '" + data.getAddress2()
				+ "', '" + tell + "', '" + fax + "', '" + data.getMail() + "', " + data.getUrikake_zan()
				+ ", " + yoshin + ", '" + eigyoushoCD + "', '" + tourokuD
				+ "', '" + ((Houjin) data).getTantouName() + "', '" + ((Houjin) data).getTantoubusho() + "', '"
				+ ((Houjin) data).getGyoushu() + "'";
		//顧客パスワードテーブルINSERT文のVALUES部分の作成
		String values2 = "'" + kokyakuCD + "', '" + data.getPassword() + "'";
		//コントロールテーブル（顧客コード現在値）を更新するSQL
		query[0] = "UPDATE CTR_CURCODE_TABLE SET CURCODE = '" + String.valueOf(nextKokyakuCD)
				+ "' WHERE DATANAME = 'KOKYAKUHOUJIN_TABLE'";
		//顧客法人テーブルに追加するSQL
		query[1] = "INSERT INTO KOKYAKUHOUJIN_TABLE (KOKYAKUCD, KOKYAKUNAME, KOKYAKUKANA, ZIP, KENCD, " +
				"ADDRESS1, ADDRESS2, TEL, FAX, MAIL, URIKAKE_ZAN, YOSIN_GENDO, EIGYOUSHOCD, TOUROKU_D, " +
				"TANTOUNAME, TANTOUBUSHO, GYOUSHU) VALUES (" + values1 + ")";
		//顧客パスワードテーブルに追加するSQL
		query[2] = "INSERT INTO KOKYAKUPASS_TABLE (KOKYAKUCD, PASSWORD) VALUES (" + values2 + ")";
		//顧客エラーフラグが「１」の場合（すでにユーザーが登録されている場合）
		if (data.getKokyakuErrFLG() == 1) {
			//SQLを実行せずに登録されている顧客コードをセットしてリターン
			data.setKokyakuCD(kokyakuCD);
		} else {
			try {
				//SQL文が格納された配列を実行する
				for (int i = 0; i < query.length; i++) {
					stmt.executeUpdate(query[i]);
				}
				//顧客コードをdataにセットする
				data.setKokyakuCD(kokyakuCD);
				//コミット
				con.commit();
			} catch (SQLException e) {
				//SQLエラーの場合、顧客エラーフラグに「９」をセット
				data.setKokyakuErrFLG(9);
				e.printStackTrace();
				try {
					//ロールバック
					con.rollback();
				} catch (SQLException er) {
					System.out.println("rollback エラー");
					//SQLエラーの場合、顧客エラーフラグに「９」をセット
					data.setKokyakuErrFLG(9);
					er.printStackTrace();
				}
			}
		}
		//statementのclose
		closeStatement();
		return data;
	}

	/**
	 * 同じメールアドレスが登録されていないかチェックする。
	 * @param mail メールアドレス
	 * @return 同じメールアドレスが登録されていれば、trueを返す。登録されていなければ、falseを返す。
	 */
	@Override
	protected boolean checkMail(String mail) {
		String query = null; //SQL文格納文字列
		ResultSet rs = null; //ResultSet
		//同じメールアドレスのデータを抽出するSQL文
		query = "SELECT * FROM KOKYAKUHOUJIN_TABLE WHERE MAIL =  '" + mail + "'";
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
		//同じメールアドレスが登録されていなければ、falseを返す
		return false;
	}

	/**
	 * 法人情報を取得する。
	 * @param kokyakuCD 顧客コード
	 * @param password パスワード
	 */
	public Houjin selectData(String kokyakuCD,
			String password) {
		boolean checkPassFlg = false; //パスワードチェックフラグ
		Houjin data = new Houjin(); //法人オブジェクト
		String query = null; //SQL文を格納する文字列
		ResultSet rs = null; //ResultSet
		String[] zipData = new String[2]; //郵便番号配列
		String[] telData = new String[3]; //電話番号配列
		String[] faxData = new String[3]; //FAX番号配列
		//statementのcreate
		creStatement();
		//顧客コードとパスワードにより、顧客パスワードテーブルで登録されているかのチェック
		checkPassFlg = checkCDAndPass(kokyakuCD, password);
		if (checkPassFlg) {
			//登録ずみの場合
			//顧客コードより、法人データ（１件）を取得するSQL
			query = "SELECT * FROM KOKYAKUHOUJIN_TABLE WHERE KOKYAKUCD = '" + kokyakuCD + "'";
			try {
				//SQLの実行
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					//ResultSetより1件のオブジェクトを取得
					//郵便番号分割処理
					zipData = zipSplit(rs.getString("ZIP"));
					//電話番号分割処理
					telData = telSplit(rs.getString("TEL"));
					if (rs.getString("FAX") != null) {
						//FAX番号分割処理
						faxData = telSplit(rs.getString("FAX"));
					} else {
						faxData = null;
					}
					//顧客データセット
					data.setKokyakuCD(rs.getString("KOKYAKUCD"));
					data.setKokyakuName(rs.getString("KOKYAKUNAME"));
					data.setKokyakuKana((rs.getString("KOKYAKUKANA")));
					data.setZip1(zipData[0]);
					data.setZip2(zipData[1]);
					data.setKenCD(rs.getString("KENCD"));
					data.setAddress1(rs.getString("ADDRESS1"));
					data.setAddress2(rs.getString("ADDRESS2"));
					data.setTel1(telData[0]);
					data.setTel2(telData[1]);
					data.setTel3(telData[2]);
					if (faxData != null) {
						data.setFax1(faxData[0]);
						data.setFax2(faxData[1]);
						data.setFax3(faxData[2]);
					}
					data.setMail(rs.getString("MAIL"));
					data.setUrikake_zan(rs.getInt("URIKAKE_ZAN"));
					data.setYoshin_gendo(rs.getInt("YOSIN_GENDO"));
					data.setEigyoshoCD(rs.getString("EIGYOUSHOCD"));
					data.setTourokuD(rs.getString("TOUROKU_D"));
					//顧客法人データセット
					data.setTantouName(rs.getString("TANTOUNAME"));
					if (rs.getString("TANTOUBUSHO") != null) {
						data.setTantoubusho(rs.getString("TANTOUBUSHO"));
					}
					if (rs.getString("GYOUSHU") != null) {
						data.setGyoushu(rs.getString("GYOUSHU"));
					}
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("executeQuery エラー");
				//SQLエラーの場合、顧客エラーフラグに「９」をセット
				data.setKokyakuErrFLG(9);
				e.printStackTrace();
			}
		} else {
			//顧客パスワードテーブルになければnullを返す
			data = null;
		}
		//statementのclose
		closeStatement();
		//値をセットした顧客データを返す
		return data;
	}
}

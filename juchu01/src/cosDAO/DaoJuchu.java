package cosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cosDataPack.Juchu;
import cosDataPack.Meisai;

/**
 * 受注Daoクラス。
 * 受注データをアクセスするクラス。主にJUCHU_TABLE,JUCHUMEISAI_TABLEをアクセス。
 * @version 1.0
 */
public class DaoJuchu {
	/** DB接続コネクション  */
	private Connection con;
	/** SQL送信用Statement */
	private Statement stmt = null;

	/**
	 * 引数なしコンストラクタ。
	 */
	public DaoJuchu() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * @param con DB接続コネクション
	 */
	public DaoJuchu(Connection con) {
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
	 * 受注データを登録する。
	 * <pre>
	 * 引数で渡された受注オブジェクト（１件）の情報をテーブルに書き込む
	 *    受注テーブル、受注明細テーブルの追加
	 *    販売在庫テーブルの更新（引き当て数）
	 *    顧客テーブルの更新（売掛け残）
	 *    コントロールテーブルの更新（受注コード）
	 * </pre>
	 * @param data 受注データ
	 * @return 受注データ
	 */
	public Juchu insertData(Juchu data) {
		String kokyakuCD; //顧客コード
		String eigyoushoCD; //営業所コード
		int jucyuKingaku; //受注金額
		int yoshinGendo; //与信限度額
		int kingakuSum; //売掛＋今回の受注金額
		String juchuCD = null; //現在の受注コード
		String splitCD = null; //受注コードの下6けた
		String splitCD4 = null; //受注コードの上4けた
		String nextCD; //次の受注コード（コントロールテーブル更新用）
		String juchuD; //受注日
		String juchYm; //受注年月
		String juchuTblName = "JUCHU_TABLE"; //insert時の受注テーブル名
		String meisaiTblName = "JUCHUMEISAI_TABLE"; //insert時の明細テーブル名
		String[] query; //SQL文を格納する配列
		//INSERT・UPDATE文を文字配列としてセット
		//受注INSERT＋各種テーブルUPDATE＋明細行INSERTの配列を確保
		query = new String[4 + data.getLines()];
		//statementのcreate
		creStatement();
		//受注エラーフラグに「0」をセットしておく
		data.setJuchuErrFLG(0);
		try {
			//自動コミット解除
			con.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("setAutoCommit エラー");
			e.printStackTrace();
		}
		/*
		 * INSERTに必要な値を取得する
		 */
		//顧客コードのセット
		kokyakuCD = data.getKokyaku().getKokyakuCD();
		//営業所コードのセット
		eigyoushoCD = data.getKokyaku().getEigyoshoCD();
		//受注日のセット
		//juchuD = DateFormat.getDateInstance().format(new Date());
		juchuD = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		data.setJuchuD(juchuD);
		/*
		 * 受注コード設定と次の受注コード更新のための準備処理
		 */
		//年のフォーマット
		DecimalFormat zeroMM = new DecimalFormat("00");
		//受注コードのフォーマット
		DecimalFormat zeroCD = new DecimalFormat("000000");
		//受注エラーコードのフォーマット
		DecimalFormat zeroErrCD = new DecimalFormat("0000000000");
		//受注日から年月を取得するため、Calendarオブジェクトを使う
		Calendar day = Calendar.getInstance();
		//受注年月取得
		String yy = String.valueOf(day.get(Calendar.YEAR));
		String mm = zeroMM.format((day.get(Calendar.MONTH) + 1));
		juchYm = yy.substring(2, 4) + mm;
		//受注金額のセット
		jucyuKingaku = data.getTotalKingaku();
		//与信限度額のセット
		yoshinGendo = data.getKokyaku().getYoshin_gendo();
		//売掛金＋今回の受注金額⇒売掛残
		kingakuSum = data.getKokyaku().getUrikake_zan() + jucyuKingaku;
		//与信限度額オーバーチェック（売掛残＞与信限度額）
		if (yoshinGendo < kingakuSum) {
			//与信オーバーの場合、
			//エラーフラグ「２」をセット
			data.setJuchuErrFLG(2);
			//現在の受注エラーコードを取得
			juchuCD = getJuchuCD("ERR_JUCHU_TABLE");
			//次受注のための受注エラーコードをセット
			nextCD = zeroErrCD.format(Integer.parseInt(juchuCD) + 1);
			//テーブル名を変える（エラー用の受注テーブルと受注明細テーブル）
			juchuTblName = "ERR_JUCHU_TABLE";
			meisaiTblName = "ERR_JUCHUMEISAI_TABLE";
			//受注金額に0をセットする
			jucyuKingaku = 0;
		} else {
			//与信OKの場合、
			//現在の受注コードを取得
			juchuCD = getJuchuCD("JUCHU_TABLE");
			if (juchuCD.equals("0000000000")) {
				//現在の受注コードがAll0の場合、
				//（受注コードのオーバーフロー）
				data.setJuchuCD(juchuCD);
				//エラーフラグに「8」をセットしてリターン
				data.setJuchuErrFLG(8);
				//statementのclose
				closeStatement();
				//dataをリターン
				return data;
			}
			//受注コード下6桁取得
			splitCD = juchuCD.substring(4);
			//受注コード上4ケタ取得(年月）
			splitCD4 = juchuCD.substring(0, 4);
			//受注コードのセット
			if (splitCD4.equals(juchYm) != true) {
				//受注年月と現在の受注コードの年月が変われば、
				//下6けたのコードを1から発番する
				splitCD = "000001";
			}
			//受注年月とコントロールテーブルから取得した受注コード下6ケタをセット
			juchuCD = juchYm + splitCD;
			//次受注のための受注コードをセット
			//受注年月とコントロールテーブルから取得した受注コード下6ケタ+1をセット
			int nextCD6 = Integer.parseInt(splitCD) + 1;
			if (nextCD6 > 999999) {
				//次受注コードのオーバーフローの場合
				//次受注コードにAll0をセットする
				nextCD = "0000000000";
			} else {
				//次受注コードのフォーマット
				nextCD = juchYm + zeroCD.format(nextCD6);
			}
		}
		//受注コードのセット（or 与信オーバーの場合、受注エラーコード）
		data.setJuchuCD(juchuCD);
		// 顧客テーブルの売掛金更新のSQL作成処理
		String kokyakuTblName = "KOKYAKUHOUJIN_TABLE";
		query[0] = "UPDATE "+ kokyakuTblName +" SET URIKAKE_ZAN = URIKAKE_ZAN + '" + jucyuKingaku +
    				"' WHERE KOKYAKUCD = '" + kokyakuCD + "'";
		//コードの現在値を格納するコントロールテーブルにUPDATEするSQL作成
		query[1] = "UPDATE CTR_CURCODE_TABLE SET CURCODE = '" + nextCD + "' WHERE DATANAME = '" + juchuTblName + "'";
		//受注テーブルのINSERT文のVALUES部分の作成
		String values2 = "'" + juchuCD + "', '" + kokyakuCD + "', '" + juchuD + "', "
				+ data.getTotalKingaku() + ", "
				+ data.getShiharaiType() + ", '" + data.getNouhinYoteiD() + "', '"
				+ data.getNouhinYoteiRange() + "'";
		//受注テーブルにINSERTするSQLの作成
		query[2] = "INSERT INTO " + juchuTblName + " (JUCHUCD, KOKYAKUCD, JUCHU_D, TOTALKINGAKU, " +
				"SIHARAI_TYPE, A_NOUHINYOTEI_D, A_NOUHINYOTEITRANGE) " +
				"VALUES (" + values2 + ")";
		//明細テーブルにINSERTするSQL作成処理
		String mQuery = null; //明細INSERT用の文字列
		StringBuilder sb = new StringBuilder(); //明細INSERT用バッファ
		//明細の行数分SQLを作成する
		for (int i = 0; i < data.getLines(); i++) {
			//明細1件を取り出す
			Meisai meisai = data.getMeisai().get(i);
			//与信限度エラーの場合は、在庫の更新をしないため、スキップ
			if (data.getJuchuErrFLG() == 0) {
				//営業所コード、商品コードで実在庫数、引当数を取得
				int[] cntList = getZaikoCnt(eigyoushoCD, meisai.getShohinPriData().getGoodsCD());
				//実在庫数
				int zaikoCnt = cntList[0];
				//引当数
				int hikiateCnt = cntList[1];
				//在庫の引き当て可能かのチェック（実在庫数＜引当数＋今回の数量）
				if (zaikoCnt < hikiateCnt + meisai.getSuryou()) {
					//在庫引き当てができない場合
					//受注エラーフラグに「１」をセット
					data.setJuchuErrFLG(1);
					//明細エラーフラグに「１」をセット
					data.getMeisai().get(i).setMeisaiErrFLG(1);
				} else {
					//在庫引き当てが可能な場合
					//明細エラーフラグに「0」をセット
					data.getMeisai().get(i).setMeisaiErrFLG(0);
					//在庫テーブルの引き当て数を更新するSQL
					query[3 + i] = "UPDATE HANBAIZAIKO" + eigyoushoCD + "_TABLE SET HIKIATE_CNT = HIKIATE_CNT + "
							+ meisai.getSuryou() +
							"  WHERE GOODSCD = '" + meisai.getShohinPriData().getGoodsCD() + "'";
				}
			}
			//受注明細テーブルにINSERTするVALUES部分の作成
			mQuery = "('" + juchuCD + "', '" + meisai.getShohinPriData().getGoodsCD() + "', '"
					+ meisai.getLineNum() + "', '" + meisai.getSuryou() + "', '" + meisai.getKingaku()
					+ "', '" + meisai.getNouhinYoteiD() + "', '" + meisai.getNouhinYoteiRange() + "')";
			//明細INSERT用バッファに追加
			sb.append(mQuery);
			if (i != data.getLines() - 1) {
				sb.append(",");
			}
		}
		//明細テーブルのINSERT文のVALUES部
		String values3 = new String(sb);
		//受注明細テーブルに追加するSQL文
		query[3 + data.getLines()] = "INSERT INTO " + meisaiTblName + " (JUCHUCD, GOODSCD, LINENUM, " +
				"SURYOU, KINGAKU, NOUHINYOTEI_D, NOUHINYOTEITRANGE) " +
				"VALUES " + values3;
		//SQLの実行
		if (data.getJuchuErrFLG() == 0 || data.getJuchuErrFLG() == 2) {
			//受注エラーフラグが「0」「2」の場合、SQLを実行
			try {
				//SQL文を格納した配列を実行
				for (int j = 0; j < query.length; j++) {
					//受注エラーフラグが「2」の場合(与信エラー）
					//在庫更新のSQL実行はスキップ
					if (query[j] != null) {
						stmt.executeUpdate(query[j]);
					}
				}
				//受注データに受注コードと受注日をセットする
				data.setJuchuCD(juchuCD);
				data.setJuchuD(juchuD);
				//コミット
				con.commit();
			} catch (SQLException e) {
				//エラーの場合、SQL受注エラーフラグに「９」をセット
				data.setJuchuErrFLG(9);
				e.printStackTrace();
				try {
					//ロールバック
					con.rollback();
				} catch (SQLException er) {
					System.out.println("rollback エラー");
					//エラーの場合、SQL受注エラーフラグに「９」をセット
					data.setJuchuErrFLG(9);
					er.printStackTrace();
				}
			}
		}
		//statementのclose
		closeStatement();
		//dataをリターンする
		return data;
	}

	/**
	 * 現在の受注コードを取得する。
	 * @param tableName テーブル名
	 * @return 受注コード
	 */
	private String getJuchuCD(String tableName) {
		String query; //SQL格納文字列
		String curCD = null; //現在の受注コード
		ResultSet rs = null; //ResultSet
		//コード現在値取得するSQL
		query = "SELECT * FROM CTR_CURCODE_TABLE WHERE DATANAME = '" + tableName + "'";
		try {
			//SQLの実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//現在の受注コードをセットする
				curCD = rs.getString("CURCODE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//受注コードをリターン
		return curCD;
	}

	/**
	 * 商品の在庫数と引き当て数を取得する。
	 * @param eigyoshoCD 営業所コード
	 * @param goodsCD 商品コード
	 * @return 在庫数、引き当て数の配列
	 */
	private int[] getZaikoCnt(String eigyoshoCD,
			String goodsCD) {
		String query = null; //SQL文を格納する文字列
		ResultSet rs = null; //ResultSet
		//在庫数と引き当て数を格納する配列
		int[] cntList = new int[2];//cntList[0]：在庫数、cntList[1]：引当数
		//在庫数取得のSQL作成
		query = "SELECT * FROM HANBAIZAIKO" + eigyoshoCD + "_TABLE WHERE GOODSCD = '" + goodsCD + "'";
		try {
			//SQLの実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//在庫数をセットする
				cntList[0] = rs.getInt("ZAIKO_CNT"); //在庫数
				//引き当て数をセットする
				cntList[1] = rs.getInt("HIKIATE_CNT"); //引当数
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//在庫数、引き当て数の配列をリターンする
		return cntList;
	}
}

package cosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cosDataPack.Shohin;
import cosDataPack.ShohinCategory;
import cosDataPack.ShohinPrimalData;

/**
 * 商品Daoクラス。
 * 商品テーブルをアクセスするDAOのスーパークラス。
 * @version 1.0
 */
public class DaoShohin {
	/** DB接続コネクション */
	protected Connection con;
	/** SQL送信用Statement */
	protected Statement stmt = null;

	/**
	 * 引数なしコンストラクタ。
	 */
	public DaoShohin() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * コネクションをセットする。
	 * @param con DB接続コネクション
	 */
	public DaoShohin(Connection con) {
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
	 * 全商品のカテゴリリストを取得する。
	 * @return 全商品のカテゴリリスト
	 */
	public ArrayList<ShohinCategory> selectData() {
		ArrayList<ShohinCategory> list = new ArrayList<ShohinCategory>();//リストオブジェクト
		String query = null; //SQL格納文字列
		ResultSet rs = null; //ResultSet
		//statementのcreate
		creStatement();
		//全ての商品カテゴリリストを取得するSQL文
		query = "SELECT G.GSUBCTGCD, G.GSUBCTGNAME, C.GCTGCD, C.GCTGNAME, C.CLASSNAME " +
				"FROM GSUBCTG_TABLE G, CTR_GCTG_TABLE C " +
				"WHERE LEFT(G.GSUBCTGCD, 2) = C.GCTGCD";
		try {
			//SQL実行
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				//ResultSetよりオブジェクトを取得
				//商品カテゴリオブジェクト作成
				ShohinCategory sc1 = new ShohinCategory();
				//カテゴリ情報のセット
				sc1.setGctgCD(rs.getString("GCTGCD"));
				sc1.setGctgName(rs.getString("GCTGNAME"));
				sc1.setGsubctgCD(rs.getString("GSUBCTGCD"));
				sc1.setGsubctgName(rs.getString("GSUBCTGNAME"));
				sc1.setClassName(rs.getString("CLASSNAME"));
				//リストに追加する
				list.add(sc1);
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//SQLエラーの場合nullを返す
			list = null;
		}
		//statementのclose
		closeStatement();
		//listを返す
		return list;
	}

	/**
	 * 商品リストを取得する。
	 * @param subCtgCD 商品サブカテゴリコード
	 * @param eigyoshoCD 営業所コード
	 * @return 商品リスト
	 */
	public ArrayList<Shohin> selectData(String subCtgCD,
			String eigyoshoCD) {
		ArrayList<Shohin> list = new ArrayList<Shohin>();//リストオブジェクト
		String query = null; //SQL格納文字列
		String ctgCD = null; //商品カテゴリコード
		String className = null; //クラス名
		ResultSet rs = null; //ResultSet
		//statementのcreate
		creStatement();
		//商品サブカテゴリコードより商品カテゴリコードを取得（上２桁）
		ctgCD = subCtgCD.substring(0, 2);
		//商品サブカテゴリコードよりクラス名を取得
		className = getClassName(ctgCD);
		//商品サブカテゴリコードと営業所コードにより情報を取得するSQL
		query = "SELECT T.GOODSCD, T.GOODSNAME, M.MAKERNAME, T.IRISU, T.TANNI, H_TANKA, G.GSUBCTGNAME, " +
				"C.GCTGNAME, C.CLASSNAME, H.ZAIKO_CNT , H.HIKIATE_CNT " +
				"FROM " + className + "_TABLE T, GSUBCTG_TABLE G ,CTR_GCTG_TABLE C, HANBAIZAIKO" + eigyoshoCD
				+ "_TABLE H, MAKER_TABLE M " +
				"WHERE ((LEFT(T.GOODSCD, 2) = C.GCTGCD) AND (LEFT(T.GOODSCD, 4) = G.GSUBCTGCD) " +
				"AND T.GOODSCD = H.GOODSCD AND T.MAKERCD = M.MAKERCD) AND LEFT(T.GOODSCD,4) = '" + subCtgCD + "'";
		try {
			//SQL実行
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				//ResultSetよりオブジェクトを取得
				//商品オブジェクト作成
				Shohin s1 = new Shohin();
				//商品基本情報オブジェクト作成
				ShohinPrimalData sp1 = new ShohinPrimalData();
				//商品カテゴリオブジェクト作成
				ShohinCategory sc1 = new ShohinCategory();
				//商品カテゴリ情報をセット
				sc1.setGctgCD(ctgCD);
				sc1.setGctgName(rs.getString("GCTGNAME"));
				sc1.setGsubctgCD(subCtgCD);
				sc1.setGsubctgName(rs.getString("GSUBCTGNAME"));
				sc1.setClassName(rs.getString("CLASSNAME"));
				//商品基本情報をセット
				sp1.setGoodsCD(rs.getString("GOODSCD"));
				sp1.setGoodsName(rs.getString("GOODSNAME"));
				sp1.setHtanka(rs.getInt("H_TANKA"));
				//商品のその他の情報をセット
				s1.setShohinPriData(sp1);
				s1.setShohinCtgInfo(sc1);
				s1.setMakerName(rs.getString("MAKERNAME"));
				s1.setIrisu(rs.getInt("IRISU"));
				s1.setTanni(rs.getString("TANNI"));
				s1.setZaikoCnt(rs.getInt("ZAIKO_CNT"));
				s1.setHikiateCnt(rs.getInt("HIKIATE_CNT"));
				//在庫状況に応じた表示文字列をセットしておく
				s1.setZaikoHyouji();
				//リストに追加する
				list.add(s1);
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//SQLエラーの場合nullを返す
			list = null;
		}
		//statementのclose
		closeStatement();
		//listを返す
		return list;
	}

	/**
	 * クラス名を取得する。
	 * @param ctgCD カテゴリコード
	 * @return クラス名
	 */
	private String getClassName(String ctgCD) {
		String query; //SQL格納文字列
		String className = null; //クラス名
		ResultSet rs = null; //ResultSet
		//カテゴリコードからクラス名（テーブル名の一部）を取得するSQL
		query = "SELECT * FROM CTR_GCTG_TABLE WHERE GCTGCD ='" + ctgCD + "'";
		try {
			//SQL実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetよりオブジェクトを取得
				//クラス名を取得
				className = rs.getString("CLASSNAME");
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//クラス名を返す
		return className;
	}
}

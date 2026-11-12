package cosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cosDataPack.Bungu;
import cosDataPack.Shohin;
import cosDataPack.ShohinCategory;
import cosDataPack.ShohinPrimalData;

/**
 * 文具Daoクラス。
 * 文具データをアクセスするクラス。主にBUNGU_TABLEをアクセス。
 * @version 1.0
 */
public class DaoBungu extends DaoShohin {
	/**
	 * 引数なしコンストラクタ。
	 */
	public DaoBungu() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * @param con DB接続コネクション
	 */
	public DaoBungu(Connection con) {
		//コネクションをセットする
		super(con);
	}

	/**
	 * 文具商品情報を取得する。
	 * @param code 商品コード
	 * @param eigyosyoCD 営業所コード
	 */
	public ArrayList<Shohin> selectData(String code,
			String eigyosyoCD) {
		ArrayList<Shohin> list = new ArrayList<Shohin>();//リストオブジェクト
		Bungu s1 = new Bungu(); //文具オブジェクト
		ShohinPrimalData sp1 = new ShohinPrimalData(); //商品基本情報オブジェクト
		ShohinCategory sc1 = new ShohinCategory(); //商品カテゴリオブジェクト
		String query = null; //クエリ格納用
		ResultSet rs = null; //ResultSet
		//statementのcreate
		creStatement();
		//引数で渡された商品コードと営業所コードにより１件の情報を取得するSQL
		query = "SELECT B.GOODSCD, B.GOODSNAME, B.H_TANKA, C.GCTGCD, C.GCTGNAME, G.GSUBCTGCD, " +
				"G.GSUBCTGNAME, C.CLASSNAME, M.MAKERNAME, B.IRISU, B.TANNI, B.MAKERCD, B.COMMENT, B.IMG_URI, " +
				"H.ZAIKO_CNT, B.SHUBETU, B.OTHER, B.COLOR, B.KATABAN, B.SIZE, B.ZAISITU " +
				"FROM BUNGU_TABLE B, GSUBCTG_TABLE G, CTR_GCTG_TABLE C, HANBAIZAIKO" + eigyosyoCD
				+ "_TABLE H, MAKER_TABLE M " +
				"WHERE ((LEFT(B.GOODSCD, 2) = C.GCTGCD) AND (LEFT(B.GOODSCD, 4) = G.GSUBCTGCD) " +
				"AND B.GOODSCD = H.GOODSCD AND B.MAKERCD = M.MAKERCD) " +
				"AND B.GOODSCD = '" + code + "'";
		try {
			//SQL（クエリ）実行
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				//ResultSetより1件のオブジェクトを取得
				//商品カテゴリ情報を取得
				sc1.setGctgCD(rs.getString("GCTGCD"));
				sc1.setGctgName(rs.getString("GCTGNAME"));
				sc1.setGsubctgCD(rs.getString("GSUBCTGCD"));
				sc1.setGsubctgName(rs.getString("GSUBCTGNAME"));
				sc1.setClassName(rs.getString("CLASSNAME"));
				//商品基本情報を取得
				sp1.setGoodsCD(rs.getString("GOODSCD"));
				sp1.setGoodsName(rs.getString("GOODSNAME"));
				sp1.setHtanka(rs.getInt("H_TANKA"));
				//商品のその他の情報を取得
				s1.setShohinPriData(sp1);
				s1.setShohinCtgInfo(sc1);
				s1.setMakerName(rs.getString("MAKERNAME"));
				s1.setIrisu(rs.getInt("IRISU"));
				s1.setTanni(rs.getString("TANNI"));
				s1.setMakerCD(rs.getString("MAKERCD"));
				s1.setComments(rs.getString("COMMENT"));
				s1.setImgURL(rs.getString("IMG_URI"));
				s1.setZaikoCnt(rs.getInt("ZAIKO_CNT"));
				s1.setZaikoHyouji();
				s1.setShubetu(rs.getString("SHUBETU"));
				s1.setOther(rs.getString("OTHER"));
				s1.setColor(rs.getString("COLOR"));
				s1.setKataban(rs.getString("KATABAN"));
				s1.setSize(rs.getString("SIZE"));
				s1.setZaisitu(rs.getString("ZAISITU"));
				//リストに追加
				list.add(s1);
			} else {
				//対象の文具データがなければnullを返す
				list = null;
			}
			//ResultSetのクローズ
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//エラーの場合、nullを返す
			list = null;
		}
		//statementのclose
		closeStatement();
		return list;
	}
}

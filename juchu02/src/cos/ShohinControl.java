package cos;

import java.util.ArrayList;

import cosDAO.DaoControl;
import cosDataPack.Shohin;
import cosDataPack.ShohinCategory;

/**
 * 商品コントロールクラス。
 * 商品検索に必要な機能を提供するクラス。
 * 受注コントロールクラスから呼び出される。
 * @version 1.0
 */
public class ShohinControl extends BaseControl {
	/**
	 * 商品カテゴリリストを取得する。
	 * <pre>
	 * 1．DaoControlのselectShohinCategoryメソッドを実行し、商品サブカテゴリリストを取得する。
	 * 2．取得した商品サブカテゴリリストをリターンする。
	 * </pre>
	 * @return 商品カテゴリリスト
	 */
	public static ArrayList<ShohinCategory> getShohinCateList() {
		ArrayList<ShohinCategory> list; //リストオブジェクト
		//DAOの商品カタログリスト作成を呼び出す
		list = DaoControl.selectShohinCategory();
		//リストをリターンする
		return list;
	}

	/**
	 * カテゴリ別商品リストまたは、商品情報を取得する。
	 * <pre>
	 * 1．DaoControlのselectShohinメソッドの引数に、商品サブカテゴリコードまたは商品コード、と営業所コードを渡し商品リストを取得する。
	 * 2．取得した商品リストをリターンする。
	 *
	 * ・ カテゴリ別商品リストの取得と、商品詳細情報（商品1個）の取得の両方で呼び出される。
	 *    （引数が商品サブカテゴリコード（4ケタ）の場合は、商品リストを取得する
	 *    引数が商品コードの（14ケタ）場合は、商品詳細情報を取得する
	 *    これらの判断は、呼び出すDaoControlで行う。）
	 * ・ 商品詳細情報の場合は、ArrayListに1件だけ格納されており、ArrayListから1件取得して使用する。
	 * </pre>
	 * @param code 商品サブカテゴリコードまたは商品コード
	 * @param eigyoshoCD 営業所コード
	 * @return カテゴリ別商品リストまたは、商品情報
	 */
	public static ArrayList<Shohin> getShohinList(String code,
			String eigyoshoCD) {
		ArrayList<Shohin> list; //リストオブジェクト
		//DAOの商品リスト作成を呼び出す
		//コード（商品サブカテゴリコードor商品コード）と営業所コードで
		//商品リストを取得する
		list = DaoControl.selectShohin(code, eigyoshoCD);
		//リストをリターンする
		return list;
	}
}

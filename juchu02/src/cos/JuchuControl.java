package cos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cosDAO.DaoControl;
import cosDataPack.Juchu;
import cosDataPack.Kokyaku;
import cosDataPack.Shohin;
import cosDataPack.ShohinCategory;
import cosDataPack.ShohinPrimalData;

/**
 * 受注コントロールクラス。
 * 受注処理に必要な機能を提供するクラス。
 * 受注処理の画面から押されたボタンを判断し、対応する処理を実行して次の画面へ転送する。
 * １つの受注につき、この受注コントロールオブジェクトが作成される。
 * @version 1.0
 */
public class JuchuControl extends BaseControl {
	/** 受注情報 */
	private Juchu data;
	/** 商品リスト */
	private ArrayList<Shohin> shohinList = null;
	/** 商品カテゴリリスト */
	private ArrayList<ShohinCategory> categoryList = null;
	/** 法人顧客Jspマップテーブル（受注登録で使用するJSPページとコマンドのマッピング表） */
	private Map<String, String> targetHoujin;
	{
		targetHoujin = new HashMap<>();
		targetHoujin.put("ログオン", "cosd1010.jsp");
		targetHoujin.put("検索", "cosd1020.jsp");
		targetHoujin.put("カートに入れる", "cosd1020.jsp");
		targetHoujin.put("カート内リスト", "cosd1030.jsp");
		targetHoujin.put("詳細01", "cosd1040.jsp");
		targetHoujin.put("詳細02", "cosd1041.jsp");
		targetHoujin.put("ご注文手続きへ", "cosd1050.jsp");
		targetHoujin.put("ご購入を続ける", "cosd1010.jsp");
		targetHoujin.put("再計算", "cosd1030.jsp");
		targetHoujin.put("カート内ご注文リストへ戻る", "cosd1030.jsp");
		targetHoujin.put("お支払い・お届け先入力", "cosd1060.jsp" );
		targetHoujin.put("次へ", "cosd1070.jsp");
		targetHoujin.put("ご注文確認へ戻る", "cosd1050.jsp");
		targetHoujin.put("ご注文を確定する", "cosd1080.jsp");
		targetHoujin.put("お支払い方法・お届け先の入力へ戻る", "cosd1060.jsp");
		targetHoujin.put("戻る", "cosd1030.jsp");
		targetHoujin.put("トップページへ", "cosd1000.html");
		targetHoujin.put("ログオフ", "cosd1000.html");
		targetHoujin.put("引当エラー", "cosd151E.jsp");
		targetHoujin.put("与信エラー", "cosd152E.jsp");
		targetHoujin.put("システムエラー", "cosd155E.jsp");
	}

	/**
	 * 引数なしコンストラクタ。
	 * スーパークラスのコンストラクタを呼び出して、基本初期化処理を行う。
	 */
	public JuchuControl() {
		super();
	}

	/**
	 * 引数ありコンストラクタ
	 * 受注オブジェクトを作成して、フィールドのdataにセットする
	 * <pre>
	 * 1．引数なしコンストラクタを呼び出して、基本の初期化処理を行う。
	 * 2. ボタン表示文字列とJSPファイルのマッピングテーブルをセットする。
	 * 3．受注オブジェクトを生成して、フィールドdataにセットする。
	 * 4．受注オブジェクト（data）のフィールドkokyakuに、引数で渡された顧客オブジェクトをセットする。
	 * </pre>
	 * @param kokyaku 顧客情報
	 */
	public JuchuControl(Kokyaku kokyaku) {
		//引数なしコンストラクタを呼び出してJspマップテーブルをセットする
		this();
		this.setTargetMap(targetHoujin);
		//受注オブジェクトを作成する
		data = new Juchu();
		//受注データに引数の顧客をセットする
		data.setKokyaku(kokyaku);
	}

	/**
	 * 受注情報を取得する。
	 * @return 受注情報
	 */
	public Juchu getData() {
		return data;
	}

	/**
	 * 受注情報を設定する。
	 * @param data 受注情報
	 */
	public void setData(Juchu data) {
		this.data = data;
	}

	/**
	 * 商品リストを取得する。
	 * @return 商品リスト
	 */
	public ArrayList<Shohin> getShohinList() {
		return shohinList;
	}

	/**
	 * 商品リストを設定する。
	 * @param shohinList 商品リスト
	 */
	public void setShohinList(ArrayList<Shohin> shohinList) {
		this.shohinList = shohinList;
	}

	/**
	 * 商品カテゴリリストを取得する。
	 * @return 商品カテゴリリスト
	 */
	public ArrayList<ShohinCategory> getCategoryList() {
		return categoryList;
	}

	/**
	 * 商品カテゴリリストを設定する。
	 * @param categoryList 商品カテゴリリスト
	 */
	public void setCategoryList(ArrayList<ShohinCategory> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * 受注オブジェクトを作成して、フィールドのdataにセットする。
	 * <pre>
	 * 1. スーパークラスのentryメソッドを実行して、このクラスの基本処理をする。
	 * 2．getCommandメソッドを実行して、押されたボタン情報を取得する。
	 * 3．ボタンが“ログオン”または、“購入を続ける”の場合、
	 *    3-1．ShohinControlのgetShohinCateListを実行して、商品サブカテゴリリストを作成する。
	 * 4．ボタンが“検索”の場合、
	 *    4-1．requestのgetParameterメソッドの引数に"category"を渡して、画面で選択されたサブカテゴリコードを取得する。
	 *    4-2．dataの顧客情報から営業所コードを取得する。
	 *    4-3．取得した商品サブカテゴリコードと営業所コードをShohinControlのgetShohinListの引数に渡して、カテゴリ別商品リストを取得する。
	 *    4-4．sessionのsetAttributeメソッドで、商品サブカテゴリコードをセットする。
	 *       session.setAttribute("subctgcd",gsubctg);
	 * 5．ボタンが“詳細”の場合、
	 *    5-1．requestのgetParameterに"hiddenCD"を渡して、詳細ボタンが押された商品の商品コードを取得する。
	 *    5-2．dataの顧客情報から営業所コードを取得する。
	 *    5-3．取得した商品コードと営業所コードを、ShohinControlのgetShohinListに渡して、該当する商品の詳細情報を取得する。（1件のみ取得する）
	 *    5-4．sessionのsetAttributeメソッドで、商品の詳細情報をセットする。
	 *       session.setAttribute("shohin", shohin);
	 *    5-5．ボタン情報に、「”詳細”+商品カテゴリコード（商品コードの頭2ケタ）」をセットする。
	 *       例）文具の場合
	 *       "詳細"+shohinCD.substring(0,2)
	 * 6．ボタンが“カートに入れる”の場合、
	 *    6-1．addCartメソッドにrequestを渡して、カートの追加処理を行う。
	 * 7．ボタンが“再計算”の場合、
	 *    7-1．updateCartメソッドの引数にrequestを渡して、カート内容を更新する。
	 * 8．ボタンが“次へ”の場合、
	 *    8-1．requestのgetParameterメソッドの引数に"nouhinyoteid"を渡して、画面から入力された納品予定日を取得する。
	 *    8-2．requestのgetParameterメソッドの引数に"nouhinyoteirange"を渡して、画面から入力されたお届け時間帯を取得する。
	 *    8-3．requestのgetParameterメソッドの引数に"shiharaitype"を渡して、画面から入力された支払方法を取得する。
	 *    8-4．取得した、納品予定日、お届け時間帯、支払方法をdataにセットする。
	 *    8-5．dataのsetMeisaiNouhinbiメソッドで受注明細の納品予定日と納品予定時間帯をセットする。
	 * 9．ボタンが“ご注文を確定する”の場合、
	 *    9-1．DaoControlのinsertJuchuの引数にdataを渡して、受注データをDBに登録する。
	 *       登録された受注データが返る。
	 *    9-2．戻ってきた受注データのgetJuchuErrFLGが　1　の場合（引当不可能商品の場合）、
	 *       9-2-1．ボタン情報に"引当エラー"をセットし直す。
	 *    9-3．戻ってきた受注データのgetJuchuErrFLGが　2　の場合（与信限度額オーバーの場合）、
	 *       9-3-1．ボタン情報に"与信エラー”をセットし直す。
	 * 10．setTargetMapメソッドにボタン情報を渡し、ターゲットのJspを取得する。
	 * 11．sessionのsetAttributeメソッドで、受注コントロールをセットする
	 *    session.setAttribute("juchucontrol",this);
	 * 12． requestのgetRequestDispatcherの引数に、「10．」で取得したターゲットJspを渡す。
	 * 13．「12．」の戻り値オブジェクトのforwardメソッドを実行して、Jspへフォワードする。
	 *    例）
	 *    RequestDispatcher rd = request.getRequestDispatcher(target);
	 *    rd.forward(request,response);
	 *
	 * ・ 4-4、5-4、11、12、13、Webの送受信およびセッション管理に必要な処理。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントへレスポンスする情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 */
	@Override
	public void entry(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException,
			IOException {
		super.entry(request, response);
		String target = null; //ターゲットJSP
		Shohin shohin = null; //商品
		//画面で押されたボタンの情報を取得する
		String btn = this.getCommand();
		/*
		 * ”ログオン”、”ご購入を続ける”が押されたとき
		 */
		if (btn.equals("ログオン") || btn.equals("ご購入を続ける")) {
			//商品コントロールの商品カテゴリリストを作成するメソッドを呼び出す
			categoryList = ShohinControl.getShohinCateList();
		}
		/*
		 * ”検索”が押されたとき
		 */
		if (btn.equals("検索")) {
			//画面で選択されたサブカテゴリを取得する
			String gsubctg = request.getParameter("category");
			//受注データから営業所コードを取得する
			String eigyoshoCD = data.getKokyaku().getEigyoshoCD();
			//商品コントロールの商品リスト取得メソッドを呼び出して商品リストを取得する
			shohinList = ShohinControl.getShohinList(gsubctg, eigyoshoCD);
			//商品サブカテゴリコードをセッションにセットする
			session.setAttribute("subctgcd", gsubctg);
		}
		/*
		 * 詳細が押されたとき
		 */
		if (btn.equals("詳細")) {
			ArrayList<Shohin> tmpList = null; //商品リストの一時格納バッファ
			//選択された商品の商品コードを取得する
			String shohinCD = request.getParameter("hiddenCD");
			//受注データから営業所コードを取得する
			String eigyoshoCD = data.getKokyaku().getEigyoshoCD();
			//商品コントロールの商品リスト取得メソッドを呼び出して商品情報を取得する
			tmpList = ShohinControl.getShohinList(shohinCD, eigyoshoCD);
			if (tmpList != null) {
				if (tmpList.get(0) != null) {
					//リストからは1件だけ取得する（この場合は1件のみ取得）
					shohin = tmpList.get(0);
				}
			}
			//商品リストをセッションにセットする
			session.setAttribute("shohin", shohin);
			//Jspマップテーブル検索用に「”詳細”+商品カテゴリコード」をセットする
			btn = "詳細" + shohinCD.substring(0, 2);
		}
		/*
		 * ”カートに入れる”が押されたとき
		 */
		if (btn.equals("カートに入れる")) {
			//カート追加メソッドを呼び出し、カート追加処理を行う
			this.addCart(request);
		}
		/*
		 * 	”再計算”が押されたとき
		 */
		if (btn.equals("再計算")) {
			//カート内容を更新するメソッドを呼び出し、カートの更新を行う
			updateCart(request);
		}
		/*
		 * ”次へ”が押されたとき
		 */
		if (btn.equals("次へ")) {
			//支払方法、お届け日、お届け時間帯をセットする
			String nouhinBi = request.getParameter("nouhinyoteid");
			String nouhinRange = request.getParameter("nouhinyoteirange");
			data.setShiharaiType(Integer.parseInt(request.getParameter("shiharaitype")));
			data.setShiharaiTypeS();
			data.setNouhinYoteiD(nouhinBi);
			data.setNouhinYoteiRange(nouhinRange);
			//明細の納品予定日と納品予定時間帯をセットする
			data.setMeisaiNouhinbi(nouhinBi, nouhinRange);
		}
		/*
		 * ”ご注文を確定する
		 */
		if (btn.equals("ご注文を確定する")) {
			//Daoにより受注データをインサートする
			data = DaoControl.insertJuchu(data);
			//何らかのエラーフラグがあれば、ボタンの文字列をエラー画面用に置き換える
			if (data.getJuchuErrFLG() == 1) {
				//「１」の場合、引き当て不可の商品在り
				btn = "引当エラー";
			} else if (data.getJuchuErrFLG() == 2) {
				//「２」の場合、与信限度オーバー
				btn = "与信エラー";
			} else if (data.getJuchuErrFLG() == 8 || data.getJuchuErrFLG() == 9) {
				btn = "システムエラー";
			}
		}
		/*
		 * ”カート内リスト”が押されたとき
		 * ”ご注文手続きへ”が押されたとき
		 * ”ご注文確認へ戻る”が押されたとき
		 * ”お支払い方法・お届け先の入力へ戻る”が押されたとき
		 * ”戻る”が押されたとき
		 * 	は、受注データをセッションにセットし、
		 * 	ターゲットJSPを設定してフォーワードする
		 */
		//ボタン文字列からターゲットのJSPファイルを取得する
		target = getTargetMap(btn);
		//受注コントロールをセッションにセットして
		//JSP（画面）へフォワードする
		session.setAttribute("juchucontrol", this);
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}

	/**
	 * カートへ購入商品を追加する。
	 * （明細の追加）
	 * <pre>
	 * 1．画面の商品リストで入力された数量を取得して受注明細を作成する。
	 *    以下の処理を画面の行数分くり返す。
	 *    1-1．requestのgetParameterの引数に「"suryou"+参照している商品リストの行」を渡し、数量を取得する。
	 *    1-2．文字が入力されていれば、数量に0をセットする。
	 *    1-3．数量が入力されていれば（!=0)、
	 *       1-3-1．商品リストから該当行のデータ（商品基本情報）を取得する。
	 *       1-3-2．dataのaddMeisaiの引数に入力された数量と取得した商品基本情報を渡して、明細を追加する。
	 * 2．dataのsetTotalKingakuを実行して、受注データの受注総額を計算する。
	 *
	 * ・ 「1-2．文字が入力されていれば」、入力された数量を数値に変換し（Interger.parseInt（））、その際の例外処理（NumberFormatExceptin）で対応する。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 */
	private void addCart(HttpServletRequest request) {
		int suryou = 0; //数量
		String strSuryou = null; //画面から数量を取得するための文字列
		//画面の商品リストで入力された数量を取得して受注明細を作成する
		for (int i = 0; i < shohinList.size(); i++) {
			//入力された数量の取得（文字列）
			strSuryou = request.getParameter("suryou" + i);
			try {
				//文字列の数量をint型に変換
				suryou = Integer.parseInt(strSuryou);
			} catch (NumberFormatException e) {
				//購入数がブランクの場合や文字の場合、数量に0をセット
				suryou = 0;
			}
			if (suryou != 0) {
				//数量が入力されていれば、明細を作成する
				//商品リストから商品基本情報の取得
				ShohinPrimalData shohinPriData = shohinList.get(i).getShohinPriData();
				//受注データの受注明細追加メソッドを呼び出し、明細を追加する
				data.addMeisai(suryou, shohinPriData);
			}
		}
		//受注データの受注総額を計算する
		data.setTotalKingaku();
	}

	/**
	 * カートの内容を更新する。
	 * （明細の更新）
	 * <pre>
	 * 1．画面のカート内リスト(受注明細)で入力された数量を取得して、受注明細を更新する。
	 *    以下の処理をdataの明細行数分くり返す。
	 *    1-1．requestのgetParameterの引数に「"suryou"+参照している明細の行」を渡し、画面の明細に入力（表示）された数量を取得する。
	 *    1-2．文字が入力されていれば、数量に0をセットする。
	 *    1-3．数量が入力されていれば（0でなければ）、
	 *       1-3-1．dataのupdateMeisaiKingakuに、数量と明細行を渡し入力された値で明細の数量をセットする。
	 * 2．dataのsetTotalKingakuを実行して、受注データの受注総額を計算する。
	 *
	 * ・「1-2．文字が入力されていれば」、入力された数量を数値に変換し（Interger.parseInt（））、その際の例外処理（NumberFormatExceptin）で対応する。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 */
	private void updateCart(HttpServletRequest request) {
		int suryou = 0; //数量
		String strSuryou = null; //画面から数量を取得するための文字列
		//画面のカート内リスト（明細）で入力された数量を取得して受注明細を更新する
		for (int i = 0; i < data.getMeisai().size(); i++) {
			//入力された数量の取得（文字列）
			strSuryou = request.getParameter("suryou" + i);
			try {
				//文字列の数量をint型に変換
				suryou = Integer.parseInt(strSuryou);
			} catch (Exception e) {
				//購入数がブランクの場合や文字の場合、数量に0をセット
				suryou = 0;
			}
			//入力された値で明細の数量をセットする
			data.updateMeisaiKingaku(suryou, i);
		}
		//受注データの受注総額をセットする
		data.setTotalKingaku();
	}
}

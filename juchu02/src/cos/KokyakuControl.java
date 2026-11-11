package cos;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cosDataPack.Houjin;
import cosDataPack.Kokyaku;

/**
 * 顧客登録コントロールクラス。
 * 顧客登録に必要な機能を提供するクラス。
 * ユーザー登録の画面から押されたボタンを判断し、対応する処理を実行して次の画面へ転送する。
 * １つのユーザー登録処理につき、この顧客登録コントロールが作成される。
 * @version 1.0
 */
public class KokyakuControl extends BaseControl {
	/** 顧客情報 */
	private Kokyaku data;
	/** JSPページとコマンドのマッピング表 */
	private Map<String, String> targetHoujin;
	{
		targetHoujin = new HashMap<>();
		targetHoujin.put("新規登録", "cosd1090.jsp");
		targetHoujin.put("次へ", "cosd1100.jsp" );
		targetHoujin.put("修正", "cosd1090.jsp");
		targetHoujin.put("ユーザー登録", "cosd1110.jsp");
		targetHoujin.put("ユーザー登録エラー", "cosd153E.jsp");
		targetHoujin.put("システムエラー", "cosd155E.jsp");
	}
	/** 県コードと県名のマッピング表 */
	Map<String, String> kenMap = null;

	/**
	 * 引数なしコンストラクタ。
	 * スーパークラスのコンストラクタを呼び出して、基本初期化処理を行う。
	 */
	public KokyakuControl() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * 顧客オブジェクトを作成して、フィールドのdataにセットする。
	 * <pre>
	 * 1．引数なしコンストラクタを呼び出し、このクラスの基本の初期化処理を行う。
	 * 2. ボタン表示文字列とJSPファイルのマッピングテーブルをセットする。
	 * 3．パッケージ名(CosDataPack)＋クラス名(className)で、オブジェクトを生成する。（顧客オブジェクトとなる）
	 * 4．生成したオブジェクトを、フィールドのdataにセットする。
	 * 5．data.setClassNameメソッドの引数に、classNameを渡してクラス名をセットする。
	 * </pre>
	 * @param className 顧客クラス名
	 * @throws ClassNotFoundException クラスロード例外
	 * @throws InstantiationException インスタンス生成例外
	 * @throws IllegalAccessException メソッド指定例外
	 */
	public KokyakuControl(String className)
			throws ClassNotFoundException,
			InstantiationException,
			IllegalAccessException {
		//引数なしコンストラクタを呼び出してJSPマップテーブルをセットする
		this();
		//Jspマップテーブルをセットする
		this.setTargetMap(targetHoujin);
		//クラス名を取得する
		Class<?> clazz = Class.forName("cosDataPack." + className);
		if (clazz != null) {
			//クラス名より顧客のインスタンスを生成
			try {
				this.data = (Kokyaku) clazz.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			//クラス名をセット
			data.setClassName(className);
		}
	}

	/**
	 * 顧客情報を取得する。
	 * @return 顧客情報
	 */
	public Kokyaku getData() {
		return data;
	}

	/**
	 * 顧客情報を設定する。
	 * @param data 顧客情報
	 */
	public void setData(Kokyaku data) {
		this.data = data;
	}

	/**
	 * 県コードと県名のマッピング表を取得する。
	 * @return 県コードと県名のマッピング表
	 */
	public Map<String, String> getKenMap() {
		return kenMap;
	}

	/**
	 * 県コードと県名のマッピング表を設定する。
	 * @param kenMap 県コードと県名のマッピング表
	 */
	public void setKenMap(Map<String, String> kenMap) {
		this.kenMap = kenMap;
	}

	/**
	 * ユーザー登録の画面で押されたボタンに対応する処理を行う。
	 * <pre>
	 * 1．スーパークラスのentryメソッドを実行して、基本処理を行う。
	 * 2．getCommandメソッドを実行して、押されたボタン情報を取得する。
	 * 3．ボタンが“新規登録”の場合、
	 *    3-1．daoのgetPrefectureメソッドを呼び出して、県コードと県名のマッピングテーブルを取得する。
	 *    3-2．setBrankメソッドで、フィールド内をブランクで埋める。
	 * 4．ボタンが“次へ”の場合、
	 *    4-1．requestのgetParameterメソッドの引数に画面の入力項目名を渡し、入力された文字列を取得する。
	 *       画面の入力項目の数だけ取得する。（メールアドレスとパスワードは各1つだけ取得する）
	 *    4-2．取得した文字列を顧客オブジェクトの該当するフィールドにアクセサメソッドを使用してセットする。
	 *       4-2-1．dataのgetClassNameでクラス名を取得し、“Houjin”の場合、次の項目を取得してセットする
	 *          業種、担当者名、担当部署名を取得し、該当フィールドにセットする。
	 * 5．ボタンが“ユーザー登録”の場合、
	 *    5-1．daoのinsertDataメソッドの引数にフィールドのdataを渡して、顧客情報をDBに登録する。
	 *    5-2．戻った値（顧客情報）のgetKokyakuErrFLGにエラーがセットされていれば（!=0）、
	 *       ボタンの情報を"ユーザー登録エラー"にセットし直す。
	 * 6．getTargetMapメソッドにボタン情報を渡し、ターゲットのJspを取得する。
	 * 7．sessionのsetAttributeメソッドで、KokyakuControlをセットする。
	 *    例）
	 *    session.setAttribute("kokyakucontrol",this);
	 * 8． requestのgetRequestDispatcherの引数に、「6．」で取得したターゲットJspを渡す。
	 * 9．「8．」の戻り値オブジェクトのforwardメソッドを実行して、Jspへフォワードする。
	 *    例）
	 *    RequestDispatcher rd = request.getRequestDispatcher(target);
	 *    rd.forward(request,response);
	 *
	 * ・ 7、8、9はWeb送受信およびセッション管理に必要。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントへレスポンスする情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 */
	public void entry(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException,
			IOException {
		super.entry(request, response);
		String target = null; //ターゲットJSP
		//画面で押されたボタンの情報を取得する
		String btn = this.getCommand();
		/*
		 * ”新規登録”が押されたとき
		 */
		if (btn.equals("新規登録")) {
			//県コードと県名のﾏｯﾋﾟﾝｸﾞテーブルを取得する
			kenMap = this.dao.getPrefecture();
			//顧客データの項目にブランクを埋める
			data.setBrank();
		}
		/*
		 * ”次へ”が押されたとき
		 */
		if (btn.equals("次へ")) {
			String ken = null;
			//”次へ”が押された場合、ユーザー情報入力画面から入力された情報を
			// 顧客データにセットする
			data.setKokyakuName((String) request.getParameter("kokyakuname"));
			data.setKokyakuKana((String) request.getParameter("kokyakukana"));
			data.setZip1((String) request.getParameter("zip1"));
			data.setZip2((String) request.getParameter("zip2"));
			ken = (String) request.getParameter("kenmap");
			//県コードを取得してセットする
			data.setKenCD(ken.substring(0, 2));
			//県名を取得してセットする
			data.setKenName(ken.substring(2));
			data.setAddress1((String) request.getParameter("address1"));
			data.setAddress2((String) request.getParameter("address2"));
			data.setTel1((String) request.getParameter("tel1"));
			data.setTel2((String) request.getParameter("tel2"));
			data.setTel3((String) request.getParameter("tel3"));
			data.setFax1((String) request.getParameter("fax1"));
			data.setFax2((String) request.getParameter("fax2"));
			data.setFax3((String) request.getParameter("fax3"));
			data.setMail((String) request.getParameter("mail1"));
			data.setPassword((String) request.getParameter("password1"));
			//法人固有データのセット
			((Houjin) data).setGyoushu((String) request.getParameter("gyoushu"));
			((Houjin) data).setTantouName((String) request.getParameter("tantouname"));
			((Houjin) data).setTantoubusho((String) request.getParameter("tantoubusho"));
		}
		/*
		 * ”ユーザー登録”が押されたとき
		 */
		if (btn.equals("ユーザー登録")) {
			//Daoのインサートメソッドに渡す
			data = dao.insertData(data);
			if (data.getKokyakuErrFLG() != 0) {
				//何らかのエラーがあった場合、ボタン文字列をエラー用に置き換えて
				//エラーの画面に転送するようにする
				if (data.getKokyakuErrFLG() == 1) {
					btn = "ユーザー登録エラー";
				} else {
					btn = "システムエラー";
				}
			}
		}
		/*
		 * JSP（画面）へ転送するための処理
		 */
		//ターゲット（JSP)の取得
		target = getTargetMap(btn);
		//顧客コントロール及び必要なデータをセッションにセットして
		//JSPへフォワードする
		session.setAttribute("kokyakucontrol", this);
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}
}

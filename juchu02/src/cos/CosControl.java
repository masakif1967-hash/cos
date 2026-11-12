package cos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cosDAO.DaoControl;
import cosDataPack.Kokyaku;

/**
 * CosSystem受付クラス。
 * CosServlet経由で全てのリクエストが送られる。
 * リクエストは受注サブシステムの大きな機能である、 「ユーザー登録」と「受注処理」に振り分け、
 * 各機能のコントロールへリクエスト情報、 レスポンス情報を渡して処理を移譲する
 * @version 1.0
 */
public class CosControl {
	/** DBアクセス用コントロール */
	private static DaoControl dao;

	/**
	 * 引数なしコンストラクタ
	 * <pre>
	 * 1．DaoControlのオブジェクトを作成し、フィールドのdaoにセットする。
	 *    これにより、DBをアクセスできるようにスタンバイする。
	 * </pre>
	 */
	public CosControl() {
		//DBアクセス用DAOコントロールを作成する
		dao = new DaoControl();
	}

	/**
	 * サーブレットが受け付けた情報は全てこのメソッドに送られる。
	 * 入力されたボタンの情報により、ユーザー登録処理、受注処理、ログオフ処理に振り分ける。
	 * <pre>
	 * 1．BaseControlの変数ctlを定義する。
	 * 2．request.getSessionを実行してSessionオブジェクトを取得する。
	 * 3．request.getParameterの引数に“top”を渡して、topボタンが押された際の情報を取得する。
	 *    3-1. topボタンが押されていれば（!=null）、
	 *       responseとsessionオブジェクトをNinshouControl.logoff の引数に渡してログオフ処理を行う。
	 * 4．request.getParameterの引数に“order”を渡して、orderボタンが押された際の情報を取得する。
	 *    4-1．orderボタンが押されていれば（!=null）、
	 *       4-1-1．sessionオブジェクトがなければ（==null）、
	 *          4-1-1-1．ログオン直後とし、NinshouControlオブジェクトを作成する。
	 *          4-1-1-2．NinshouControlのsetDaoの引数にフィールドdaoを渡してDaoControlをセットする。
	 *          4-1-1-3．request.getParameterの引数に“userID”を渡して、画面より入力されたユーザーIDを取得する。
	 *                  同様に、request.getParameterの引数に“password”を渡して、画面より入力されたパスワードを取得する。
	 *          4-1-1-4．ユーザーIDとパスワードで、NinshouControlのlogonを呼び出して、ログオン処理を行う。ここでログオン認証ができれば顧客情報が返る。
	 *          4-1-1-5．顧客情報が戻ってこなければ、NinshouControlのlogonErrを呼び出して、ログオンエラー処理を行う。
	 *          4-1-1-6．ログオン認証で受けとった顧客情報を引数としてJuchuControlオブジェクトを生成し、ctl変数にセットする。
	 *       4-1-2．sessionオブジェクトがあれば（!=null）、
	 *          4-1-2-1．sessionのgetAttibuteメソッドの引数に“juchucontrol”を渡し、セッション中のJuchuControlオブジェクトを取得し、ctl変数にセットする。
	 * 5．request.getParameterの引数に“user”を渡して、userボタンが押された際の情報を取得する。
	 *    5-1．userボタンが押されていれば（!=null）、
	 *       5-1-1．sessionオブジェクトがなければ（==null）、
	 *          5-1-1-1．新規登録ボタンを押した直後とし、KokyakuControlオブジェクトを生成してctl変数にセットする。
	 *       5-1-2．sessionオブジェクトがあれば（!=null）、
	 *          5-1-2-1．sessionのgetAttibuteメソッドの引数に“kokyakucontrol”を渡し、セッション中のKokyakuControlオブジェクトを取得し、ctl変数にセットする。
	 * 6．ctl変数に、オブジェクトがセットされていれば（!=null）、
	 *    6-1．コントロールオブジェクト（ctl変数）にDaoをセットする。
	 *    6-2．コントロールオブジェクト（ctl変数）のsetCommandに画面から押されたボタン情報をセットする。
	 *    6-3．requestとresponseを引数として、コントロールオブジェクト（ctl変数）のentryメソッドを呼び出す。
	 *
	 * ・ 受注処理の画面から押される全てのボタンには、「name」プロパティに“order”がセットされている。
	 *  このため、request.getParameter(“order”)で、nullでなければ、受注処理画面の何れかのボタンが押されたことになる。
	 * ・ ユーザー登録画面から押される全てのボタンには、「name」プロパティに“user”がセットされている。
	 *  このため、request.getParameter(“user”)で、nullでなければ、ユーザー登録画面の何れかのボタンが押されたことになる。
	 * ・ 「トップへ戻る」または「ログオフ」ボタンには、「name」プロパティに“top”がセットされている。
	 *  このため、何れの画面でも「トップへ戻る」または「ログオフ」ボタンが押されたことになる。
	 * ・ このメソッドでは、ボタンのrequest.getParameterで取得された情報により、「受注処理」「ユーザー登録処理」「ログオフ処理」に分けている
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントからのレスポンス情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 * @throws ClassNotFoundException クラスロード例外
	 * @throws InstantiationException インスタンス生成例外
	 * @throws IllegalAccessException メソッド指定例外
	 */
	public static void action(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException,
			IOException,
			ClassNotFoundException,
			InstantiationException,
			IllegalAccessException {
		BaseControl ctl = null; //コントロール系オブジェクト
		NinshouControl nctl = null; //認証コントロールオブジェクト
		Kokyaku kokyaku = null; //顧客データ
		String requestBuf; //getRequestで取得する情報を格納するバッファ
		String btn = null; //ボタン情報
		//セッションの取得
		HttpSession session = request.getSession(false); //セッションオブジェクト
		/*
		 * ログオフ処理
		 * "トップページへ"または"ログオフ"のボタンが押された場合
		 * 画面のボタンのname=”top”⇒ログオフ処理
		 */
		requestBuf = request.getParameter("top");
		if (requestBuf != null) {
			//ボタン情報をセットする
			btn = requestBuf;
			//認証コントロールのログオフ処理を呼び出す
			NinshouControl.logoff(response, session);
		}
		/*
		 * 受注処理の場合
		 * 画面のボタンのname=”order”⇒受注処理
		 * ログオンも受注処理となる
		 */
		requestBuf = request.getParameter("order");
		if (requestBuf != null) {
			//ボタン情報をセットする
			btn = requestBuf;
			if (session == null) {
				//セッションが無い場合は、ログオン処理
				/*
				 * ログオン認証処理
				 */
				//入力されたユーザーIDとパスワードで認証コントロールにより
				//ログオン処理を行う
				nctl = new NinshouControl();
				nctl.setDao(dao);
				kokyaku = (Kokyaku) nctl.logon(request.getParameter("userId"),
						request.getParameter("password"));
				if (kokyaku == null) { //顧客データがnullの場合、ログオンNGとなる
					//ログオエラーを呼び出す
					nctl.logonErr(response);
				} else {
					//ログオンOKの場合、
					//顧客データを渡して受注コントロールを作成する
					ctl = new JuchuControl(kokyaku);
				}
			} else {
				//セッションがnullでない場合、2回目以降のアクセスとなるため、
				//セッションより受注コントロールを取得
				session = request.getSession();
				//セッションより受注コントロールを取得してコントロールにセットする
				ctl = (JuchuControl) session.getAttribute("juchucontrol");
			}
		}
		/*
		 * ユーザー登録処理の場合
		 * 画面のボタンのname=”user”⇒ユーザー登録処理
		 *
		 */
		requestBuf = request.getParameter("user");
		if (requestBuf != null) {
			btn = requestBuf;
			String className = null; //クラス名
			if (session == null) {
				//セッションが無い場合は、トップページからのアクセス（1回目の呼び出し）
				//ラジオボタンよりクラス名を取得
				className = request.getParameter("kokyaku");
				//クラス名により顧客コントロールを生成
				ctl = new KokyakuControl(className);
			} else {
				//セッションがnullでない場合は、2回目以降のアクセスとなるので
				//セッションより顧客コントロールを取得
				session = request.getSession();
				//セッションより顧客コントロールを取得してコントロールにセットする
				ctl = (KokyakuControl) session.getAttribute("kokyakucontrol");
			}
		}
		/*
		 * 「ユーザー登録」、「受注」の共通処理
		 * 各機能の登録処理（entry）を呼び出す
		 */
		if (ctl != null) {
			//コントロールにDAOをセット
			ctl.setDao(dao);
			//コントロールにボタン情報をセットする
			ctl.setCommand(btn);
			//データの登録処理へ（ユーザー登録、受注登録）
			ctl.entry(request, response);
		}
	}

	/**
	 * Cosシステムのクローズ処理を行う
	 * <pre>
	 * 1．DaoControlのオブジェクト（dao）のconnectionCloseメソッドを呼び出し、DBのクローズ処理を行う。
	 * </pre>
	 */
	public static void close() {
		//DaoControlのクローズメソッドを呼び出す
		dao.connectionClose();
	}
}

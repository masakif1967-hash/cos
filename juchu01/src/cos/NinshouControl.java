package cos;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cosDataPack.Kokyaku;

/**
 * 認証コントロールクラス。
 * ユーザー認証に必要な機能を提供するクラス。
 * ログオン処理、ログオフ処理の機能をもつ。
 * CosControlで呼び出される。
 * @version 1.0
 */
public class NinshouControl extends BaseControl {
	/**
	 * 引数なしコンストラクタ。
	 */
	public NinshouControl() {
		super();
	}

	/**
	 * ユーザーのログオン認証を行う。
	 * <pre>
	 * 1．ユーザーIDの先頭1文字を取得する。
	 * 2．ユーザーIDの先頭1文字が1～3の場合、
	 *    2-1．classNameに"Houjin"をセットする。
	 * 3．｢1.,2．｣以外の場合
	 *    3-1．nullをリターンする。
	 * 4．文字の場合、
	 *    4-1．nullをリターンする
	 * 5．daoのselectKokyakuメソッドの引数に、kokyakuCD,password,classNameを渡し、顧客情報を取得する。
	 * 6．顧客情報を取得できれば（!=null）、
	 *    6-1．取得した顧客情報の setClassNameメソッドでclassNameをセットする。
	 *    6-2．取得した顧客情報をリターンする。
	 * 7．顧客情報を取得できなければ（==null）、
	 *    7-1．nullをリターンする。
	 *
	 * ・ 「4. 文字の場合、」、先頭文字を数値に変換し（Interger.parseInt（））、その際の例外処理（NumberFormatExceptin）で行う。
	 * </pre>
	 * @param kokyakuCD 顧客コード
	 * @param password パスワード
	 * @return 顧客情報
	 */
	public Kokyaku logon(String kokyakuCD,
			String password) {
		Kokyaku data = null; //顧客データ
		String className = null; //クラス名
		int id = 0;
		//顧客コードの先頭文字を取得して、法人の顧客コードか否かを判断して、
		//クラス名をセットする
		try {
			id = Integer.parseInt(kokyakuCD.substring(0, 1));
		} catch (Exception e) {
			//文字の場合はログオンエラー
			//nullがセットされたdataをリターン
			return data;
		}
		//100000～399999→顧客法人
		if (id >= 1 && id < 4) {
			className = "Houjin";
		} else {
			//上記以外はログオン失敗
			//nullがセットされたdataをリターン
			return data;
		}
		//ユーザーID、パスワード、クラス名をDAOに渡してログオンチェックする
		data = dao.selectKokyaku(kokyakuCD, password, className);
		if (data != null) {
			//顧客データにクラス名をセットする
			data.setClassName(className);
		}
		return data;
	}

	/**
	 * ユーザーのログオンエラーの処理を行う。
	 * <pre>
	 * 1．responseのsendRedirectの引数にログオンエラー画面(cosd150E.jsp)を渡して、リダイレクトする。
	 * </pre>
	 * @param response Webクライアントへレスポンスする情報
	 */
	public void logonErr(HttpServletResponse response) {
		String target = "cosd150E.jsp"; //ログオンエラーの画面(JSP)
		try {
			//エラー画面へリダイレクトする
			response.sendRedirect(target);
		} catch (IOException e) {
			System.out.println("sendRedirect エラー");
			e.printStackTrace();
		}
	}

	/**
	 * ユーザーのログオフ処理を行う。
	 * <pre>
	 * 1．sessionが有効であれば（!=null）、
	 *    1-1．sessionのinvalidateメソッドを実行して、セッションを無効にする。
	 * 2．responseのsendRedirectの引数にトップページ(cosd1000.html)を渡して、リダイレクトする。
	 * </pre>
	 * @param response Webクライアントからのリクエスト情報
	 * @param session セッション
	 */
	public static void logoff(HttpServletResponse response, HttpSession session) {
		String target = "cosd1000.html"; //トップ画面
		if (session != null) {
			//セッションを無効にする
			session.invalidate();
		}
		try {
			//target（トップ画面）に転送
			response.sendRedirect(target);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
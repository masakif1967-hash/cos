package cos;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cosDAO.DaoControl;

/**
 * 基本コントロールクラス。
 * コントロール系クラスのスーパークラス。各コントロールクラスの共通機能を持つ。
 * @version 1.0
 */
public abstract class BaseControl {
	/** DBアクセス用コントロール */
	protected DaoControl dao;
	/** ボタンとJSPファイルのマッピング表 */
	private Map<String, String> targetMap;
	/** コマンド（画面のボタン情報） */
	private String command;
	/** セッション */
	protected HttpSession session;

	/**
	 * 引数なしコンストラクタ。
	 */
	public BaseControl() {
		super();
	}

	/**
	 * 引数ありコンストラクタ。
	 * @param dao DBアクセス用コントロール
	 */
	public BaseControl(DaoControl dao) {
		super();
		this.dao = dao;
	}

	/**
	 * DBアクセス用コントロールを取得する。
	 * @return DBアクセス用コントロール
	 */
	public DaoControl getDao() {
		return this.dao;
	}

	/**
	 * DBアクセス用コントロールを設定する。
	 * @param dao DBアクセス用コントロール
	 */
	public void setDao(DaoControl dao) {
		this.dao = dao;
	}

	/**
	 * ボタンとJSPファイルのマッピング表を取得する。
	 * @return ボタンとJSPファイルのマッピング表
	 */
	public Map<String, String> getTargetMap() {
		return targetMap;
	}

	/**
	 * ボタンとJSPファイルのマッピング表を設定する。
	 * @param targetMap ボタンとJSPファイルのマッピング表
	 */
	public void setTargetMap(Map<String, String> targetMap) {
		this.targetMap = targetMap;
	}

	/**
	 * コマンドを取得する。
	 * @return コマンド
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * コマンドを設定する。
	 * @param command コマンド
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Cos受注システムのユーザー登録や受注処理の基本処理を行う。
	 * <pre>
	 * 1．requestのgetSessionメソッドを実行し、sessionオブジェクトを取得する。
	 * 2．フィールドsessionにセットする。
	 * 3．responseのsetContentTypeメソッドに"text/html; charset=UTF-8"を渡して、コンテトタイプのセットを行う。
	 * 4．requestのsetCharacterEncodingメソッドに"UTF-8"を渡して、文字セットを行う。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントからのレスポンス情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 */
	public void entry(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//セッションの取得
		session = request.getSession();
		//コンテントタイプのセット
		response.setContentType("text/html; charset=UTF-8");
		//キャラクタ文字セット
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * 押されたボタンに対応するJspを取得する。
	 * @param btn ボタン
	 * @return JSPファイル
	 */
	protected String getTargetMap(String btn) {
		return targetMap.get(btn);
	}
}

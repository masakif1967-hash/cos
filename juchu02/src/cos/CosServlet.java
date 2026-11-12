package cos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cosサーブレットクラス。
 * Web受付のサーブレット。画面からのリクエストは全てこのサーブレットが受け付ける。
 * ただし、受け付けのみで実際の処理は、CosControlで行われるため、リクエスト情報とレスポンス情報をCosControlへ渡す。
 * @version 1.0
 */
public class CosServlet extends HttpServlet {
	/** シリアライズを宣言したクラスのバージョン */
	private static final long serialVersionUID = 1L;
	/** Cosコントロールのオブジェクト */
	private CosControl cos;

	/**
	 * 引数なしコンストラクタ。
	 */
	public CosServlet() {
		super();
	}

	/**
	 * Cosコントロールのオブジェクトを取得する。
	 * @return Cosコントロールのオブジェクト
	 */
	public CosControl getCos() {
		return cos;
	}

	/**
	 * Cosコントロールのオブジェクトを設定する。
	 * @param cos Cosコントロールのオブジェクト
	 */
	public void setCos(CosControl cos) {
		this.cos = cos;
	}

	/**
	 * サーブレットの初期化およびCos受注システムを利用可能にする。
	 * <pre>
	 * 1．スーパークラスのinitメソッドを実行し、サーブレット初期化処理を行う。
	 * 2．CosControlのオブジェクトを作成し、フィールドにセットする。
	 *    これにより、システムのWebサービスの受け付けがスタンバイされる。
	 *
	 * ・ サーブレットのスーパークラスで定義されているメソッドであり、必要に応じてオーバーライドするメソッドである。
	 * ・ サーブレットの初回呼び出し時に実行されるメソッドで、2回目以降の呼び出しはない、そのためサーブレットの初期化処理として利用される。
	 * ・ 今回は、Cosコントロールを生成してCosSystem Webオンラインショッピングを利用できるようにする。
	 * </pre>
	 * @throws ServletException サーブレット例外
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		//CosControlのオブジェクトを作成することで、Daoを登録する
		cos = new CosControl();
	}

	/**
	 * Webクライアントの送受信をCosControlへそのまま引き渡す。
	 * <pre>
	 * 1．requestと responseをCosControlのactionメソッドの引数に渡して実行する。
	 *    （このメソッドでは処理をせずに、全てCosControlのactionメソッドに渡して処理をする）
	 *
	 * ・サーブレットのスーパークラスで定義されているメソッドであり、必要に応じてオーバーライドするメソッドである。
	 * ・WebクライアントからPOSTメソッドにより呼び出されるメソッドで、通常はPOSTメソッドで呼ばれる際に実行するロジックを記述する。
	 *    （今回の呼び出しは、全てPOSTメソッドによる呼び出しをしている）
	 * ・サーブレット初回呼び出しは、initメソッドの後に呼び出される。
	 * ・今回は、CosControlのactionメソッドを呼び出して、全ての処理はCosCosntorolで行う。
	 *    例）cosd1000.htmlからの呼び出しform action="CosServlet" method="post"
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントからのレスポンス情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			//CosContorolのアクションメソッドへリクエストとレスポンスの情報を渡す
			CosControl.action(request, response);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * サーブレット終了処理。
	 * <pre>
	 * 1．CosControlのcloseメソッドを呼び出して、Cosシステムの終了処理を行う。
	 * 2．スーパークラスのdoDeleteを呼び出して、サーブレットの終了処理をする。
	 *
	 * ・ サーブレットのスーパークラスで定義されているメソッドであり、必要に応じてオーバーライドするメソッドである。
	 * ・ サーブレットオブジェクトがアンロードされる際に呼び出されるため、サーブレットの終了処理として利用される。
	 * ・ 今回は、DBのコネクションをクローズする処理を実行する。
	 * </pre>
	 * @param request Webクライアントからのリクエスト情報
	 * @param response Webクライアントからのレスポンス情報
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力例外
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//CosControlのクローズメソッド呼び出し
		CosControl.close();
		super.doDelete(request, response);
	}
}

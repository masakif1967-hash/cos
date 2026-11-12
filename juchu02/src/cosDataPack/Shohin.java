package cosDataPack;

/**
 * 商品クラス。
 * 商品関連のスーパークラス（Bungu,Book,Foods,Av,Pcのスーパークラス）。各商品の共通情報と機能を持つ。
 * @version 1.0
 */
public class Shohin {
	/** 商品基本情報 */
	private ShohinPrimalData shohinPriData;
	/** 商品カテゴリ情報と商品サブカテゴリ情報 */
	private ShohinCategory shohinCtgInfo;
	/** 入り数 */
	private int irisu;
	/** 単位 */
	private String tanni;
	/** メーカーコード */
	private String makerCD;
	/** メーカー名 */
	private String makerName;
	/** 商品コメント */
	private String comments;
	/** 画像データのURL */
	private String imgURL;
	/** 在庫数 */
	private int zaikoCnt;
	/** 引き当て数 */
	private int hikiateCnt;
	/** 在庫情報表示文字列 */
	private String zaikoHyouji;
	/** 商品種別 */
	private String shubetu;
	/** その他の情報 */
	private String other;
	/** 在庫情報表示文字列“在庫あり” */
	public static final String ZAIKO_OK = "在庫あり";
	/** 在庫情報表示文字列“在庫僅少” */
	public static final String ZAIKO_FEW = "在庫僅少";
	/** 在庫情報表示文字列“在庫なし” */
	public static final String ZAIKO_NO = "在庫なし";
	/** 在庫表示基準値（“在庫僅少”となる値） */
	private static final int ZAIKO_FEW_VALUE = 9;
	/** 在庫表示基準値（“在庫なし”となる値） */
	private static final int ZAIKO_NO_VALUE = 0;

	/**
	 * 引数なしコンストラクタ。
	 */
	public Shohin() {
		super();
	}

	/**
	 * 商品基本情報を取得する。
	 * @return 商品基本情報
	 */
	public ShohinPrimalData getShohinPriData() {
		return shohinPriData;
	}

	/**
	 * 商品基本情報を設定する。
	 * @param shohinPriData 商品基本情報
	 */
	public void setShohinPriData(ShohinPrimalData shohinPriData) {
		this.shohinPriData = shohinPriData;
	}

	/**
	 * 商品カテゴリ情報と商品サブカテゴリ情報を取得する。
	 * @return 商品カテゴリ情報と商品サブカテゴリ情報
	 */
	public ShohinCategory getShohinCtgInfo() {
		return shohinCtgInfo;
	}

	/**
	 * 商品カテゴリ情報と商品サブカテゴリ情報を設定する。
	 * @param shohinCtgInfo 商品カテゴリ情報と商品サブカテゴリ情報
	 */
	public void setShohinCtgInfo(ShohinCategory shohinCtgInfo) {
		this.shohinCtgInfo = shohinCtgInfo;
	}

	/**
	 * 入り数を取得する。
	 * @return 入り数
	 */
	public int getIrisu() {
		return irisu;
	}

	/**
	 * 入り数を設定する。
	 * @param irisu 入り数
	 */
	public void setIrisu(int irisu) {
		this.irisu = irisu;
	}

	/**
	 * 単位を取得する。
	 * @return 単位
	 */
	public String getTanni() {
		return tanni;
	}

	/**
	 * 単位を設定する。
	 * @param tanni 単位
	 */
	public void setTanni(String tanni) {
		this.tanni = tanni;
	}

	/**
	 * メーカーコードを取得する。
	 * @return メーカーコード
	 */
	public String getMakerCD() {
		return makerCD;
	}

	/**
	 * メーカーコードを設定する。
	 * @param makerCD メーカーコード
	 */
	public void setMakerCD(String makerCD) {
		this.makerCD = makerCD;
	}

	/**
	 * メーカー名を取得する。
	 * @return メーカー名
	 */
	public String getMakerName() {
		return makerName;
	}

	/**
	 * メーカー名を設定する。
	 * @param makerName メーカー名
	 */
	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}

	/**
	 * 商品コメントを取得する。
	 * @return 商品コメント
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 商品コメントを設定する。
	 * @param comments 商品コメント
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * 画像データのURLを取得する。
	 * @return 画像データのURL
	 */
	public String getImgURL() {
		return imgURL;
	}

	/**
	 * 画像データのURLを設定する。
	 * @param imgURL 画像データのURL
	 */
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	/**
	 * 在庫数を取得する。
	 * @return 在庫数
	 */
	public int getZaikoCnt() {
		return zaikoCnt;
	}

	/**
	 * 在庫数を設定する。
	 * @param zaikoCnt 在庫数
	 */
	public void setZaikoCnt(int zaikoCnt) {
		this.zaikoCnt = zaikoCnt;
	}

	/**
	 * 引き当て数を取得する。
	 * @return 引き当て数
	 */
	public int getHikiateCnt() {
		return hikiateCnt;
	}

	/**
	 * 引き当て数を設定する。
	 * @param hikiateCnt 引き当て数
	 */
	public void setHikiateCnt(int hikiateCnt) {
		this.hikiateCnt = hikiateCnt;
	}

	/**
	 * 在庫情報表示文字列を取得する。
	 * @return 在庫情報表示文字列
	 */
	public String getZaikoHyouji() {
		return zaikoHyouji;
	}

	/**
	 * 在庫情報表示文字列を設定する。
	 * @param zaikoHyouji 在庫情報表示文字列
	 */
	public void setZaikoHyouji(String zaikoHyouji) {
		this.zaikoHyouji = zaikoHyouji;
	}

	/**
	 * 商品種別を取得する。
	 * @return 商品種別
	 */
	public String getShubetu() {
		return shubetu;
	}

	/**
	 * 商品種別を設定する。
	 * @param shubetu 商品種別
	 */
	public void setShubetu(String shubetu) {
		this.shubetu = shubetu;
	}

	/**
	 * その他の情報を取得する。
	 * @return その他の情報
	 */
	public String getOther() {
		return other;
	}

	/**
	 * その他の情報を設定する。
	 * @param other その他の情報
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * 在庫表示文字列を設定する。
	 * <pre>
	 * 在庫の値より、在庫表示基準値に相当した在庫表示文字列を設定する。
	 *    引き当て可能数 ＞＝ 10                    ⇒ ”在庫あり”
	 *    0  ＜ 引き当て可能数 ＜＝ 9             ⇒ ”在庫僅少”
	 *    引き当て可能数 ＜＝ 0                      ⇒ ”在庫なし”
	 * </pre>
	 */
	@SuppressWarnings("static-access")
	public void setZaikoHyouji() {
		//在庫数と引き当て数から引き当て可能数を取得する
		int hikiateKanou = this.zaikoCnt - this.hikiateCnt;
		if (hikiateKanou > ZAIKO_FEW_VALUE) {
			//引き当て可能数＞9⇒”在庫あり”
			this.zaikoHyouji = this.ZAIKO_OK;
		} else if (hikiateKanou > ZAIKO_NO_VALUE) {
			//引き当て可能数＜= 9 && 引き当て可能数 ＞0 ⇒”在庫僅少”
			this.zaikoHyouji = this.ZAIKO_FEW;
		} else {
			//引き当て可能数＜0⇒”在庫なし”
			this.zaikoHyouji = this.ZAIKO_NO;
		}
	}
}

package cosDataPack;

/**
 * 受注明細クラス。
 * 受注情報の明細部を保持するクラス。１明細分のオブジェクトとなる。この受注明細のリストが受注のメンバーとなる。
 * @version 1.0
 */
public class Meisai {
	/** 明細行番号（明細番号） */
	private String lineNum;
	/** 商品基本情報 */
	private ShohinPrimalData shohinPriData;
	/** 数量 */
	private int suryou;
	/** 金額（販売単価×数量） */
	private int kingaku;
	/** 納品予定日 */
	private String nouhinYoteiD;
	/** 納品予定時間帯 */
	private String nouhinYoteiRange;
	/** 明細エラーフラグ（0:OK, 1:引当不可NG, 9:SQLエラー） */
	private int meisaiErrFLG;

	/**
	 * 引数なしコンストラクタ
	 */
	public Meisai() {
		super();
	}

	/**
	 * 商品基本情報を取得する
	 * @return 商品基本情報
	 */
	public ShohinPrimalData getShohinPriData() {
		return shohinPriData;
	}

	/**
	 * 商品基本情報を設定する
	 * @param shohinPriData 商品基本情報
	 */
	public void setShohinPriData(ShohinPrimalData shohinPriData) {
		this.shohinPriData = shohinPriData;
	}

	/**
	 * 明細行番号を取得する
	 * @return 明細行番号
	 */
	public String getLineNum() {
		return lineNum;
	}

	/**
	 * 明細行番号を設定する
	 * @param lineNum 明細行番号
	 */
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * 数量を取得する
	 * @return 数量
	 */
	public int getSuryou() {
		return suryou;
	}

	/**
	 * 数量を設定する
	 * @param suryou 数量
	 */
	public void setSuryou(int suryou) {
		this.suryou = suryou;
	}

	/**
	 * 金額を取得する
	 * @return 金額
	 */
	public int getKingaku() {
		return kingaku;
	}

	/**
	 * 金額を設定する
	 */
	public void setKingaku() {
		this.kingaku = this.suryou * this.shohinPriData.getHtanka();
	}

	/**
	 * 納品予定日を取得する
	 * @return 納品予定日
	 */
	public String getNouhinYoteiD() {
		return nouhinYoteiD;
	}

	/**
	 * 納品予定日を設定する
	 * @param nouhinYoteiD 納品予定日
	 */
	public void setNouhinYoteiD(String nouhinYoteiD) {
		this.nouhinYoteiD = nouhinYoteiD;
	}

	/**
	 * 納品予定時間帯を取得する
	 * @return 納品予定時間帯
	 */
	public String getNouhinYoteiRange() {
		return nouhinYoteiRange;
	}

	/**
	 * 納品予定時間帯を設定する
	 * @param nouhinYoteiRange 納品予定時間帯
	 */
	public void setNouhinYoteiRange(String nouhinYoteiRange) {
		this.nouhinYoteiRange = nouhinYoteiRange;
	}

	/**
	 * 明細エラーフラグを取得する
	 * @return 明細エラーフラグ
	 */
	public int getMeisaiErrFLG() {
		return meisaiErrFLG;
	}

	/**
	 * 明細エラーフラグを設定する
	 * @param meisaiErrFLG 明細エラーフラグ
	 */
	public void setMeisaiErrFLG(int meisaiErrFLG) {
		this.meisaiErrFLG = meisaiErrFLG;
	}
}

package cosDataPack;

/**
 * 商品基本情報クラス。
 * 商品の基本情報。商品コード、商品名、販売単価など明細に必要な情報をまとめたもの。
 * @version 1.0
 */
public class ShohinPrimalData {
	/** 商品コード */
	private String goodsCD;
	/** 商品名 */
	private String goodsName;
	/** 販売単価 */
	private int htanka;

	/**
	 * 引数なしコンストラクタ。
	 */
	public ShohinPrimalData() {
		super();
	}

	/**
	 * 商品コードを取得する。
	 * @return 商品コード
	 */
	public String getGoodsCD() {
		return goodsCD;
	}

	/**
	 * 商品コードを設定する。
	 * @param goodsCD 商品コード
	 */
	public void setGoodsCD(String goodsCD) {
		this.goodsCD = goodsCD;
	}

	/**
	 * 商品名を取得する。
	 * @return 商品名
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 商品名を設定する。
	 * @param goodsName 商品名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 販売単価を取得する。
	 * @return 販売単価
	 */
	public int getHtanka() {
		return htanka;
	}

	/**
	 * 販売単価を設定する。
	 * @param htanka 販売単価
	 */
	public void setHtanka(int htanka) {
		this.htanka = htanka;
	}
}

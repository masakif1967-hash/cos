package cosDataPack;

/**
 * 文具クラス。
 * 文具情報を保持するクラス。文具商品に特有な情報と機能をもつ。
 * @version 1.0
 */
public class Bungu extends Shohin {
	/** 色 */
	private String color;
	/** 型番 */
	private String kataban;
	/** 寸法 */
	private String size;
	/** 材質 */
	private String zaisitu;

	/**
	 * 引数なしコンストラクタ。
	 */
	public Bungu() {
		super();
	}

	/**
	 * 色を取得する。
	 * @return 色
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 色を設定する。
	 * @param color 色
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 型番を取得する。
	 * @return 型番
	 */
	public String getKataban() {
		return kataban;
	}

	/**
	 * 型番を設定する。
	 * @param kataban 型番
	 */
	public void setKataban(String kataban) {
		this.kataban = kataban;
	}

	/**
	 * 寸法を取得する。
	 * @return 寸法
	 */
	public String getSize() {
		return size;
	}

	/**
	 * 寸法を設定する。
	 * @param size 寸法
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 材質を取得する。
	 * @return 材質
	 */
	public String getZaisitu() {
		return zaisitu;
	}

	/**
	 * 材質を設定する。
	 * @param zaisitu 材質
	 */
	public void setZaisitu(String zaisitu) {
		this.zaisitu = zaisitu;
	}
}

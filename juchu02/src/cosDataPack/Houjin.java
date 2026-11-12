package cosDataPack;

/**
 * 顧客法人クラス。
 * 法人情報を保持するクラス。法人特有な情報と機能を持つ。
 * @version 1.0
 */
public class Houjin extends Kokyaku {
	/** 業種 */
	private String gyoushu;
	/** 担当者名 */
	private String tantouName;
	/** 担当部署 */
	private String tantoubusho;

	/**
	 * 引数なしコンストラクタ。
	 */
	public Houjin() {
		super();
	}

	/**
	 * 業種を取得する。
	 * @return 業種
	 */
	public String getGyoushu() {
		return gyoushu;
	}

	/**
	 * 業種を設定する。
	 * @param gyoushu 業種
	 */
	public void setGyoushu(String gyoushu) {
		this.gyoushu = gyoushu;
	}

	/**
	 * 担当者名を取得する。
	 * @return 担当者名
	 */
	public String getTantouName() {
		return tantouName;
	}

	/**
	 * 担当者名を設定する。
	 * @param tantouName 担当者名
	 */
	public void setTantouName(String tantouName) {
		this.tantouName = tantouName;
	}

	/**
	 * 担当部署を取得する。
	 * @return 担当部署
	 */
	public String getTantoubusho() {
		return tantoubusho;
	}

	/**
	 * 担当部署を設定する。
	 * @param tantoubusho 担当部署
	 */
	public void setTantoubusho(String tantoubusho) {
		this.tantoubusho = tantoubusho;
	}

	/**
	 * フィールドをブランクで埋める（画面表示用）。
	 */
	@Override
	public void setBrank() {
		super.setBrank();
		this.setGyoushu("");
		this.setTantouName("");
		this.setTantoubusho("");
	}
}

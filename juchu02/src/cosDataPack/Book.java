package cosDataPack;

/**
 * 書籍クラス。
 * 書籍情報を保持するクラス。書籍商品に特有な情報と機能を持つ。
 * @version 1.0
 */
public class Book extends Shohin {
	/** 巻数 */
	private String volume;
	/** ISBN-10 */
	private String isbn10;
	/** ISBN-13 */
	private String isbn13;
	/** 著者 */
	private String writer;
	/** ページ数 */
	private String page;
	/** 発行日 */
	private String hakkouD;
	/** 寸法 */
	private String size;

	/**
	 * 引数なしコンストラクタ。
	 */
	public Book() {
		super();
	}

	/**
	 * 巻数を取得する。
	 * @return 巻数
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * 巻数を設定する。
	 * @param volume 巻数
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * ISBN-10を取得する。
	 * @return ISBN-10
	 */
	public String getIsbn10() {
		return isbn10;
	}

	/**
	 * ISBN-10を設定する。
	 * @param isbn10 ISBN-10
	 */
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	/**
	 * ISBN-13を取得する。
	 * @return ISBN-13
	 */
	public String getIsbn13() {
		return isbn13;
	}

	/**
	 * ISBN-13を設定する。
	 * @param isbn13 ISBN-13
	 */
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	/**
	 * 著者を取得する。
	 * @return 著者
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * 著者を設定する。
	 * @param writer 著者
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * ページ数を取得する。
	 * @return ページ数
	 */
	public String getPage() {
		return page;
	}

	/**
	 * ページ数を設定する。
	 * @param page ページ数
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * 発行日を取得する。
	 * @return 発行日
	 */
	public String getHakkouD() {
		return hakkouD;
	}

	/**
	 * 発行日を設定する。
	 * @param hakkouD 発行日
	 */
	public void setHakkouD(String hakkouD) {
		this.hakkouD = hakkouD;
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
}

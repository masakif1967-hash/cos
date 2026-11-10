package cosDataPack;

/**
 * 顧客クラス。
 * 顧客のスーパークラス。将来のために顧客の共通情報と機能を持つ
 * @version 1.0
 */
public class Kokyaku {
	/** クラス名 */
	private String className;
	/** 顧客コード */
	private String kokyakuCD;
	/** 顧客名 */
	private String kokyakuName;
	/** 顧客名カナ */
	private String kokyakuKana;
	/** 郵便番号1（上3ケタ） */
	private String zip1;
	/** 郵便番号2（下4ケタ） */
	private String zip2;
	/** 県コード（都道府県コード） */
	private String kenCD;
	/** 県名（都道府県） */
	private String kenName;
	/** 住所１（県名を除く、市・区・群） */
	private String address1;
	/** 住所２（住所１以降） */
	private String address2;
	/** 電話番号１（市外局番） */
	private String tel1;
	/** 電話番号２（局番） */
	private String tel2;
	/** 電話番号３（下4ケタ） */
	private String tel3;
	/** FAX番号１（市外局番） */
	private String fax1;
	/** FAX番号２（局番） */
	private String fax2;
	/** FAX番号３（下4ケタ） */
	private String fax3;
	/** メールアドレス */
	private String mail;
	/** 売掛け残高（顧客から未回収の金額） */
	private int urikake_zan;
	/** 与信限度額（売掛残高も含めた購入可能金額） */
	private int yoshin_gendo;
	/** 営業所コード（住所により管轄営業所が決定） */
	private String eigyoshoCD;
	/** ユーザー登録日 */
	private String tourokuD;
	/** パスワード */
	private String password;
	/** 顧客エラーフラグ (0:OK, 1:登録済み, 9:SQLエラー) */
	private int kokyakuErrFLG;

	/**
	 * 引数なしコンストラクタ。
	 */
	public Kokyaku() {
		super();
	}

	/**
	 * クラス名を取得する。
	 * @return クラス名
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * クラス名を設定する。
	 * @param className クラス名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 顧客コードを取得する。
	 * @return 顧客コード
	 */
	public String getKokyakuCD() {
		return kokyakuCD;
	}

	/**
	 * 顧客コードを設定する。
	 * @param kokyakuCD 顧客コード
	 */
	public void setKokyakuCD(String kokyakuCD) {
		this.kokyakuCD = kokyakuCD;
	}

	/**
	 * 顧客名を取得する。
	 * @return 顧客名
	 */
	public String getKokyakuName() {
		return kokyakuName;
	}

	/**
	 * 顧客名を設定する。
	 * @param kokyakuName 顧客名
	 */
	public void setKokyakuName(String kokyakuName) {
		this.kokyakuName = kokyakuName;
	}

	/**
	 * 顧客名カナを取得する。
	 * @return 顧客名カナ
	 */
	public String getKokyakuKana() {
		return kokyakuKana;
	}

	/**
	 * 顧客名カナを設定する。
	 * @param kokyakuKana 顧客名カナ
	 */
	public void setKokyakuKana(String kokyakuKana) {
		this.kokyakuKana = kokyakuKana;
	}

	/**
	 * 郵便番号１を取得する。
	 * @return 郵便番号１
	 */
	public String getZip1() {
		return zip1;
	}

	/**
	 * 郵便番号１を設定する。
	 * @param zip1 郵便番号１
	 */
	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}

	/**
	 * 郵便番号２を取得する。
	 * @return 郵便番号２
	 */
	public String getZip2() {
		return zip2;
	}

	/**
	 * 郵便番号２を設定する。
	 * @param zip2 郵便番号２
	 */
	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}

	/**
	 * 県コードを取得する。
	 * @return 県コード
	 */
	public String getKenCD() {
		return kenCD;
	}

	/**
	 * 県コードを設定する。
	 * @param kenCD 県コード
	 */
	public void setKenCD(String kenCD) {
		this.kenCD = kenCD;
	}

	/**
	 * 県名を取得する。
	 * @return 県名
	 */
	public String getKenName() {
		return kenName;
	}

	/**
	 * 県名を設定する。
	 * @param kenName 県名
	 */
	public void setKenName(String kenName) {
		this.kenName = kenName;
	}

	/**
	 * 住所１を取得する。
	 * @return 住所１
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * 住所１を設定する。
	 * @param address1 住所１
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * 住所２を取得する。
	 * @return 住所２
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * 住所２を設定する。
	 * @param address2 住所２
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * 電話番号１を取得する。
	 * @return 電話番号１
	 */
	public String getTel1() {
		return tel1;
	}

	/**
	 * 電話番号１を設定する。
	 * @param tel1 電話番号１
	 */
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	/**
	 * 電話番号２を取得する。
	 * @return 電話番号２
	 */
	public String getTel2() {
		return tel2;
	}

	/**
	 * 電話番号２を設定する。
	 * @param tel2 電話番号２
	 */
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	/**
	 * 電話番号３を取得する。
	 * @return 電話番号３
	 */
	public String getTel3() {
		return tel3;
	}

	/**
	 * 電話番号３を設定する。
	 * @param tel3 電話番号３
	 */
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	/**
	 * FAX番号１を取得する。
	 * @return FAX番号１
	 */
	public String getFax1() {
		return fax1;
	}

	/**
	 * FAX番号１を設定する。
	 * @param fax1 FAX番号１
	 */
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	/**
	 * FAX番号２を取得する。
	 * @return FAX番号２
	 */
	public String getFax2() {
		return fax2;
	}

	/**
	 * FAX番号２を設定する。
	 * @param fax2 FAX番号２
	 */
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	/**
	 * FAX番号３を取得する。
	 * @return FAX番号３
	 */
	public String getFax3() {
		return fax3;
	}

	/**
	 * FAX番号３を設定する。
	 * @param fax3 FAX番号３
	 */
	public void setFax3(String fax3) {
		this.fax3 = fax3;
	}

	/**
	 * メールアドレスを取得する。
	 * @return メールアドレス
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * メールアドレスを設定する。
	 * @param mail メールアドレス
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 売掛け残高を取得する。
	 * @return 売掛け残高
	 */
	public int getUrikake_zan() {
		return urikake_zan;
	}

	/**
	 * 売掛け残高を設定する。
	 * @param urikake_zan 売掛け残高
	 */
	public void setUrikake_zan(int urikake_zan) {
		this.urikake_zan = urikake_zan;
	}

	/**
	 * 与信限度額を取得する。
	 * @return 与信限度額
	 */
	public int getYoshin_gendo() {
		return yoshin_gendo;
	}

	/**
	 * 与信限度額を設定する。
	 * @param yoshin_gendo 与信限度額
	 */
	public void setYoshin_gendo(int yoshin_gendo) {
		this.yoshin_gendo = yoshin_gendo;
	}

	/**
	 * 営業所コードを取得する。
	 * @return 営業所コード
	 */
	public String getEigyoshoCD() {
		return eigyoshoCD;
	}

	/**
	 * 営業所コードを設定する。
	 * @param eigyoshoCD 営業所コード
	 */
	public void setEigyoshoCD(String eigyoshoCD) {
		this.eigyoshoCD = eigyoshoCD;
	}

	/**
	 * ユーザー登録日を取得する。
	 * @return ユーザー登録日
	 */
	public String getTourokuD() {
		return tourokuD;
	}

	/**
	 * ユーザー登録日を設定する。
	 * @param tourokuD ユーザー登録日
	 */
	public void setTourokuD(String tourokuD) {
		this.tourokuD = tourokuD;
	}

	/**
	 * パスワードを取得する。
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定する。
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 顧客エラーフラグを取得する。
	 * @return 顧客エラーフラグ
	 */
	public int getKokyakuErrFLG() {
		return kokyakuErrFLG;
	}

	/**
	 * 顧客エラーフラグを設定する。
	 * @param kokyakuErrFLG 顧客エラーフラグ
	 */
	public void setKokyakuErrFLG(int kokyakuErrFLG) {
		this.kokyakuErrFLG = kokyakuErrFLG;
	}

	/**
	 * フィールドをブランクで埋める（画面表示用）。
	 */
	public void setBrank() {
		this.setKokyakuCD(null);
		this.setKokyakuName("");
		this.setKokyakuKana("");
		this.setZip1("");
		this.setZip2("");
		this.setAddress1("");
		this.setAddress2("");
		this.setTel1("");
		this.setTel2("");
		this.setTel3("");
		this.setFax1("");
		this.setFax2("");
		this.setFax3("");
		this.setMail("");
		this.setTourokuD("");
		this.setEigyoshoCD("");
		this.setPassword("");
	}
}

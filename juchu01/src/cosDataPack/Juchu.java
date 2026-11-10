package cosDataPack;

import java.util.ArrayList;

/**
 * 受注クラス。
 * 受注情報を保持するクラス。受注のヘッダ部と受注明細をメンバーにもつ。
 * @version 1.0
 */
public class Juchu {
	/** 受注コード */
	private String juchuCD;
	/** 顧客情報 */
	private Kokyaku kokyaku;
	/** 受注日 */
	private String juchuD;
	/** 受注総金額 */
	private int totalKingaku;
	/** 消費税率 */
	private double taxRate;
	/** 支払方法 */
	private int shiharaiType;
	/** 支払方法文字列 */
	private String shiharaiTypeS;
	/** 納品予定日（お届け日） */
	private String nouhinYoteiD;
	/** 納品予定時間帯（お届け時間帯） */
	private String nouhinYoteiRange;
	/** 受注明細行数 */
	private int lines;
	/** 受注明細 */
	private ArrayList<Meisai> meisai;
	/** 受注エラーフラグ 0:OK, 1:引当NG, 2:与信オーバー , 9:SQLエラー */
	private int juchuErrFLG;
	/** 消費税率 */
	private final double TAX_RATE = 0.05;
	/** 支払方法 0：月末一括払い */
	private final String PAY_TYPE_0 = "月末一括お支払い";

	/**
	 * 引数なしコンストラクタ。
	 * オブジェクトの初期化処理と、消費税率をセットする。
	 * <pre>
	 * 1．消費税率（TAX_RATE）をフィールドのtaxRateにセットする。
	 * </pre>
	 */
	public Juchu() {
		super();
		//消費税率をセットする
		setTaxRate(TAX_RATE);
	}

	/**
	 * 受注コードを取得する。
	 * @return 受注コード
	 */
	public String getJuchuCD() {
		return juchuCD;
	}

	/**
	 * 受注コードを設定する。
	 * @param juchuCD 受注コード
	 */
	public void setJuchuCD(String juchuCD) {
		this.juchuCD = juchuCD;
	}

	/**
	 * 顧客情報を取得する。
	 * @return 顧客情報
	 */
	public Kokyaku getKokyaku() {
		return kokyaku;
	}

	/**
	 * 顧客情報を設定する。
	 * @param kokyaku 顧客情報
	 */
	public void setKokyaku(Kokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * 受注日を取得する。
	 * @return 受注日
	 */
	public String getJuchuD() {
		return juchuD;
	}

	/**
	 * 受注日を設定する。
	 * @param juchuD 受注日
	 */
	public void setJuchuD(String juchuD) {
		this.juchuD = juchuD;
	}

	/**
	 * 受注総金額を取得する。
	 * @return 受注総金額
	 */
	public int getTotalKingaku() {
		return totalKingaku;
	}

	/**
	 * 受注総金額を設定する。
	 * @param totalKingaku 受注総金額
	 */
	public void setTotalKingaku(int totalKingaku) {
		this.totalKingaku = totalKingaku;
	}

	/**
	 * 消費税率を取得する。
	 * @return 消費税率
	 */
	public double getTaxRate() {
		return taxRate;
	}

	/**
	 * 消費税率を設定する。
	 * @param taxRate 消費税率
	 */
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * 支払方法を取得する。
	 * @return 支払方法
	 */
	public int getShiharaiType() {
		return shiharaiType;
	}

	/**
	 * 支払方法を設定する。
	 * @param shiharaiType 支払方法
	 */
	public void setShiharaiType(int shiharaiType) {
		this.shiharaiType = shiharaiType;
	}

	/**
	 * 支払方法文字列を取得する。
	 * @return 支払方法文字列
	 */
	public String getShiharaiTypeS() {
		return shiharaiTypeS;
	}

	/**
	 * 支払方法文字列を設定する。
	 * @param shiharaiTypeS 支払方法文字列
	 */
	public void setShiharaiTypeS(String shiharaiTypeS) {
		this.shiharaiTypeS = shiharaiTypeS;
	}

	/**
	 * 支払方法文字列を設定する。
	 * 法人の場合、”月末一括支払い”のみ
	 */
	public void setShiharaiTypeS() {
		this.shiharaiTypeS = PAY_TYPE_0;
	}

	/**
	 * 納品予定日を取得する。
	 * @return 納品予定日
	 */
	public String getNouhinYoteiD() {
		return nouhinYoteiD;
	}

	/**
	 * 納品予定日を設定する。
	 * @param nouhinYoteiD 納品予定日
	 */
	public void setNouhinYoteiD(String nouhinYoteiD) {
		this.nouhinYoteiD = nouhinYoteiD;
	}

	/**
	 * 納品予定時間帯を取得する。
	 * @return 納品予定時間帯
	 */
	public String getNouhinYoteiRange() {
		return nouhinYoteiRange;
	}

	/**
	 * 納品予定時間帯を設定する。
	 * @param nouhinYoteiRange 納品予定時間帯
	 */
	public void setNouhinYoteiRange(String nouhinYoteiRange) {
		this.nouhinYoteiRange = nouhinYoteiRange;
	}

	/**
	 * 受注エラーフラグを取得する。
	 * @return 受注エラーフラグ
	 */
	public int getJuchuErrFLG() {
		return juchuErrFLG;
	}

	/**
	 * 受注エラーフラグを設定する。
	 * @param juchuErrFLG 受注エラーフラグ
	 */
	public void setJuchuErrFLG(int juchuErrFLG) {
		this.juchuErrFLG = juchuErrFLG;
	}

	/**
	 * 受注明細行数を取得する。
	 * @return 受注明細行数
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * 受注明細行数を設定する。
	 * @param lines 受注明細行数
	 */
	public void setLines(int lines) {
		this.lines = lines;
	}

	/**
	 * 受注明細を取得する。
	 * @return 受注明細
	 */
	public ArrayList<Meisai> getMeisai() {
		return meisai;
	}

	/**
	 * 受注明細を設定する。
	 * @param meisai 受注明細
	 */
	public void setMeisai(ArrayList<Meisai> meisai) {
		this.meisai = meisai;
	}

	/**
	 * 受注明細を追加する。
	 * <pre>
	 * 1．明細オブジェクトを生成する。（1件分の明細を作成）
	 * 2．引数で渡された、商品基本情報と数量を、作成した明細にセットする。
	 * 3．明細のsetKingakuを実行して、明細の金額をセットする。
	 * 4．このオブジェクトのupdateMeisaiメソッドの引数に「1．」で作成した明細を渡して、明細を追加する。
	 * </pre>
	 * @param suryou 数量
	 * @param spd 商品基本情報
	 */
	public void addMeisai(int suryou, ShohinPrimalData spd) {
		//１明細の作成
		Meisai line = new Meisai();
		//商品基本情報を明細にセットする
		line.setShohinPriData(spd);
		//購入数をセットする
		line.setSuryou(suryou);
		//数量と販売単価で金額を計算してセットする
		line.setKingaku();
		//受注データ（this）に明細を追加する
		this.updateMeisai(line);
	}

	/**
	 * 受注明細を更新する。
	 * <pre>
	 * 1．受注データの明細が１件もなければ、
	 *    1-1．Meisai型のArrayListオブジェクトを作成して、setMeisaiで受注データの明細にセットする。
	 *    1-2．setLinesメソッドで、明細行数に0をセットする。
	 * 2．引数で渡された商品コードとこのオブジェクトの明細の商品コードが同じなら、明細を作成せずに、数量と金額を更新する。
	 *    以下の処理を受注データの明細の行数分くり返す。
	 *    2-1．このオブジェクトの明細から、1行分の明細を取得する。
	 *    2-2．取得した明細の商品コードと、引数で渡された明細の商品コードが等しい場合、
	 *       2-2-1．取得した明細の数量に引数で渡された明細の数量を加算する。
	 *       2-2-2．取得した明細の金額をセットする。
	 *       2-2-3．このオブジェクトの明細に、再セットする。
	 * 3．このオブジェクトの明細に引数で渡された明細の商品コードがない場合、（「2-2．」に合致しなかった場合）
	 *    3-1．このオブジェクトの明細のaddメソッドに引数で渡された明細を渡して、新たに明細を追加する。
	 *       this.meisai.add(data);
	 * 4．数量が0の明細行を削除するため、cleanMeisaiメソッドを実行して明細のクリーンナップをする。
	 * </pre>
	 * @param data 更新（追加）する明細(1行)
	 */
	public void updateMeisai(Meisai data) {
		int i; //明細の行数をカウントする変数
		//明細が１件もなければArrayList<Meisai>を作成する
		if (this.getMeisai() == null) {
			//明細を作成して受注データ（this）にセットする
			this.setMeisai(new ArrayList<Meisai>());
			//明細行数を0にセットする
			i = 0;
			this.setLines(i);
		}
		//現在の明細と、引数で渡されたデータ（明細）の商品コードが同じなら、
		//数量を加算して金額を計算する
		for (i = 0; i < this.getLines(); i++) {
			//現在の明細を1行取り出す
			Meisai curMeisai = this.getMeisai().get(i);
			//取り出した明細の商品コードと引数で渡れた明細の商品コードを比較
			if (curMeisai.getShohinPriData().getGoodsCD().equals(
					data.getShohinPriData().getGoodsCD())) {
				//数量と金額を再設定
				curMeisai.setSuryou(curMeisai.getSuryou() + data.getSuryou());
				curMeisai.setKingaku();
				//現在の明細にセットする
				this.getMeisai().set(i, curMeisai);
				break;
			}
		}
		//既存の明細に同じ商品コードがなければ、引数で渡されたデータで明細行を追加する
		if (i >= this.getLines()) {
			//引数で渡された明細を追加
			this.meisai.add(data);
		}
		//明細部分をクリーンナップする（数量0の明細行を削除）
		this.cleanMeisai();
	}

	/**
	 * 受注明細の金額を計算する。
	 * <pre>
	 * 1．このオブジェクトの引数で渡された明細行番号の明細に、引数で渡された数量をセットする。
	 * 2．このオブジェクトの引数で渡された明細行番号の金額をセットする。
	 * </pre>
	 * @param suryou 数量
	 * @param meisaiIndex 明細行番号
	 */
	public void updateMeisaiKingaku(int suryou, int meisaiIndex) {
		//新しい数量をセットする
		this.getMeisai().get(meisaiIndex).setSuryou(suryou);
		//金額を計算し直す
		this.getMeisai().get(meisaiIndex).setKingaku();
	}

	/**
	 * 受注明細の納品予定日、お届け時間帯を設定する。
	 * <pre>
	 * 1．明細の各行に、受注データと同じ納品予定日とお届け時間帯をセットする。
	 *    以下の処理を受注データの明細数分くり返す。
	 *    1-1．このオブジェクトの明細に、引数で渡された納品予定日をセットする。
	 *    1-2．このオブジェクトの明細に、引数で渡されたお届け先時間帯をセットする。
	 * </pre>
	 * @param nouhinBi 納品予定日
	 * @param nouhinRange お届け時間帯
	 */
	public void setMeisaiNouhinbi(String nouhinBi, String nouhinRange) {
		//明細行の納品予定日と納品予定時間帯をセットする
		for (int i = 0; i < this.getLines(); i++) {
			this.meisai.get(i).setNouhinYoteiD(nouhinBi);
			this.meisai.get(i).setNouhinYoteiRange(nouhinRange);
		}
	}

	/**
	 * 受注明細の金額の合計を求めて、受注総金額に設定する。
	 * <pre>
	 * 1．このオブジェクトに明細が1件でもあれば、
	 *    1-1．cleanMeisaiメソッドを実行して、数量が０の明細行を削除する。
	 *    1-2．setLineNoメソッドを実行して明細行番号を振り直す。
	 *    1-3．明細の各行の金額の合計を求める。
	 *       以下の処理を受注データの明細行数分くり返す。
	 *       1-3-1．このオブジェクトの明細の金額を取得して合計金額（変数）に加算する。
	 *    1-4．「1-3．」で求めた明細の合計金額をsetTotalKingakuで、このオブジェクトの受注総額にセットする。
	 * </pre>
	 */
	public void setTotalKingaku() {
		int total = 0; //受注総額
		if (this.getMeisai() != null) {
			//明細部分をクリーンナップする（数量0の明細行を削除）
			cleanMeisai();
			//明細番号の振り直し
			setLineNo();
			//明細行の金額の合計を求める
			for (int i = 0; i < this.getLines(); i++) {
				total += this.meisai.get(i).getKingaku();
			}
			//受注の受注総額にセットする
			this.setTotalKingaku(total);
		}
	}

	/**
	 * 受注明細のクリーンナップ。
	 * （数量が0の明細を削除する）
	 * <pre>
	 * 1．明細の各行の数量が0の明細を削除する
	 *    以下の処理を受注データの明細のカウントをとりながら、明細行数分くり返す。
	 *    1-1．明細の数量が0の場合、
	 *       1-1-1．当該明細を削除する
	 *       1-1-2．明細のカウントを減算する。
	 * 2．このオブジェクトの明細行数にカウントした明細行数をセットする。
	 * 3．setLineNoメソッドを実行して、明細番号の振り直しをする。
	 * </pre>
	 */
	private void cleanMeisai() {
		//数量０の明細を削除して明細行数をセットする
		for (int i = 0; i < this.meisai.size(); i++) {
			if (this.getMeisai().get(i).getSuryou() == 0) {
				this.getMeisai().remove(i);
				i--;
			}
		}
		//受注データの明細行数を更新する
		this.setLines(this.meisai.size());
		//明細番号の振り直し
		this.setLineNo();
	}

	/**
	 * 受注明細の行番号を割り当てる。
	 * （明細を削除したり、追加したりした場合に呼び出され、明細番号を振り直す際に利用）
	 * <pre>
	 * 1．このオブジェクトの全明細の行番号に、1～の行番号をセットする。
	 * </pre>
	 */
	private void setLineNo() {
		for (int i = 0; i < this.getLines(); i++) {
			this.getMeisai().get(i).setLineNum(Integer.toString(i + 1));
		}
	}
}

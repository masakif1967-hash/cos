<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cosDataPack.Shohin"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cosDataPack.Meisai"%>
<%@ page import="cos.JuchuControl"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注データ、顧客データ、受注明細の取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
	ArrayList<Meisai> meisai = juchu.getMeisai();
	//金額類をカンマ編集する
	String sJuchuKingaku = null; //カンマ編集後受注金額の文字列
	String sTax = null; //カンマ編集後消費税の文字列
	String sTotal = null; //カンマ編集後合計金額の文字列
	DecimalFormat nf = new DecimalFormat("###,###,###"); //カンマ編集用クラス
	try {
		//受注金額の編集
		sJuchuKingaku = nf.format(juchu.getTotalKingaku());
		//消費税の編集
		sTax = nf.format((int) (juchu.getTotalKingaku() * juchu.getTaxRate()));
		//合計金額の編集
		sTotal = nf.format(juchu.getTotalKingaku() +
				(int) (juchu.getTotalKingaku() * juchu.getTaxRate()));
	} catch (IllegalArgumentException iae) {
		System.out.println("IllegalArgumentException");
	}
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd1050">
	<!-- コンテナ部分（ページ全体） -->
	<div id="container">
		<!-- ヘッダー部分 -->
		<div id="header">
			<h1>Office Goody</h1>
			<h2>Customer Order System</h2>
		</div>
		<!-- コンテンツ部分 -->
		<div id="contents">
			<form action="CosServlet" method="post">
				<!-- コンテンツ内ヘッダー部分 -->
				<div id="contents_header">
					<p><small>COSD1050</small></p>
					<h3>注文内容のご確認</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
					<input type="submit" name="order" value="カート内ご注文リストへ戻る"> <input
						type="submit" name="order" value="お支払い・お届け先入力">
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<div class="cart_list">
						<P>◎ご注文内容がご確認できましたら、「支払方法・お届け先入力」ボタンを押してください。
						<p>◎ご注文内容を訂正される時は、「カート内ご注文リストへ戻る」ボタンを押してください。
						<table>
							<colgroup>
								<col class="col_1">
								<col class="col_2">
								<col class="col_3">
								<col class="col_4">
								<col class="col_5">
							</colgroup>
							<thead>
								<tr>
									<th>商品コード</th>
									<th>商品名</th>
									<th>単価</th>
									<th>ご購入数</th>
									<th>金額</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<td colspan="3"></td>
									<td>計</td>
									<td align="right"><%=sJuchuKingaku%>円</td>
								</tr>
								<tr>
									<td colspan="3"></td>
									<td>消費税</td>
									<td align="right"><%=sTax%>円</td>
								</tr>
								<tr>
									<td colspan="3"></td>
									<td>合計金額</td>
									<td align="right"><%=sTotal%>円</td>
								</tr>
							</tfoot>
							<tbody>
								<%
									//受注データの各明細行を表示する
									Meisai line = null;
									String sTanka = null; //カンマ編集後単価の文字列
									String sKingaku = null; //カンマ編集後金額文字列
									for (int i = 0; i < meisai.size(); i++) {
										line = (Meisai) meisai.get(i);
										//単価と金額をカンマ編集する
										try {
											sKingaku = nf.format(line.getKingaku());
											sTanka = nf.format(line.getShohinPriData().getHtanka());
										} catch (IllegalArgumentException iae) {
											System.out.println("IllegalArgumentException");
										}
									%>
									<tr>
										<td><%=line.getShohinPriData().getGoodsCD()%></td>
										<td><%=line.getShohinPriData().getGoodsName()%></td>
										<td align="right"><%=sTanka%>円</td>
										<td><%=line.getSuryou()%></td>
										<td align="right"><%=sKingaku%>円</td>
									</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>
			</form>
		</div>
		<!-- フッター部分 -->
		<div id="footer">
			<address>&copy;Office Goody 2011 All rights reserved Office
				Goody Co,Ltd.</address>
		</div>
	</div>
</body>
</html>
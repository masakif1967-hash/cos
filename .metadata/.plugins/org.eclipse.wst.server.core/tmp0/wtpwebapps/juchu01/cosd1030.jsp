<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"  %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="cosDataPack.Meisai" %>
<%@ page import="cosDataPack.Juchu" %>
<%@ page import="cosDataPack.Kokyaku" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.lang.IllegalArgumentException" %>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注データ、顧客データ、受注明細の取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
	ArrayList<Meisai> meisai = juchu.getMeisai();
	int meisaiSize = 0;
	if (meisai != null) {
		meisaiSize = meisai.size();
	}
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
<body id="cosd1030">
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
					<p><small>COSD1030</small></p>
					<h3>カート内容ご注文リスト</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
					<input type="submit" name="order" value="ご購入を続ける">
					<%
						//明細がなければ（何もカートに入っていない）、
						//”ご注文手続きへ”と”再計算”はボタンは押下不可にする
						if (meisaiSize != 0) {
					%>
					<input type="submit" name="order" value="ご注文手続きへ"> <input
						type="submit" name="order" value="再計算">
					<%
						} else {
					%>
					<input type="submit" name="order" value="ご注文手続きへ"
						disabled="disabled"> <input type="submit" name="order"
						value="再計算" disabled="disabled">
					<%
						}
					%>
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<div class="cart_list">
						<P>◎ご購入を続ける時は、「ご購入を続ける」ボタンを押してください。
						<P>◎ご購入数を変更されるには、「ご購入数」の数を変更し、「再計算」ボタンを押してください。
						<P>◎購入商品を取り消す場合は、「ご購入数」に０を入力してください。
						<P>◎よろしければ「ご注文手続きへ」ボタンを押して、ご注文手続き処理へ進んでください。
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
									if (meisaiSize == 0) {
								%>
								<tr>
									<td colspan="5">カートの中には何もありません。</td>
								</tr>
								<%
									} else {
										//受注データの各明細行を表示する
										Meisai line = null;
										String sTanka = null; //カンマ編集後単価の文字列
										String sKingaku = null; //カンマ編集後金額の文字列
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
												<td><input type="text" name="suryou<%=i%>" maxlength="4"
													value=<%=line.getSuryou()%>></td>
												<td align="right"><%=sKingaku%>円</td>
											</tr>
								<%
										}
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
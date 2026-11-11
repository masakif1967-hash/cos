<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cosDataPack.Houjin"%>
<%@ page import="cos.JuchuControl"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注データ、顧客データの取得
	Juchu juchu = juchucontrol.getData();
	Houjin kokyaku = (Houjin) juchu.getKokyaku();
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
<body id="cosd1070">
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
					<p><small>COSD1070</small></p>
					<h3>ご注文内容のご確認</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%= kokyaku.getKokyakuName() %>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
					<input type="submit" name="order" value="お支払い方法・お届け先の入力へ戻る">
					<input type="submit" name="order" value="ご注文を確定する">
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<table>
						<caption>次の内容でよろしければ、「ご注文を確定する」ボタンを押してください。</caption>
						<tr>
							<th colspan="2">会社名</th>
							<td><%=kokyaku.getKokyakuName() %>様</td>
						<tr>
							<th colspan="2">ご担当者名</th>
							<td><%=kokyaku.getTantouName() %>様</td>
						</tr>
						<tr>
							<th colspan="2">メールアドレス</th>
							<td><%=kokyaku.getMail() %></td>
						</tr>
						<tr>
							<th>&nbsp;</th>
						</tr>
						<tr>
							<th colspan="2">今回のご購入商品数</th>
							<td><%=juchu.getLines() %>商品</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
						</tr>
						<tr>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<th>ご購入金額</th>
							<td><%=sJuchuKingaku%>円</td>
						</tr>
						<tr>
							<th>&nbsp;&nbsp;&nbsp;</th>
							<th>消費税</th>
							<td><%=sTax%>円</td>
						</tr>
						<tr>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<th>合計金額</th>
							<td style="border-bottom: 2px solid;"><%=sTotal%>円</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
						</tr>
						<tr>
							<th colspan="2">お支払い方法</th>
							<td><%=juchu.getShiharaiTypeS() %></td>
						</tr>
						<tr>
							<th colspan="2">お届け先住所</th>
							<td>〒<%=kokyaku.getZip1()%>-<%=kokyaku.getZip2()%>&nbsp; <%=kokyaku.getAddress1()%>&nbsp;<%=kokyaku.getAddress2()%><br>
								TEL:<%=kokyaku.getTel1()%>-<%=kokyaku.getTel2()%>-<%=kokyaku.getTel3()%></td>
						</tr>
						<tr>
							<th colspan="2">お届け日</th>
							<td><%=juchu.getNouhinYoteiD()%></td>
						</tr>
						<tr>
							<th colspan="2">お届け時間帯</th>
							<td><%=juchu.getNouhinYoteiRange()%></td>
						</tr>
					</table>
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
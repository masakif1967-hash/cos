<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cos.JuchuControl"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注ﾃﾞｰﾀ、顧客データの取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd151e">
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
					<p><small>COSD151E</small></p>
					<h3>在庫切れ商品があります</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
					<input type="submit" name="order" value="戻る">
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<table>
						<tr>
							<td colspan="4">申し訳ございません。<br> 下記商品につきましては、在庫が不足しております。<br>
								ご変更をお願いします。
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
						</tr>
						<%
							String shohinCD = null;
							String shohinName = null;
							for (int i = 0; i < juchu.getLines(); i++) {
								if (juchu.getMeisai().get(i).getMeisaiErrFLG() == 1) {
									shohinCD = juchu.getMeisai().get(i).getShohinPriData().getGoodsCD();
									shohinName = juchu.getMeisai().get(i).getShohinPriData().getGoodsName();
						%>
							<tr style="line-height: 2em;">
								<th>商品コード：</th>
								<td><%=shohinCD%></td>
								<th>商品名：</th>
								<td><%=shohinName%></td>
							</tr>
						<%
								}
							}
						%>
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
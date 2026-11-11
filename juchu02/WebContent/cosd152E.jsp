<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注ﾃﾞｰﾀ、顧客ﾃﾞｰﾀの取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd152e">
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
					<p><small>COSD152E</small></p>
					<h3>ご登録できませんでした</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<table>
						<tr>
							<td colspan="2">申し訳ございません。<br> 今回のご注文はお取り受けできませんでした。<br>
								詳細は、カスタマーサービスまでお問い合わせください。
							</td>
						</tr>
						<tr>
							<th>お問い合わせ番号：</th>
							<td><%=juchu.getJuchuCD()%></td>
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
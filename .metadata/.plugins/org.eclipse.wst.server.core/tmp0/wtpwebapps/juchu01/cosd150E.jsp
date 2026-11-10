<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd150e">
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
					<p><small>COSD150E</small></p>
					<h3>ログオンできませんでした</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<%
						String msg1 = "ユーザーIDとパスワードをお確かめください。";
						String msg2 = "ユーザーIDやパスワードをお忘れの場合は、下記までメールでお問い合わせください";
						String msg3 = "お問い合わせ：○○○○@▲▲▲.△△△";
					%>
					<div align="center">
						<h3><%=msg1%></h3>
						<br> <br>
						<p><%=msg2%></p>
						<p><%=msg3%></p>
						<br> <br>
						<p><input type="submit" name="top" value="トップページへ">
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
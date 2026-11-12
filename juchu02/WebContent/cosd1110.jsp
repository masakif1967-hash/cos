<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cos.KokyakuControl"%>
<!-- ************************************************ ここから ver.3 ************************************************ -->
<%@ page import="cosDataPack.Kokyaku"%>
<!-- ************************************************ ここまで ver.3 ************************************************ -->
<%@ page import="java.lang.String"%>

<!DOCTYPE html>
<jsp:useBean id="kokyakucontrol" scope="session"
	class="cos.KokyakuControl" />
<jsp:useBean id="msg" scope="session" class="java.lang.String" />
<html lang="ja">
<%
//************************************************ ここから ver.3 ************************************************
	//顧客データを取り出す
	Kokyaku kokyaku = kokyakucontrol.getData();
//************************************************ ここまで ver.3 ************************************************
	//メッセージのセット
	String message="ユーザーが登録されました。トップページからログオンしてください。";
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="temp">
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
					<p><small>COSD1110</small></p>
					<h3>ユーザー登録完了</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<br> <br>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body" align="center">
					<font size="5">ユーザーＩＤ： <%=kokyaku.getKokyakuCD() %></font><br>
					<%=message %><br> <br> <br> <br>
					<div align="center">
						<input type="submit" name="top" value="トップページへ">
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
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Kokyaku"%>

<!DOCTYPE html>
<jsp:useBean id="kokyakucontrol" scope="session"
	class="cos.KokyakuControl" />
<html lang="ja">
<%
	Kokyaku kokyaku = kokyakucontrol.getData();
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd153e">
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
					<p><small>COSD153E</small></p>
					<h3>ご登録できませんでした</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<table>
						<tr>
							<td colspan="2">
								<%
									String msg1 = null;
									String msg2 = null;
									String msg3 = "お問い合わせ：○○○○@▲▲▲.△△△";
									//既に同じ名前とメールアドレスでユーザーが登録されている場合
									System.out.println("JSP:kokyakuErr:" + kokyaku.getKokyakuErrFLG() + ":" + kokyaku.getKokyakuName());
									if (kokyaku.getKokyakuErrFLG() == 1) {
										msg1 = "既に登録されています。";
										msg2 = "ユーザーIDやパスワードをお忘れの場合は、下記までメールでお問い合わせください";
										//その他のエラーの場合
									} else {
										msg1 = "システムの障害です。";
										msg2 = "もうしばらくお待ちください。ご迷惑をおかけしております。";
									}
								%>
								<div align="center">
									<h3><%=msg1%></h3>
									<h3><%=msg2%></h3>
									<P>
									<h3><%=msg3%></h3>
									<input type="submit" name="top" value="トップページへ">
								</div>
							</td>
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
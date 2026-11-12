<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Houjin"%>
<%@ page import="cos.KokyakuControl"%>

<!DOCTYPE html>
<jsp:useBean id="kokyakucontrol" scope="session"
	class="cos.KokyakuControl" />
<html lang="ja">
<%
	//顧客データを取り出す
	Houjin kokyaku=(Houjin)kokyakucontrol.getData();
 %>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd1100">
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
					<p><small>COSD1100</small></p>
					<h3>ユーザー情報の確認</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<P>以下の内容を確認し、訂正される場合は「修正」ボタンを、このまま登録される場合は「ユーザー登録」ボタンを押してください。</P>
					<table>
						<tr>
							<th align="left">ユーザー登録情報</th>
							<td></td>
						</tr>
						<tr>
							<th align="left">会社名</th>
							<td><input type="text" readonly="readonly"
								name="kokyakuname" maxlength="50" size="100"
								value=<%=kokyaku.getKokyakuName() %>></td>
						</tr>
						<tr>
							<th align="left">会社カナ名</th>
							<td><input type="text" readonly="readonly"
								name="kokyakukana" maxlength="50" size="100"
								value=<%=kokyaku.getKokyakuKana() %>></td>
						</tr>
						<tr>
							<th align="left">郵便番号</th>
							<td><input type="text" readonly="readonly" name="zip1"
								maxlength="3" size="10" value=<%=kokyaku.getZip1() %>>－
								<input type="text" readonly="readonly" name="zip2" maxlength="4"
								size="10" value=<%=kokyaku.getZip2() %>></td>
						</tr>
						<tr>
							<th align="left">住所１</th>
							<td><input type="text" readonly="readonly" name="kenName"
								size="15" value=<%=kokyaku.getKenName() %>> <input
								type="text" readonly="readonly" name="address1" maxlength="50"
								size="80" value=<%=kokyaku.getAddress1() %>></td>
						</tr>
						<tr>
							<th align="left">住所２</th>
							<td><input type="text" readonly="readonly" name="address2"
								maxlength="50" size="100" value=<%=kokyaku.getAddress2() %>></td>
						</tr>
						<tr>
							<th align="left">電話番号</th>
							<td><input type="text" readonly="readonly" name="tel1"
								maxlength="5" size="10" value=<%=kokyaku.getTel1()%>>－ <input
								type="text" readonly="readonly" name="tel2" maxlength="4"
								size="10" value=<%=kokyaku.getTel2()%>>－ <input
								type="text" readonly="readonly" name="tel3" maxlength="4"
								size="10" value=<%=kokyaku.getTel3()%>></td>
						</tr>
						<tr>
							<th align="left">ＦＡＸ番号</th>
							<td><input type="text" readonly="readonly" name="fax1"
								maxlength="5" size="10" value=<%=kokyaku.getFax1()%>>－ <input
								type="text" readonly="readonly" name="fax2" maxlength="4"
								size="10" value=<%=kokyaku.getFax2()%>>－ <input
								type="text" readonly="readonly" name="fax3" maxlength="4"
								size="10" value=<%=kokyaku.getFax3()%>></td>
						</tr>
						<tr>
							<th align="left">業種</th>
							<td><input type="text" readonly="readonly" name="gyoushu"
								maxlength="20" size="50" value=<%=kokyaku.getGyoushu() %>></td>
						</tr>
						<tr>
							<th align="left">担当者名</th>
							<td><input type="text" readonly="readonly" name="tantouname"
								maxlength="50" size="100" value=<%=kokyaku.getTantouName() %>></td>
						</tr>
						<tr>
							<th align="left">担当者部署名</th>
							<td><input type="text" readonly="readonly"
								name="tantoubusho" maxlength="50" size="100"
								value=<%=kokyaku.getTantoubusho() %>>
						</tr>
						<tr>
							<th align="left">メールアドレス</th>
							<td><input type="text" readonly="readonly" name="mail1"
								maxlength="256" size="100" value=<%=kokyaku.getMail()%>></td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" name="user" value="修正"> <input
							type="submit" name="user" value="ユーザー登録">
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
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Houjin"%>
<%@ page import="cos.KokyakuControl"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>

<!DOCTYPE html>
<jsp:useBean id="kokyakucontrol" scope="session"
	class="cos.KokyakuControl" />
<html lang="ja">
<%
	//顧客データを取り出す
	Houjin kokyaku = (Houjin) kokyakucontrol.getData();
	//県コードと県名のﾏｯﾋﾟﾝｸﾞテーブルを取得
	Map<String, String> ken = kokyakucontrol.getKenMap();
%>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-script-Type"
	content="text/javascript; charset=utf-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
<script type="text/javascript" src="js/common1.js" charset="utf-8"></script>
</head>
<body id="cosd1090">
	<!-- コンテナ部分（ページ全体） -->
	<div id="container">
		<!-- ヘッダー部分 -->
		<div id="header">
			<h1>Office Goody</h1>
			<h2>Customer Order System</h2>
		</div>
		<!-- コンテンツ部分 -->
		<div id="contents">
			<form action="CosServlet" method="post" name="form1"
				onSubmit="return formCheck()">
				<!-- コンテンツ内ヘッダー部分 -->
				<div id="contents_header">
					<p><small>COSD1090</small></p>
					<h3>ユーザー情報の入力</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<p>以下の内容を入力し、「次へ」ボタンを押して登録をしてください。</p>
					<div id="errorMessage"></div>
					<table>
						<colgroup>
							<col class="col_1">
							<col class="col_2">
						</colgroup>
						<tr>
							<th align="left">（*印は必須入力項目です）</th>
							<td></td>
						</tr>
						<tr>
							<th align="left">会社名・団体名*</th>
							<td><input type="text" name="kokyakuname" maxlength="50"
								size="100" value=<%=kokyaku.getKokyakuName()%>></td>
						</tr>
						<tr>
							<th align="left">会社カナ名*</th>
							<td><input type="text" name="kokyakukana" maxlength="50"
								size="100" value=<%=kokyaku.getKokyakuKana()%>></td>
						</tr>
						<tr>
							<th align="left">郵便番号*</th>
							<td><input type="text" name="zip1" maxlength="3" size="10"
								onkeyDown="return numOnly()" value=<%=kokyaku.getZip1()%>>－
								<input type="text" name="zip2" maxlength="4" size="10"
								value=<%=kokyaku.getZip2()%>></td>
						</tr>
						<tr>
							<th align="left">住所１*</th>
							<td><span class="user_kenmap"> 都道府県
								<select name="kenmap">
									<option selected></option>
									<%
										for (Map.Entry<String, String> entry : ken.entrySet()) {
											//修正ボタン押下時、顧客データに県コードがセットされている県名をセレクトしておく
											if (entry.getKey().equals(kokyaku.getKenCD())) {
									%>
												<option selected label=<%=entry.getValue()%>
													value=<%=entry.getKey()%><%=entry.getValue()%>><%=entry.getValue()%></option>
										<%
											} else {
										%>
												<option label=<%=entry.getValue()%>
													value=<%=entry.getKey()%><%=entry.getValue()%>><%=entry.getValue()%></option>
										<%
											}
										}
									%>
								</select>
							</span> <input type="text" name="address1" maxlength="50" size="70"
								value=<%=kokyaku.getAddress1()%>><br>
								群市区（島）を入れてください。</td>
						</tr>
						<tr>
							<th align="left">住所２*</th>
							<td><input type="text" name="address2" maxlength="50"
								size="100" value=<%=kokyaku.getAddress2()%>><br>
								それ以降の住所を入れてください。</td>
						</tr>
						<tr>
							<th align="left">電話番号*</th>
							<td><input type="text" name="tel1" maxlength="5" size="10"
								value=<%=kokyaku.getTel1()%>>－ <input type="text"
								name="tel2" maxlength="4" size="10" value=<%=kokyaku.getTel2()%>>－
								<input type="text" name="tel3" maxlength="4" size="10"
								value=<%=kokyaku.getTel3()%>></td>
						</tr>
						<tr>
							<th align="left">ＦＡＸ番号</th>
							<td><input type="text" name="fax1" maxlength="5" size="10"
								value=<%=kokyaku.getFax1()%>>－ <input type="text"
								name="fax2" maxlength="4" size="10" value=<%=kokyaku.getFax2()%>>－
								<input type="text" name="fax3" maxlength="4" size="10"
								value=<%=kokyaku.getFax3()%>></td>
						</tr>
						<tr>
							<th align="left">業種</th>
							<td><input type="text" name="gyoushu" maxlength="20"
								size="50" value=<%=kokyaku.getGyoushu()%>></td>
						</tr>
						<tr>
							<th align="left">担当者名*</th>
							<td><input type="text" name="tantouname" maxlength="50"
								size="100" value=<%=kokyaku.getTantouName()%>></td>
						</tr>
						<tr>
							<th align="left">担当者部署名</th>
							<td><input type="text" name="tantoubusho" maxlength="50"
								size="100" value=<%=kokyaku.getTantoubusho()%>></td>
						</tr>
						<tr>
							<th align="left">メールアドレス*</th>
							<td><input type="text" name="mail1" maxlength="256"
								size="100" value=<%=kokyaku.getMail()%>></td>
						</tr>
						<tr>
							<th align="left">メールアドレス<br>(確認のため再度入力）*
							</th>
							<td><input type="text" name="mail2" maxlength="256"
								size="100" value=<%=kokyaku.getMail()%>></td>
						</tr>
						<tr>
							<th align="left">パスワード(8文字～20文字）*</th>
							<td><input type="password" name="password1" maxlength="20"
								size="50"></td>
						</tr>
						<tr>
							<th align="left">パスワード(確認のため再度入力）*</th>
							<td><input type="password" name="password2" maxlength="20"
								size="50"><br>
								※このパスワードは、ユーザー登録に発行されるユーザーIDとともにログオン時に使用してください</td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" name="top" value="トップページへ" onclick="setValue('top')">
						<input type="submit" name="user" value="次へ" onclick="setValue('user')">
						<input type="hidden" name="onbtn"> <input type="hidden" name="classname" value=<%=kokyaku.getClassName()%>>
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
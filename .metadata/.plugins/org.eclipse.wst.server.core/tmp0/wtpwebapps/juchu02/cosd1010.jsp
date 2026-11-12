<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cosDataPack.ShohinCategory"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cos.JuchuControl"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<%
	//受注データ、顧客データ、商品カテゴリリストを取り出す
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
	ArrayList<ShohinCategory> categorylist = juchucontrol.getCategoryList();
	//受注金額をフォーマット編集する
	int kingaku = juchu.getTotalKingaku();
	String sKingaku = null; //フォーマット編集後受注金額
	DecimalFormat nf = new DecimalFormat("###,###,###"); //カンマ編集用クラス
	try {
		//金額のフォーマット編集
		sKingaku = nf.format(kingaku);
	} catch (IllegalArgumentException iae) {
		System.out.println("IllegalArgumentException");
	}
%>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd1010">
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
					<p>
						<small>COSD1010</small>
					</p>
					<h3>商品検索</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様
						</p>
						<P>
							<input type="submit" name="top" value="ログオフ">
						</p>
					</div>
					<div align="center">検索するカテゴリを選択して「検索」ボタンを押してください。</div>
					<br>
					<div class="goods_category" align="center">
						<p>
							商品カテゴリ <select name="category">
								<%
									String oldCD = null;
									for(ShohinCategory category : categorylist) {
										if(!category.getGctgCD().equals(oldCD)) {
											oldCD = category.getGctgCD();
								%>
											<optgroup label=<%=category.getGctgName()%>>
									<%
										}
									%>
										<option label=<%=category.getGsubctgName()%>
											value=<%=category.getGsubctgCD()%>>
											<%=category.getGsubctgName()%>
										</option>
								<%
									}
								%>
								</optgroup>
							</select> <input type="submit" name="order" value="検索">
						</p>
					</div>
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<div class="cart_state">
						<table>
							<caption>カートの内容</caption>
							<tr>
								<th>商品：</th>
								<td><%=juchu.getLines()%>商品</td>
							</tr>
							<tr>
								<th>金額：</th>
								<td><%=sKingaku%>円</td>
							</tr>
						</table>
						<div align="right">
							<input type="submit" name="order" value="カート内リスト">
						</div>
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
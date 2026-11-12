<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cosDataPack.Shohin"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cosDataPack.ShohinCategory"%>
<%@ page import="cos.JuchuControl"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注データ、顧客データ、商品カテゴリリスト、商品リストの取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
	ArrayList<ShohinCategory> categorylist = juchucontrol.getCategoryList();
	ArrayList<Shohin> shohinList = juchucontrol.getShohinList();
	String gsubctg = (String) session.getAttribute("subctgcd");
	//選択された商品サブカテゴリ名の取得
	String gsubctgName = null;
	for (ShohinCategory category : categorylist) {
		if (category.getGsubctgCD().equals(gsubctg)) {
			gsubctgName = category.getGsubctgName();
		}
	}
	//受注金額をカンマ編集する
	int kingaku = juchu.getTotalKingaku();
	String sKingaku = null; //カンマ編集後受注金額文字列
	DecimalFormat nf = new DecimalFormat("###,###,###"); //カンマ編集用クラス
	try {
		sKingaku = nf.format(kingaku);
	} catch (IllegalArgumentException iae) {
		System.out.println("IllegalArgumentException");
	}
%>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-script-Type"
	content="text/javascript; charset=utf-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
<script type="text/javascript" src="js/common1.js" charset="utf-8"></script>
</head>
<body id="cosd1020">
	<!-- コンテナ部分（ページ全体） -->
	<div id="container">
		<!-- ヘッダー部分 -->
		<div id="header">
			<h1>Office Goody</h1>
			<h2>Customer Order System</h2>
		</div>
		<!-- コンテンツ部分 -->
		<div id="contents">
			<!-- コンテンツ内ヘッダー部分 -->
			<div id="contents_header">
				<p><small>COSD1020</small></p>
				<h3>カテゴリ別商品リスト</h3>
			</div>
			<!-- コンテンツ内ナビゲーション部分 -->
			<div id="contents_nav">
				<form action="CosServlet" method="post" name="form2">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
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
				</form>
			</div>
			<hr>
			<!-- コンテンツ内ボディ部分 -->
			<div id="contents_body">
				<form action="CosServlet" method="post" name="form3"
					onsubmit="clearTarget()">
					<div class="cart_state">
						<table>
							<caption>カートの内容</caption>
							<tr>
								<th>商品：</th>
								<td><%=juchu.getLines()%>商品</td>
							</tr>
							<tr>
								<th>金額：</th>
								<td align="right"><%=sKingaku%>円</td>
							</tr>
						</table>
						<div align="right">
							<input type="submit" name="order" value="カートに入れる">&nbsp;&nbsp;&nbsp;
							<input type="submit" name="order" value="カート内リスト">
						</div>
					</div>
					<br>
					<div align="right">購入数を入力して「カートに入れる」ボタンを押してください。</div>
					<div class="goods_list">
						<p>
							商品カテゴリ：<%=gsubctgName%>
						</p>
						<table>
							<colgroup>
								<col class="col_1">
								<col class="col_2">
								<col class="col_3">
								<col class="col_4">
							</colgroup>
							<tr>
								<th rowspan="2">商品コード</th>
								<th rowspan="2">商品名</th>
								<th><small>単価<br>（在庫状況）
								</small></th>
								<th>購入数</th>
							</tr>
						</table>
						<table>
							<colgroup>
								<col class="col_1">
								<col class="col_2">
								<col class="col_3">
								<col class="col_4">
							</colgroup>
							<%
								int tanka = 0;
								String sTanka = null; //カンマ編集後単価文字列
								Shohin shohin;
								for (int i = 0; i < shohinList.size(); i++) {
									shohin = (cosDataPack.Shohin) shohinList.get(i);
									//単価をカンマ編集する
									try {
										tanka = shohin.getShohinPriData().getHtanka();
										sTanka = nf.format(tanka);
									} catch (IllegalArgumentException iae) {
										System.out.println("IllegalArgumentException");
									}
							%>
							<tr>
								<td rowspan="2"><%=shohin.getShohinPriData().getGoodsCD()%></td>
								<td rowspan="2"><%=shohin.getShohinPriData().getGoodsName()%></td>
								<td align="right"><%=sTanka%>円</td>
								<%
									if ((shohin.getZaikoCnt() - shohin.getHikiateCnt()) <= 0) {
									//引き当て可能数が0以下なら、数量の入力を不可にする
								%>
								<td><input type="text" name="suryou<%=i%>" maxlength="4"
									readonly></td>
								<%
									} else {
								%>
								<td><input type="text" name="suryou<%=i%>" maxlength="4"></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>（<%=shohin.getZaikoHyouji()%>）
								</td>
								<td><input type="button" value="詳細"
									onclick="setCD('<%=shohin.getShohinPriData().getGoodsCD()%>')"></td>
							</tr>
							<%
								}
							%>
						</table>
					</div>
					<input type="hidden" name="hiddenCD"> <input type="hidden"
						name="order" value="詳細">
				</form>
			</div>
		</div>
		<!-- フッター部分 -->
		<div id="footer">
			<address>&copy;Office Goody 2011 All rights reserved Office
				Goody Co,Ltd.</address>
		</div>
	</div>
</body>
</html>
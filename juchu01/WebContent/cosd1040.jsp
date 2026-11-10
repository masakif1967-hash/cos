<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Shohin"%>
<%@ page import="cosDataPack.ShohinPrimalData"%>
<%@ page import="cosDataPack.Bungu"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="shohin" scope="session" class="cosDataPack.Shohin" />
<html lang="ja">
<%
	//商品データの取得
	ShohinPrimalData sp = shohin.getShohinPriData();
	Bungu bungu = (Bungu) shohin;
	DecimalFormat nf = new DecimalFormat("###,###,###"); //カンマ編集用クラス
	//単価をカンマ編集する
	String sTanka = null;
	try {
		sTanka = nf.format(bungu.getShohinPriData().getHtanka());
	} catch (IllegalArgumentException iae) {
		System.out.println("IllegalArgumentException");
	}
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd1040">
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
					<p><small>COSD1040</small></p>
					<h3>商品の詳細説明</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav"></div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<div id="goods_img">
						<table>
							<tr>
								<td><img src="<%=bungu.getImgURL()%>"
									alt="<%=bungu.getShohinPriData().getGoodsCD() %>" title="サンプル" />
								</td>
							</tr>
							<tr>
								<td
									style="border: 1px solid #000000; text-align: left; vertical-align: top; height: 10em;">
									<%=bungu.getComments() %>
								</td>
							</tr>
						</table>
					</div>
					<div id="goods_exp">
						<table>
							<tr>
								<th>商品コード</th>
								<td><%=bungu.getShohinPriData().getGoodsCD() %></td>
							</tr>
							<tr>
								<th>商品名</th>
								<td><%=bungu.getShohinPriData().getGoodsName()%></td>
							</tr>
							<tr>
								<th>単価</th>
								<td><%=sTanka%>円</td>
							</tr>
							<tr>
								<th>在庫</th>
								<td><%=bungu.getZaikoHyouji() %></td>
							</tr>
							<tr>
								<th>メーカー名</th>
								<td><%=bungu.getMakerName() %></td>
							</tr>
							<tr>
							<tr>
								<th>セット数</th>
								<td><%=bungu.getIrisu() %></td>
							</tr>
							<tr>
								<th>色</th>
								<td><%=bungu.getColor() %></td>
							</tr>
							<tr>
								<th>型番</th>
								<td><%=bungu.getKataban() %></td>
							</tr>
							<tr>
								<th>サイズ</th>
								<td><%=bungu.getSize() %></td>
							</tr>
							<tr>
								<th>材質</th>
								<td><%=bungu.getZaisitu() %></td>
							</tr>
						</table>
						<br>
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
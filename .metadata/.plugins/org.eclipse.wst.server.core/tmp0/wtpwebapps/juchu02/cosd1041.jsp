<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Shohin"%>
<%@ page import="cosDataPack.ShohinPrimalData"%>
<%@ page import="cosDataPack.Book"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.IllegalArgumentException"%>

<!DOCTYPE html>
<jsp:useBean id="shohin" scope="session" class="cosDataPack.Shohin" />
<html lang="ja">
<%
	//商品データの取得
	ShohinPrimalData sp = shohin.getShohinPriData();
	Book book = (Book) shohin;
	DecimalFormat nf = new DecimalFormat("###,###,###"); //カンマ編集用クラス
	//単価をカンマ編集する
	String sTanka = null;
	try {
		sTanka = nf.format(book.getShohinPriData().getHtanka());
	} catch (IllegalArgumentException iae) {
		System.out.println("IllegalArgumentException");
	}
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
<body id="cosd1041">
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
					<p><small>COSD1041</small></p>
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
								<td><img src="<%=book.getImgURL()%>"
									alt="<%=book.getShohinPriData().getGoodsCD() %>" title="サンプル" />
								</td>
							</tr>
							<tr>
								<td
									style="border: 1px solid #000000; text-align: left; vertical-align: top; height: 10em;">
									<%=book.getComments() %>
								</td>
							</tr>
						</table>
					</div>
					<div id="goods_exp">
						<table>
							<tr>
								<th>商品コード</th>
								<td><%=book.getShohinPriData().getGoodsCD() %></td>
							</tr>
							<tr>
								<th>商品名</th>
								<td><%=book.getShohinPriData().getGoodsName() %></td>
							</tr>
							<tr>
								<th>単価</th>
								<td><%=sTanka %>円</td>
							</tr>
							<tr>
								<th>在庫</th>
								<td><%=book.getZaikoHyouji() %></td>
							</tr>
							<tr>
								<th>出版社</th>
								<td><%=book.getMakerName() %></td>
							</tr>
							<tr>
							<tr>
								<th>セット数</th>
								<td><%=book.getIrisu() %></td>
							</tr>
							<tr>
								<th>巻数</th>
								<td><%=book.getVolume() %>巻</td>
							</tr>
							<tr>
								<th>ISBN-10</th>
								<td><%=book.getIsbn10() %></td>
							</tr>
							<tr>
								<th>ISBN-13</th>
								<td><%=book.getIsbn13() %></td>
							</tr>
							<tr>
								<th>著者</th>
								<td><%=book.getWriter() %></td>
							</tr>
							<tr>
								<th>ページ数</th>
								<td><%=book.getPage() %></td>
							</tr>
							<tr>
								<th>発行日</th>
								<td><%=book.getHakkouD() %></td>
							</tr>
							<tr>
								<th>寸法</th>
								<td><%=book.getSize() %></td>
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
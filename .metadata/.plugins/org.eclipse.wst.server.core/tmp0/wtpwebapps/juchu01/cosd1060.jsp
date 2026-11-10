<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cosDataPack.Juchu"%>
<%@ page import="cosDataPack.Kokyaku"%>
<%@ page import="cos.JuchuControl"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>

<!DOCTYPE html>
<jsp:useBean id="juchucontrol" scope="session" class="cos.JuchuControl" />
<html lang="ja">
<%
	//受注データ、顧客データの取得
	Juchu juchu = juchucontrol.getData();
	Kokyaku kokyaku = juchu.getKokyaku();
%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common1.css">
<title>Office Goody</title>
</head>
<body id="cosd1060">
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
					<p><small>COSD1060</small></p>
					<h3>お支払い方法・お届け先の入力</h3>
				</div>
				<!-- コンテンツ内ナビゲーション部分 -->
				<div id="contents_nav">
					<div class="user_info">
						<p><%=kokyaku.getKokyakuName()%>様 <input type="submit"
								name="top" value="ログオフ">
						</p>
					</div>
					<input type="submit" name="order" value="ご注文確認へ戻る"> <input
						type="submit" name="order" value="次へ">
				</div>
				<hr>
				<!-- コンテンツ内ボディ部分 -->
				<div id="contents_body">
					<table>
						<tr>
							<th>お支払い方法</th>
							<td colspan="3"><input type="radio" name="shiharaitype"
								value="0" checked>&nbsp;&nbsp;月末一括お支払い</td>
						</tr>
						<tr>
							<td colspan="4"><small>◎毎月月末にご請求書をお送りします。</small></td>
						</tr>
						<tr>
							<th>お届け先</th>
							<td colspan="3">
								<p>〒<%=kokyaku.getZip1()%>-<%=kokyaku.getZip2()%></p>
								<p><%=kokyaku.getAddress1()%>&nbsp;<%=kokyaku.getAddress2()%></p>
								<p><%=kokyaku.getKokyakuName()%>様</p>
								<P>TEL:<%=kokyaku.getTel1()%>-<%=kokyaku.getTel2()%>-<%=kokyaku.getTel3()%></P>
							</td>
						</tr>
						<tr>
							<th>お届け日</th>
							<td>
								<div class="delivery_day">
									<select name="nouhinyoteid">
										<%
											Calendar day = Calendar.getInstance();
											String nohinDay = null;
											//お届け日は30日以内
											for (int i = 0; i < 30; i++) {
												day.add(Calendar.DATE, 1);
												//最短3日のお届け
												if (i < 2)continue;
												nohinDay = day.get(Calendar.YEAR) + "-" +
												(day.get(Calendar.MONTH) + 1) + "-" +
												day.get(Calendar.DATE);
										%>
										<option label=<%=nohinDay%> value="<%=nohinDay%>"><%=nohinDay%></option>
										<%
											}
										%>
									</select>
								</div>
							</td>
							<th>お届け時間帯</th>
							<td>
								<div class="delivery_time">
									<select name="nouhinyoteirange">
										<option label="午前中" value="午前中">午前中</option>
										<option label="12:00-14:59" value="12:00-14:59">12:00-14:59</option>
										<option label="15:00-17:59" value="15:00-17:59">15:00-17:59</option>
										<option label="18:00-21:59" value="18:00-21:59">18:00-21:59</option>
									</select>
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
<%@ page language="java" contentType="text/html; charset=Windows-31J"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%
    Calendar objCal1 = Calendar.getInstance();
    Calendar objCal2 = Calendar.getInstance();
    objCal2.set(1970, 0, 1, 0, 0, 0);
    response.setDateHeader("Last-Modified", objCal1.getTime().getTime());
    response.setDateHeader("Expires", objCal2.getTime().getTime());
    response.setHeader("pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">

<link rel="stylesheet" href="css/style.css" type="text/css" />
<title>じゃんけん</title>
<script type="text/javascript" src="./js/main.js"></script>
</head>
<body>
	<h2>じゃんけん</h2>
	<br>
	<br>
	<br>
	<h1>
		<%=request.getAttribute("msg")%>
	</h1>
	<br>
	<br>
	<table class="mainTbl">
		<tr>
			<td width="40%"><h1>あなた</h1></td>
			<td>&nbsp;</td>
			<td width="40%"><h1>コンピュータ</h1></td>
		</tr>
		<tr>
			<td width="40%"><img
				src="<%=request.getAttribute("userPoseImg")%>"></td>
			<td>&nbsp;</td>
			<td width="40%"><img
				src="<%=request.getAttribute("compPoseImg")%>"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>
				<table class="tbl" align="left">
					<caption>コンピュータの対戦成績</caption>
					<tr>
						<td>対戦回数</td>
						<td><%=request.getAttribute("matchCnt")%>回</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>勝ち数</td>
						<td><%=request.getAttribute("winCnt")%>回</td>
						<td><%=request.getAttribute("winRate")%>％</td>
					</tr>
					<tr>
						<td>負け数</td>
						<td><%=request.getAttribute("loseCnt")%>回</td>
						<td><%=request.getAttribute("loseRate")%>％</td>
					</tr>
					<tr>
						<td>あいこ</td>
						<td><%=request.getAttribute("drawCnt")%>回</td>
						<td><%=request.getAttribute("drawRate")%>％</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<div align="center">ボタンを選択してください。</div>
	<form method="post" action="jangkeng" name="makePose">
		<table class="tbl">
			<tr>
				<td><input type="button" value="グー" name="GU" class="defBtn" onclick="btnCliked(this.name);"></td>
				<td><input type="button" value="チョキ" name="CHOKI"
					class="defBtn" onclick="btnCliked(this.name)"></td>
				<td><input type="button" value="パー" name="PA" class="defBtn" onclick="btnCliked(this.name)"></td>
			</tr>
		</table>
	</form>
	<br>
	<br>
</body>
</html>
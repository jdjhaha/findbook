<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<style>
table {
	width: 100%;
	border: 1px solid #333333;
}

td {
	padding: 10px;
	border: 1px solid #333333;
}
.b {
	border-collapse: collapse;
}
</style>

<meta charset="utf-8">
<title>책 상세 정보</title>
</head>
<body>
	<input type="hidden" id="search_value" name ="search_value" value="${search_value }"/>
	<b><font size="5" color="skyblue">책 상세 정보 화면입니다.</font></b>
	<br>
	<br>
	<table class="b">
		<tr>
			<td bgcolor="skyblue">제목</td>
			<td><c:out value='${title}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">도서 썸네일</td>
			<td><img src="<c:out value='${thumbnail}' />" /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">소개</td>
			<td><c:out value='${contents}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">ISBN</td>
			<td><c:out value='${isbn}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">저자</td>
			<td><c:out value='${authors}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">출판사</td>
			<td><c:out value='${publisher}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">출판일</td>
			<td><c:out value='${datetime}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">정가</td>
			<td><c:out value='${price}' /></td>
		</tr>
		<tr>
			<td bgcolor="skyblue">판매가</td>
			<td><c:out value='${sale_price}' /></td>
		</tr>
	</table>
	<br />
	<br />
	<input type="button" value="목록" onclick="main();" />

	<script type="text/javascript" src="/resources/js/jquery.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
		});
		function main() {
			location.href = "/main?search_value="+$("#search_value").val();
		}
	</script>
</body>
</html>
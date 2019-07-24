<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>나의 검색 이력</title>
</head>
<body>
	<form name="loginInfo" method="post" action="" onsubmit="">
		<!-- historyList -->
		<c:forEach var="keyword" items="${keywordList}" varStatus="status">
			<p><c:out value='${keyword.keyword}' />,<c:out value='${keyword.count}' /></p>
		</c:forEach>
		<br> 
	</form>
	
	<script type="text/javascript" src="/resources/js/jquery.js"></script>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
		});
	</script>
</body>
</html>
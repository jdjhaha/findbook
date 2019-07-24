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
		<c:forEach var="history" items="${historyList}" varStatus="status">
			<p><c:out value='${history.id}' /> , <c:out value='${history.keyword}' />,<c:out value='${history.time}' /></p>
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
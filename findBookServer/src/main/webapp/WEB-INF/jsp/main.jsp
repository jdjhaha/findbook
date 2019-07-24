<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css"/> 
<title>책 검색</title>
</head>
<body>
	<form id="myform" action="<c:url value='/detail' />" method="POST">
		<input type="hidden" id="title" name="title" value="" />
		<input type="hidden" id="thumbnail" name="thumbnail" value="" />
		<input type="hidden" id="contents" name="contents" value="" />
		<input type="hidden" id="isbn" name="isbn" value="" />
		<input type="hidden" id="authors" name="authors" value="" />
		<input type="hidden" id="publisher" name="publisher" value="" />
		<input type="hidden" id="datetime" name="datetime" value="" />
		<input type="hidden" id="price" name="price" value="" />
		<input type="hidden" id="sale_price" name="sale_price" value="" />
		<input type="hidden" id="search_value" name="search_value" value="" />
		
	</form>
	<b><font size="5" color="skyblue">책을 검색하세요.</font></b>
	<br>
	<br>
	<c:choose>
		<c:when test="${!empty logonId}">
			<h2>
				<font color="blue">${logonId}</font> 님
				로그인되었습니다.
			</h2>
			<table id="searchTable" class="table table-bordered">
				<thead>
					<tr>
						<th>썸네일</th>
						<th>책 제목</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<br>
			<br>
			<input type="button" value="로그아웃" onclick="logoutPro();" />
			<input type="button" value="나의 검색 이력" onclick="myHistory();" />
			<input type="button" value="인기검색어" onclick="keyword();" />
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				location.href="login";
			</script>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>
	<script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>

	<script type="text/javascript">
		var pageInfoObj;
		var search_value;
		var searchFilterInput;
		
		$(document).ready(function(){
			search_value ='${search_value}';
		var dataTable = $("#searchTable").DataTable({
				"lengthMenu": [ 10, 25, 50 ],
				"scrollY": "500px",
				"scrollCollapse": true,
				"responsive": true,
				"processing" : true,
				"serverSide" : true,
				"deferLoading": 0, 
				ajax : {
					type:"POST",
					url : "<c:url value='/search' />",
					data : function(data){
						
					},
					dataType : "JSON"
				},
				"dataSrc": function(result) {
					pageInfoObj = result;
				},
				columns : [
					{
						data : "thumbnail",
						"render" : function(data, type, row) {
							return '<img src="'+data+'" style="height:50px;" />';
						}
					},
					{ data : "title" }
				],
				"fnInitComplete" : function() {

					// 검색 영역 삭제
					searchFilterInput = $("div.dataTables_filter > label > input");
					searchFilterInput.unbind();
					

					$("#searchTable_filter>label").remove();
					
					// 기존 검색 input 제거
					searchFilterInput.remove();
					
					// 검색 필드
					var searchFilterDiv = $("div.dataTables_wrapper div.row:first-child");
					
					var normalSearchHtml =	$('<div id="normalSearchDiv" class="row" style="padding-top: 10px;"><label><input id="searchInput" type="search" class="form-control" placeholder="검색어" aria-controls="datatable-responsive"/><button id="normalSearchButton" class="btn btn-default" type="button" style="margin-left: 5px;">검색</button></label></div>');
					// 검색 영역 추가
					var normalSearch =	$(normalSearchHtml);
					
					// 검색 영역 추가
					searchFilterDiv.after(normalSearch);
					
					// 검색 필드 재 지정
					searchFilterInput = $("#searchInput");
					searchFilterInput.attr( 'maxlength', 100 );
					
					// 검색 버튼 처리
					$("#normalSearchButton").bind("click", function(e) {
						if(searchFilterInput.val().trim() == ''){
							alert('검색어를 입력하세요');
							return;
						}
						dataTable.search(searchFilterInput.val()).draw();
						addHistory();
					});
					
					// 엔터키 시 검색 처리
					$('input').bind("keyup", function(e) {
						if ( e.keyCode == 13 )
						{
							dataTable.search(searchFilterInput.val()).draw();
							addHistory();
						}
					});
					
				}
			});
			if(search_value != undefined && search_value != null && search_value !=''){
				dataTable.search(search_value).draw();
				searchFilterInput.val(search_value);
			}
			// row 클릭시 이벤트 처리
			var dataTableBody = $('#searchTable tbody');
	
			dataTableBody.on('click', 'tr', function() {
				var data = dataTable.row($(this)).data();
				
				if ( data )
				{
					console.log(JSON.stringify(pageInfoObj));
					var tempForm = $("#myform");

					$("#title").val(data["title"]);
					$("#thumbnail").val(data["thumbnail"]);
					$("#contents").val(data["contents"]);
					$("#isbn").val(data["isbn"]);
					$("#authors").val(data["authors"]);
					$("#publisher").val(data["publisher"]);
					$("#datetime").val(data["datetime"]);
					$("#price").val(data["price"]);
					$("#sale_price").val(data["sale_price"]);
					$("#search_value").val(searchFilterInput.val());
					
					tempForm.submit();
				}
			});
		});

		function logoutPro() {
			if (confirm('로그아웃 하시겠습니까?')) {
				$.ajax({
					url : '/logout',
					type : 'post',
					data : {},
					success : function(data) {
						if (data.logoutYn != undefined && data.logoutYn != null
								&& data.logoutYn == 'Y') {
							alert('로그아웃 성공!');
							location.href = '/login';
						} else {
							alert('로그아웃 실패!');
						}
					},
					error : function(err) {
						alert('에러 : ' + JSON.stringify(err));
					}
				});
			}
		}

		function myHistory() {
			location.href="/myHistory";
		}

		function keyword(){
			location.href="/keyword";
		}

		function addHistory(){
			
			$.ajax({
				url:'/historyAdd',
				type:'post', 
				data:{'keyword':searchFilterInput.val()}, 
				success: function(data) {
					
				},
				error: function(err) {
					
				}
			});
		}
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
</head>
<body>
	<form name="loginInfo" method="post" action="" onsubmit="">
		<table>
			<tr>
				<td bgcolor="skyblue">아이디</td>
				<td><input type="text" id="id" name="id" maxlength="50"></td>
			</tr>
			<tr>
				<td bgcolor="skyblue">비밀번호</td>
				<td><input type="password" id="password" name="password" maxlength="50"></td>
			</tr>
		</table>
		<br> 
		<input type="button" value="로그인" onclick="login();"/> 
		<input type="button" value="회원가입" onclick="goMemberRegist();" />
	</form>
	
	<script type="text/javascript" src="/resources/js/jquery.js"></script>
	<script type="text/javascript" src="/resources/js/jsencrypt.js"></script>
	<script type="text/javascript" src="/resources/js/core.min.js"></script>
	<script type="text/javascript" src="/resources/js/sha256.min.js"></script>
	
	<script type="text/javascript">
		var crypt;
		var duplYn;
		
		$(document).ready(function(){
			crypt = new JSEncrypt();
			crypt.setPublicKey('${publicKey}');
		});
		
		function login(){
			var checkId = $("#id").val();
			var password = $('#password').val();

			if(checkId == null || checkId == undefined || checkId.trim() == ''){
				alert('아이디를 입력 하세요.');
				return;
			}

			if(password == null || password == undefined || password.trim() == ''){
				alert('비밀번호를 입력 하세요.');
				return;
			}

			var encryptedId = crypt.encrypt(checkId);
			var shaPw = CryptoJS.SHA256(password).toString(); 

			$.ajax({
				url:'/checkIn',
				type:'post', 
				data:{
					'encryptedId':encryptedId,
					'password':shaPw
				}, 
				success: function(data) {
					if(data.logon != undefined && data.logon != null && data.logon == 'Y'){
						alert('로그인 성공!');
						location.href="/main";
					}else{
						alert('로그인 실패!');
					}
				},
				error: function(err) {
					alert('에러 : '+JSON.stringify(err));
				}
			});
		}
		
		function goMemberRegist(){
			location.href="/member/regist";
		}
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Member Regist</title>
</head>
<body>
	<div id="wrap">
		<br> <br> <b><font size="6" color="gray">회원가입</font></b> <br>
		<br> <br>
		<form method="post" action="" name="userInfo">
			<table>
				<tr>
					<td id="title">아이디</td>
					<td>
						<input type="text" id="id" name="id" maxlength="50">
						<input type="button" onclick="checkDuplId();" value="중복확인">
					</td>
				</tr>
				<tr>
					<td id="title">비밀번호</td>
					<td><input type="password" id="password" name="password" maxlength="50">
					</td>
				</tr>
				<tr>
					<td id="title">비밀번호 확인</td>
					<td><input type="password" id="passwordcheck" name="passwordcheck" maxlength="50">
					</td>
				</tr>
			</table>
			<br>
			<input type="button" onclick="add();" value="가입" />
			<input type="button" value="취소" />
		</form>
	</div>
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

		function checkDuplId() {
			var checkId = $("#id").val();
			var encryptedId = crypt.encrypt(checkId);
			
			$.ajax({
				url:'/member/checkDuplId',
				type:'post', 
				data:{'encryptedId':encryptedId}, 
				success: function(data) {
					duplYn = data['duplYn'];
					if(duplYn == 'N'){
						alert('사용 가능한 아이디 입니다');
					}else{
						alert('중복되는 아이디가 존재합니다');
					}
				},
				error: function(err) {
					
				}
			});
		}

		function add() {
			var checkId = $("#id").val();
			var password = $('#password').val();
			var passwordcheck = $('#passwordcheck').val();

			if(checkId == null || checkId == undefined || checkId.trim() == ''){
				alert('아이디를 입력 하세요.');
				return;
			}

			if(duplYn == null || duplYn == undefined ){
				alert('아이디 중복체크를 수행하세요.');
				return;
			}

			if(duplYn == 'Y'){
				alert('중복되는 아이디가 존재합니다');
				return;
			}

			if(password == null || password == undefined || password.trim() == ''){
				alert('비밀번호를 입력 하세요.');
				return;
			}

			if(passwordcheck == null || passwordcheck == undefined || passwordcheck.trim() == ''){
				alert('비밀번호 확인 값을 입력 하세요.');
				return;
			}

			if(password != passwordcheck){
				alert('동일한 비밀번호를 입력 하세요.');
				return;
			}
			
			var shaPw = CryptoJS.SHA256(password).toString(); 
			
			var encryptedId = crypt.encrypt(checkId);
			$.ajax({
				url:'/member/add',
				type:'post', 
				data:{
					'encryptedId':encryptedId,
					'password':shaPw
				}, 
				success: function(data) {
					if(data.registYn != undefined && data.registYn != null && data.registYn == 'Y'){
						alert('회원가입 성공!');
						location.href="/login"
					}else{
						alert('회원가입 실패!');
					}
				},
				error: function(err) {
					alert('에러 : '+JSON.stringify(err));
				}
			});
		}
		
	</script>
</body>
</html>

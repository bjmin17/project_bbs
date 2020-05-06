<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
<link rel="stylesheet" href="${rootPath}/css/login-css.css?ver=20200503001">
<script>
	$(function(){
		$(document).on("click","#btn-join",function(){
			// 유효성검사
			// id, password가 입력되지 않았을 때 경고
			let username = $("#username")
			let password = $("#password")
			let re_password = $("#re_password")
			
			if(username.val() == "") {
				alert("아이디를 입력하세요")
				username.focus()
				return false
			}
			
			if(password.val() == "") {
				alert("비밀번호를 입력하세요")
				password.focus()
				return false
			}
			
			if(re_password.val() == "") {
				alert("비밀번호 확인을 입력하세요")
				re_password.focus()
				return false
			}
			
			if(password.val() != re_password.val()) {
				alert("비밀번호와 비밀번호 확인이 다릅니다")
				re_password.focus()
				return false
			}
			
			$("form").submit()
			
		})
		
		// 현재 입력박스에서 포커스가 벗어났을 때 발생하는 이벤트
		$(document).on("blur","#username",function(){
			let username = $(this).val()
			if(username == "") {
				$("#m_username").text("아이디는 반드시 입력해야 합니다")
				$("#username").focus()
				return false;
			}
			
			$.ajax({
				url : "${rootPath}/user/idcheck",
				method : "GET",
				data : {username : username},
				success : function(result) {
					
					if(result == "EXISTS") {
						
						$("#m_username").text("* 이미 가입된 사용자이름 입니다.")
						$("#m_username").css("color","red")
						$("#username").focus()
						return false
					} else {
						$("#m_username").text("* 사용가능한 사용자ID")
						$("#m_username").css("color","blue")
					}
					
				},
				error:function(){
					$("#m_username").text("* 서버통신오류.")
					//alert("서버와 통신 오류")
				}
				
			})
			
		})
		// 현재 DOM 화면에 class가 view_pass인 모든것에 적용하라
		$(".view_pass").each(function(index,input){
			// 매개변수로 전달된 input을 선택하여
			// 변수 $input에 임시 저장
			let $input = $(input)
			$("input#view_pass").click(function(){
				let change = $(this).is(":checked") ? "text" : "password";
				// 가상의 input 생성
				// <input type='text'> 또는 <input type='password'>
				let ref = $("<input class='form-control view_pass'  placeholder='비밀번호' type='" + change + "' />'")
					.val($input.val())
					.insertBefore($input);
				
				// 앞에 있는 input 지우고 새로 입력하라
				$input.remove();
				$input = ref;
			})
			
		})
		
	})
</script>
	<style>
		section.container {
			border-radius: 10px; 
		}
	</style>
</head>

<body>
	<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
	<section class="container">
		<style>
			.message {
				font-weight: bold;
				font-size: 0.3rem;
			}
		</style>
		<form:form method="POST" action="${rootPath}/user/join" modelAttribute="userVO">
			<div class="container p-3 my-3 bg-primary text-white">
				<h2>회원가입</h2>
			</div>
			<!-- 
			<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}">
			-->
			<div class="form-group">
				<label for="username">User ID</label> 
				<form:input type="text"	class="form-control" path="username" placeholder="User ID"/>
			</div>
			<div class="message" id="m_username"></div>
			<div class="form-group">
				<label for="password">비밀번호</label> 
				<form:input type="password"	class="form-control view_pass" path="password" placeholder="비밀번호"/>
			</div>
			<div class="form-group">
				<label for="re_password">비밀번호 확인</label> 
				<input type="password"	class="form-control view_pass" id="re_password" name="re_password"	placeholder="비밀번호 확인">
			</div>
			<div class="option">
				<label for="view_pass">
					<input type="checkbox" id="view_pass">비밀번호 보이기
				</label>
			</div>
			<div class="mb-3">
				<button type="button" class="btn btn-primary" id="btn-join">회원가입</button>
				<button type="button" class="btn btn-success" id="btn-loss">ID/비밀번호 찾기</button>
			</div>
		</form:form>
	</section>
</body>
</html>
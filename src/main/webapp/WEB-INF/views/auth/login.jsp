<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
<link rel="stylesheet" href="${rootPath}/css/login-css.css?ver=20200503001">
	<script>
	$(function(){
		$(document).on("click","button.join",function(){
			document.location.href="${rootPath}/join"
		})
		
		$(".btn-login").click(function(){
			let username = $("#username").val()
			let password = $("#password").val()
			
			if(username == "") {
				alert("아이디를 입력하세요")
				$("#username").focus()
				return false
			} else if (password == "") {
				alert("비밀번호를 입력하세요")
				$("#password").focus()
				return false
			} else {
				$("form").submit()
			}
		})
	})
	</script>
	<style>
		section.container {
			border-radius: 10px; 
			height: 360px;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
	<section class="container">
		<form:form action="${rootPath}/login" method="POST">
			<div class="container p-3 my-3 bg-primary text-white text-center">
				<h2>로그인</h2>
			</div>
			
			<div>
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					<span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
				
				</c:if>
			</div>
			
			<div class="form-group">
				<label for="username">User ID</label> 
				<input type="text" class="form-control"	id="username" name="username" placeholder="User ID">
			</div>
			<div class="form-group">
				<label for="password">Password:</label> 
				<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호">
			</div>
			<div class="mb-3">
				<button type="button" class="btn btn-primary btn-login">로그인</button>
				<button class="btn btn-success join" type="button">회원가입</button>
			</div> 
			
		</form:form>
	</section>
</body>
</html>
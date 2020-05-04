<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
	<script>
		$(function(){
			$("input").prop("readonly",true)
			

			
			$(document).on("click",".btn_update",function(){
				let pass = $("#password").val()
				if(pass == "") {
					alert("수정하려면 비밀번호를 입력한 후 \n"
							+"다시 수정하기 버튼을 클릭하세요")
					$("div.password").css("display","block")
					$("#password").prop("readonly",false)
					$("#password").focus()
					return false
				}
				/* 보낼 데이터가 하나 뿐이라면 data뒤에 JSON 쓰지 않고 data값 하나만 넘겨줘도 된다.*/
				if(pass != "") {
					
					let token = $()
					
					$.ajax({
						url : '${rootPath}/user/password',
						method : 'POST',
						data : {
							password:pass,
							"${_csrf.parameterName}" : "${_csrf.token}"
						},
						success : function(result) {
							if(result == "PASS_OK") {
								$("input").prop("readonly",false)
								$("input").css("color","blue")
								$("button#btn_save").prop("disabled",false)
								$("button.btn_save").prop("disabled",false)
								$("button#btn_update").prop("disabled",true)
								$("button.btn_update").prop("disabled",true)
							} else {
								alert("비밀번호가 일치하지 않습니다")
							}
						}, 
						error : function() {
							$("#password").focus()
							alert("서버 통신 오류")
						}
					})
				}
				
			})
			
		})
	</script>
	<style>
		body {
			width: 100%
		}
		section {
			left:0;
			width: 100%;
			
						/*height: 2000px;*/
			margin: 0 auto;
			margin-top: 92px;
		}

		section.container {
			border-radius: 10px; 
		}
		
		section h2 {
		}
		
		div.paswword-div {
			height: 40px;
		}
		
		form {
			width: 70%;
			margin: 10px auto;
		}
		
		form div.password {
			display: none;
		}
		
		form input.auth {
			display: block;
		}
		
		section#main-body {

		}
		
		.tbl_mypage {
			position: relative;
		    width: 100%;
		    table-layout: fixed;
		    border-spacing: 0;
		    border-collapse: collapse;
		    word-wrap: break-word;
		    word-break: keep-all;
		    border: 0;
		    border-bottom: 1px solid #e5e5e5;
		}
		
		.tbl_mypage tr {
			display: table-row;
		    vertical-align: inherit;
		    border-color: inherit;
		    
		}
		
		.tbl_mypage th {
			color: #333;
		    border-right: 1px solid #e5e5e5;
		    background: #f9f9f9;
		    padding : 32px 31px;
		    height:165px;
		}
		
		.tbl_mypage td {
			padding: 32px 0 32px 30px;
			display: flex;
			flex-direction: column;
		}
		
		.pre_content span {
			width: 100px;
		}
		
		button.btn_user {
			border: 1px solid #bfbfbf;
	    	background: #fff;
	    	font-weight: bold;	
	    	margin: auto;
	    	
			padding:5px 6px ;
		}
		
		button.btn_content {
			border: 1px solid #bfbfbf;
	    	background: #fff;
	    	font-weight: bold;	
	    	color: blue;
	    	
			padding:10px 12px ;
		}
		
		button#btn_loss_pass {
			border: 1px solid #bfbfbf;
	    	background: #fff;
	    	font-weight: bold;	
	    	
			padding:10px 12px ;
		}
		
		.tbl_mypage td input {
			float: left;
		    width: 147px;
		    margin: 3px 3px 3px 0;
		    padding-left: 0;
		    letter-spacing: 0;
		    border: 1;
		}
		
		.main-h{
			top: 
		}
	
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
	<section id="main-body" class="container">
		<h2>${userVO.username}의 MYPAGE</h2>
		<hr/>
		<form:form modelAttribute="userVO">
			<div class="paswword-div">
				<div class="password">
					<div class="input-group mb-3 input-group-sm">
						<div class="input-group-prepend">
							<span class="input-group-text">비밀번호</span>
						</div>
						<input type="password" id="password" class="form-control" placeholder="비밀번호를 입력!!">
					</div>
				<!-- 
					<label for="password">비밀번호 : 
					<input type="password" id="password" class="form-control mt-3 mb-3" placeholder="비밀번호를 입력!!"/>
					</label>
					<button class="btn btn_content btn_update">비밀번호 입력</button>
				 -->
				</div>
				<div>
				</div>
			</div>
			<table class="tbl_mypage">
				<tr>
					<th>이름</th>
					<td>
						<div class="pre_content">
							<span>${userVO.username}</span>
						</div>
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<div class="pre_content">
							<span>${userVO.email}</span>
							<button type="button" class="btn btn_user" data-toggle="collapse" data-target="#update_email">수정</button>
						</div>
						<div id="update_email" class="collapse form-group">
							<form:input type="email" path="email" class="form-control" placeholder="E-mail"/>
							<button class="btn btn_content btn_update" type="button">수정하기</button>
							<button class="btn btn_content btn_save">저장</button>
						</div>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<div class="pre_content">
							<span>${userVO.phone}</span>
							<button type="button" class="btn btn_user" data-toggle="collapse" data-target="#update_phone">수정</button>
						</div>
						<div id="update_phone" class="collapse form-group">
							<form:input type="text" path="phone"  class="form-control" placeholder="전화번호"/>
							<button class="btn btn_content btn_update" type="button">수정하기</button>
							<button class="btn btn_content btn_save">저장</button>
						</div>
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>
						<div class="pre_content">
							<span>${userVO.address}</span>
							<button type="button" class="btn btn_user" data-toggle="collapse" data-target="#update_addr">수정</button>
						</div>
						<div id="update_addr" class="collapse form-group">
							<form:input type="text" path="address"  class="form-control" placeholder="주소"/>
							<button class="btn btn_content btn_update" type="button">수정하기</button>
							<button class="btn btn_content btn_save">저장</button>
						</div>
					</td>
				</tr>
			</table>
			<div align="right">
				<button type="button" class="btn mt-3" id="btn_loss_pass">비밀번호 찾기</button>
			</div>
		</form:form>
	</section>
</body>
</html>
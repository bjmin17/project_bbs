<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<style>
section {
	margin: 10px auto;
	width: 70%;
}

form {
	width: 100%;
	margin: 10px auto;
}

form label {
	display: inline-block;
	width: 150px;
	text-align: right;
}

form input.auth {
	display: block;
}
</style>
<section>
	<form:form modelAttribute="userVO">

		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<label for="username" class="input-group-text">UserName</label>
			</div>
			<form:input type="text" path="username" class="form-control" />
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<label for="email" class="input-group-text">Email</label>
			</div>
			<form:input type="text" path="email" class="form-control" />
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<label for="phone" class="input-group-text">Phone</label>
			</div>
			<form:input type="text" path="phone" class="form-control" />
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<label for="address" class="input-group-text">Address</label>
			</div>
			<form:input type="text" path="address" class="form-control" />
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<label for="address" class="input-group-text">계정활성화</label>
			</div>
			<form:checkbox path="enabled" class="form-control" />
		</div>

		<div id="auth_box">
			<div align="right">
				<button type="button" class="btn btn-success mb-3" id="auth_append">권한
					정보 입력 추가</button>
			</div>
			<c:if test="${not empty userVO.authorities}">
				<!-- <select id="auth" class="form-control mb-3" name="auth">
					<option value="ADMIN">ADMIN</option>
					<option value="ROLE_ADMIN">ROLE_ADMIN</option>
					<option value="USER">USER</option>
					<option value="ROLE_USER">ROLE_USER</option>
				</select>
				 -->
				<div id="auth_list">
					<h6>
						<b>현재 권한</b>
					</h6>
					<c:forEach items="${userVO.authorities}" var="auth"
						varStatus="index">
						<!-- <p>${index.index+1}번째권한 : ${auth.authority}</p> -->
						<!-- <input name="auth" value="${auth.authority}" class="auth form-control mb-3"> -->
						<div class="input-group mb-3">
							<input name="auth" value="${auth.authority}"
								class="auth form-control" placeholder="Search">
							<div class="input-group-append">
								<button class="btn btn-danger btn-delete" type="button"
									data-id="${auth.id}" data-username="${auth.username}">&times</button>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
		<div align="right">
			<button type="button" class="btn btn-success" id="btn_save">저장</button>
		</div>
	</form:form>
</section>
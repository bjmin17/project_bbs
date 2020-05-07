<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/views/include/include-head.jspf" %>
<style>
#b_content {
	width: 100%;
}
</style>
<script>
	$(function(){
		$(document).on("click","button.btn-save",function(){
			let b_subject = $("#b_subject")
			let b_text = $("#b_text")
			
			if(b_subject.val() == "") {
				alert("제목을 입력하세요")
				b_subject.focus()
				return false
			}
			
			if(b_text.val() == "") {
				alert("내용을 입력하세요")
				b_text.focus()
				return false
			}
			
			$("form").submit()
		})
	})
</script>
</head>
<body>
<%@ include file = "/WEB-INF/views/include/include-nav.jspf" %>
	<section class="container-fluid">
		<fieldset>
			<form:form method="POST" modelAttribute="bbsVO">
				<div class="form-group">
					<input id="b_subject" name="b_subject" class="form-control" placeholder="제목" value="${BBS.b_subject}">
				</div>
				<div class="form-group summer">
					<textarea id="b_text" name="b_text" rows="20" cols="100" >${BBS.b_content}</textarea>
				</div>
				<div class="form-group d-flex justify-content-end">
					<button type="button" class="btn btn-primary btn-save mr-2">저장</button>
					<a href="${rootPath}/board"><button type="button" class="btn btn-success">목록으로</button></a>
				</div>
			</form:form>
			
		</fieldset>
	</section>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/views/include/include-head.jspf" %>
<style>
#b_text {
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
		
		var toolbar = [
			['style',['bold','italic','underline'] ],
			['fontsize',['fontsize']],
			['font Style',['fontname']],
			['color',['color']],
			['para',['ul','ol','paragraph']],
			['height',['height']],
			['table',['table']],
			['insert',['link','hr','picture']],
			['view',['fullscreen','codeview']]
			
		]
		
		// summernote의 드롭앤드랍 기능 가로채기 위해서 callbacks 생성
		// onImageUpload : function(files,editor,isEdit)
		// 파일정보는 files에, editor에는 섬머노트 자체가 전달된다.
		$("#b_text").summernote({
			lang:'ko-KR',
			placeholder:'본문을 입력하세요',
			width:'100%',
			toolbar:toolbar,
			height:'400px',
			disableDragAndDrop : false,
			callbacks : {
				onImageUpload : function(files,editor,isEdit){
				
					for(let i = files.length - 1; i >=0 ; i--) {
						// 파일을 1개씩 업로드할 함수
						upFile(files[i],this); // files의 i번째 파일을 업로드,,,, this = editor, 섬머노트 그 자체를 말한다.
					}
				}
			}
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
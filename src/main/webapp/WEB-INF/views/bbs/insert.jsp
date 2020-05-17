<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/views/include/include-head.jspf" %>
<sec:csrfMetaTags/>
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
<style>
#b_text {
	width: 100%;
}
</style>
<script>
	$(function(){
		var rootPath = "${rootPath}"
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    })
		
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
		
		// 사진 업로드 기능 
		$("#d_d_box").on('dragover',function(e){
			$("#d_d_box h3").text("파일을 내려 놓으세요!!!")
			return false
		})
		
		/*
			file 1개를 Drag and Drop으로 업로드 수행하기
			업로드 되면서 e가 그 정보를 담고 있음.
			e.originalEvent.dataTransfer.files : 정보를 뽑아내고
			0번째 위치 값을 뽑아냄
		*/
		
		$("#d_d_box").on('drop',function(e){
			$("#d_d_box h3").text("파일 업로드 중!!!")
			
			// drop한 파일리스트 추출
			let files = e.originalEvent.dataTransfer.files
			
			// 리스트에서 첫번째 파일만 추출
			let file = files[0]
			//alert(file.name)
			
			// 추출된 파일 정보를 서버에 먼저 업로드
			
			// js FormData 클래스를 
			// 사용해서 서버에 파일 업로드 준비
			let formData = new FormData()
			formData.append('file',file)
			
			$.ajax({
				url : "${rootPath}/board/file_up",
				type : "POST",
				data : 	formData,
				enctype : "multipart/form-data",
				// 파일을 올릴 때는 이 두가지가 필수
				processData : false, /* 파일업로드 필수 옵션 */
				contentType : false, /* 파일업로드 필수 옵션 */
				
				success : function(result) {
					//alert("사진 업로드 ajax")					
					//alert("사진 rest컨트롤러 거쳐온 값 : " + result)					
					if(result == 'FAIL') {
						alert("파일 업로드 오류")
					} else {
						$("#img_file").val(result)
						$("#img_view").css("display","block")
						$("#img_view").attr("src",'${rootPath}/images/' + result)
						
						$("#d_d_box h3").text("파일업로드 성공!!!")
						$("#d_d_box h3").css("display")
					}
					
					//alert(result)
				},
				
				error:function() {
					alert("서버 통신 오류")
				}
			})
			
			return false
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
					<input id="b_subject" name="b_subject" class="form-control" placeholder="제목" value="${bbsVO.b_subject}">
				</div>
				<div class="form-group summer">
					<textarea id="b_text" name="b_text" rows="20" cols="100" >${bbsVO.b_text}</textarea>
				</div>
				<div class="input_box">
					<input type="hidden" name="b_file" id="img_file" value="${bbsVO.b_file}"/>
					<div id="d_d_box">
						<h3>이미지를 올려놓으세요</h3>
						<img id="img_view" height="95%">
					</div>		
				</div>
				<c:if test="${!empty bbsVO.b_file}">
					<div class="input_box">
						<img src="${rootPath}/images/${bbsVO.b_file}" width="200px">
					</div>
				</c:if>
				<div class="form-group d-flex justify-content-end">
					<button type="button" class="btn btn-primary btn-save mr-2">저장</button>
					<a href="${rootPath}/board"><button type="button" class="btn btn-success">목록으로</button></a>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</form:form>
			
		</fieldset>
	</section>
</body>
</html>
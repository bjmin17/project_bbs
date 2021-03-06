<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
<script>
$(function(){
	
	// let c_id = $(this).closet("div").data("id")
	// 가장 인접한 곳(closet)에 있는 div를 찾아서 거기 있는 id를 가져오라
	//$(document).on("click","div.cmt-item-del",function(event){
	$("div.cmt-item-del").click(function(){	
		
		// 나를 감싸고 있는 곳으로 이벤트가 전파되는 것을
		// 그만두어라
		event.stopPropagation()
		if(!confirm("댓글을 삭제할까요?")) {
			return false
		}
		
		// $(this).parent("div")
		// 현재 자신을 감싸고 있는 가장 가까운 div를 찾아라
		let c_id = $(this).parent("div").data("id")
		alert("item-del : " + c_id)
		
		$.ajax({
			url : "${rootPath}/comment/delete/",
			data : {
				c_id : c_id,
				b_id : "${BBS.b_id}",
				"${_csrf.parameterName}" : "${_csrf.token}"
			},
			type : "POST",
			success : function(result){
				alert("댓글 삭제 성공")
			}
		})
	})
	
	// 버튼 별로 기능 분류
	//$(document).on("click","button",function(){
	$("button").click(function(){
		
		let txt = $(this).text()
		if(txt == '수정'){
			//alert("수정")
			document.location.href="${rootPath}/board/update?b_id=${bbsVO.b_id}"
					
		} else if(txt == '삭제') {
			if(confirm("삭제할까요?")) {
				//document.location.replace("${rootPath}/board/delete?b_id=${bbsVO.b_id}")
				$.ajax({
					url : "${rootPath}/board/delete",
					data : {b_id : "${bbsVO.b_id}"},
					type : "GET",
					success : function(result){
						alert("삭제 성공")
						//document.location.replace("${rootPath}/board/delete?b_id=${bbsVO.b_id}")
					},
					error : function() {
						alert("삭제 불가")
					}
				})
			}
		} else if(txt == '저장') {
			var formData = $("form.repl").serialize()
			$.ajax({
					url : "${rootPath}/comment/insert",
					data : formData,
					type : "POST",
					success : function(result){
						//alert("댓글 저장 성공")
						//$(".modal-main").css("display","none")
						$("div.cmt-list").html(result)
					},
					error : function() {
						alert("서버 통신 오류")
					}
					
				
				
			})
		} else if(txt == '목록') {
			document.location.href="${rootPath}/board"
				+"?currentPageNo=1&search=&kategorie="
		}
		
	})
	
	$("#ftu").click(function(){
		
		let id = ${bbsVO.b_id}
		//alert(${bbsVO.b_id})
		//alert(id)
		
		b_id = $(this).data("id")
		//alert("b_id값:" + b_id)
		$.ajax({
					url : "${rootPath}/board/recommend_up",
					data : {
						b_id : id,
						"${_csrf.parameterName}" : "${_csrf.token}"
						},
					type : "POST",
					success : function(result){
						if(result < 1){
							alert("이미 추천했습니다.")
						} else {
							alert("추천 성공")
							document.location.replace("${rootPath}/board/detail?b_id=${bbsVO.b_id}")
						}
					},
					error : function() {
						alert("추천 불가")
					}
		})
	})
	
	$("#ftd").click(function(){
		
		let id = ${bbsVO.b_id}
		//alert(${bbsVO.b_id})
		//alert(id)
		$.ajax({
					url : "${rootPath}/board/recommend_down",
					data : {
						b_id : id,
						"${_csrf.parameterName}" : "${_csrf.token}"
						},
					type : "POST",
					success : function(result){
						if(result < 1){
							alert("이미 추천했습니다.")
						} else {
							alert("추천 성공")
							document.location.replace("${rootPath}/board/detail?b_id=${bbsVO.b_id}")
						}
					},
					error : function() {
						alert("추천 불가")
					}
		})
	})
	

	// 댓글 추천
	$(".cftu").click(function(){
		let id = $(this).data("id")
		//alert(id)
		
		let bid = $(this).data("bid")
		//alert("b_id값:"+bid)
		
		let bbsvoid = "${bbsVO.b_id}"
		//alert("bbsvoid : " + bbsvoid)
		$.ajax({
					url : "${rootPath}/comment/recommend_up",
					data : {
						c_id : id,
						"${_csrf.parameterName}" : "${_csrf.token}"
						},
					type : "POST",
					success : function(result){
						if(result < 1){
							alert("이미 추천했습니다.")
						} else {
							alert("추천 성공")
							document.location.replace("${rootPath}/board/detail?b_id=${bbsVO.b_id}")
						}
					},
					error : function() {
						alert("추천 불가")
					}
				})
			
	})
	
	// 댓글 비추천
	$(".cftd").click(function(){
		let id = $(this).data("id")
		//alert(id)
		
		let bid = $(this).data("bid")
		//alert("b_id값:"+bid)
		
		$.ajax({
					url : "${rootPath}/comment/recommend_down",
					data : {
						c_id : id,
						"${_csrf.parameterName}" : "${_csrf.token}"
						},
					type : "POST",
					success : function(result){
						if(result < 1){
							alert("이미 추천했습니다.")
						} else {
							alert("비추천 성공")
							document.location.replace("${rootPath}/board/detail?b_id="+bid)
						}
					},
					error : function() {
						alert("추천 불가")
					}
				})
	})
	
})
</script>
<style>
.bbsview_text{
	height: 400px;
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
	<section>
	<article>
		<p>${bbsVO.b_subject}</p>
		
		<span>${bbsVO.b_date}</span>
		<span>${bbsVO.b_time}</span>
		<span>추천 <i class="far fa-thumbs-up" ></i> ${bbsVO.b_recommend} </span>
		<span>조회 <i class="far fa-eye"></i> ${bbsVO.b_count}</span>
		<hr/>
	</article>
	<article>
		<div class="bbsview_text">
			<p>${bbsVO.b_text}</p>
		</div>
		<div>
			<c:if test="${!empty bbsVO.b_file}">
				<img src="${rootPath}/images/${bbsVO.b_file}" width="500px" height="300px">
			</c:if>
			
		</div>
		<div align="center">
			<button class="w3-button w3-khaki w3-round-large" id="ftu" type="button" data-id="${bbsVO.b_id}">추천<i class="far fa-thumbs-up" ></i></button>
			<button class="w3-button w3-khaki w3-round-large" id="ftd" type="button" data-id="${bbsVO.b_id}">비추천<i class="far fa-thumbs-down" ></i></button>
		</div>
		<hr/>
	</article>
	<article>
		<div><b>댓글 작성란</b></div>
		<form:form method="POST" modelAttribute="commentVO" class="repl">
			<div class="row p-4">
				<div class="col-2">
					<input name="c_id" id="c_id" class="form-control" type="hidden" value="0">
					<input name="c_b_id" class="form-control" type="hidden" value="${bbsVO.b_id}">
					<input name="c_writer" id="c_writer" class="form-control" placeholder="작성자" value="${loginUsername}" readonly="readonly">
				</div>
				<div class="col-8">
					<input name="c_subject" id="c_subject" class="form-control" placeholder="댓글을 입력하세요">
				</div>
				<div class="col-2 d-flex justify-content-start">
					<button type="button" class="btn btn-primary mr-2 commentInsert btn-cmt-save">저장</button>
					<button type="button" class="btn btn-success"><i class="fas fa-list"></i>목록</button>
				</div>
			</div>	
		</form:form>
		<hr/>
		<div align="right" class="mb-5">
			<c:if test="${bbsVO.b_writer == loginUsername || loginUsername == 'admin'}">
				<button class="btn btn-warning btn-save"><i class="fas fa-edit"></i>수정</button>
				<button class="btn btn-danger btn-delete"><i class="fas fa-trash-alt"></i>삭제</button>
			</c:if>
			<button type="button" class="btn btn-success"><i class="fas fa-list"></i>목록</button>
		</div>
		<hr/>		
		<div class="p-4 cmt-list">
			<%@ include file="/WEB-INF/views/comment/comment_list.jsp" %>
		</div>
	</article>
</section>
</body>
</html>
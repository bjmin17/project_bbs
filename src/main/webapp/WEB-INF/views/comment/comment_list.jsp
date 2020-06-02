<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>

<style>
.cmt {
	width: 80%;
	margin : 0 auto;
}

.font-awesome {
	font-size: 30px;
}

.fa-thumbs-up, .fa-thumbs-down {
	cursor: pointer;
}
</style>
<script>
$(function(){

})
</script>
<div class="row p-4 cmt-item">
</div>
<c:forEach var="COMMENT" items="${COMMENT}">
	<hr/>
	<div class=" p-4 cmt-item" data-id="${COMMENT.c_id}">
		<!-- <div class="col-2 m-1 writer"><b>${COMMENT.c_writer}</b></div>
		<div class="col-6 m-1 subject">${COMMENT.c_subject}</div>
		<div class="col-2 cmt-item-repl"><b>${COMMENT.c_date_time}</b></div>
		<c:if test="${COMMENT.c_writer == loginUsername || loginUsername == 'admin'}">
			<div class="col-1 cmt-item-del"><b>&times;</b></div>
		</c:if> -->
		<p>
		<div class="row">
			<div class="font-awesome">
				<p class="cftu" data-id="${COMMENT.c_id}"><i class="far fa-thumbs-up" ></i></p>
				<p>
				<span>${COMMENT.c_recommend}</span>
				<p>
				
				<p class="cftd" data-id="${COMMENT.c_id}" data-bid="${bbsVO.b_id}"><i class="far fa-thumbs-down"></i></p>
			</div>
			<div class="row cmt cmt-item" data-id="${COMMENT.c_id}">
				<div class="col-2 mt-1 ml-1 mb-1 writer">
					<span style="font-size: 20px"><b>${COMMENT.c_writer}글쓴 사람 아이디</b></span>
				</div>
				<div class="col-4 mt-1 mb-1 date">
					<span style="color: gray">${COMMENT.c_date_time}</span>
				</div>
				<div class="col-4 m-1 subject"></div>
				<c:if test="${COMMENT.c_writer == loginUsername || loginUsername == 'admin'}">
					<div class="col-1 cmt-item-del">
						<span>삭제&times;</span>
					</div>
				</c:if>
				<!-- <div class="col-1 m-1 subject"></div> -->
			</div>
			<div class="col-11 pt-3 pr-3 pb-3 cmt">
				<p style="font-size: 20px">${COMMENT.c_subject}
			</div>
		</div>
	</div>
	<div class="container">
		<a href="javascript:void(0)" data-toggle="popover"></a>
	</div>
</c:forEach>
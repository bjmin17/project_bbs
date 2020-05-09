<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>

<div class="row p-4 cmt-item">
	<div class="col-2 m-1 writer"><b>작성자</b></div>
	<div class="col-6 m-1 subject">내용</div>
	<div class="col-2 cmt-item-repl"><b>날짜</b></div>
</div>
<c:forEach var="COMMENT" items="${COMMENT}">
	<div class="row p-4 cmt-item" data-id="${COMMENT.c_id}">
		<div class="col-2 m-1 writer"><b>${COMMENT.c_writer}</b></div>
		<div class="col-6 m-1 subject">${COMMENT.c_subject}</div>
		<div class="col-2 cmt-item-repl"><b>${COMMENT.c_date_time}</b></div>
		<c:if test="${COMMENT.c_writer == loginUsername || loginUsername == 'admin'}">
			<div class="col-1 cmt-item-del"><b>&times;</b></div>
		</c:if>
		
	</div>
	<div class="container">
		<a href="javascript:void(0)" data-toggle="popover"></a>
	</div>
</c:forEach>
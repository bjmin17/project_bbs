<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>

<c:forEach var="COMMENT" items="${COMMENT}">
	<div class="row p-4 cmt-item" data-id="${COMMENT.c_id}">
		<div class="col-2 m-1 writer"><b>${COMMENT.c_writer}</b></div>
		<div class="col-7 m-1 subject">${COMMENT.c_subject}</div>
		<div class="col-1 cmt-item-repl"><b>답변</b></div>
		<div class="col-1 cmt-item-del"><b>&times;</b></div>
		
	</div>
	<div class="container">
		<a href="javascript:void(0)" data-toggle="popover"></a>
	</div>
</c:forEach>
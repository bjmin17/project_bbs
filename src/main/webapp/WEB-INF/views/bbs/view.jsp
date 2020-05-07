<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
<script>
$(function(){
	$(".btn-save").click(function(){
		alert("수정")
		document.location.href="${rootPath}/board/update?b_id=${bbsVO.b_id}"
	})
	
	$(".btn-delete").click(function(){
		
		// alert("삭제")
		if(confirm("삭제 하시겠습니까?")){
			document.location.replace("${rootPath}/board/delete?b_id=${bbsVO.b_id}")
		}
	})
})
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
<p>${bbsVO.b_subject}</p>

<span>${bbsVO.b_date}</span>
<span>${bbsVO.b_time}</span>
<span>추천 0 </span>
<span>조회 ${bbsVO.b_count}</span>
<hr/>
<p>${bbsVO.b_text}</p>

<button class="btn btn-warning btn-save">수정</button>
<button class="btn btn-danger btn-delete">삭제</button>
<a href="${rootPath}/board"><button type="button" class="btn btn-success">목록으로</button></a>
</body>
</html>
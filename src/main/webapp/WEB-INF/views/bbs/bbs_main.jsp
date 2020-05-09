<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/views/include/include-head.jspf" %>
<script>
$(function(){
	$(".bbs_view").click(function(){
		let id = $(this).attr("data-id")
		// alert(id)
		document.location.href = "${rootPath}/board/detail?b_id=" + id
	})
})

</script>
<style>
.article_table {
	height: 560px;
}

.bbs_view {
	cursor: pointer;
}

.pagination {
	display: flex;
	justify-content: center;
}
</style>
</head>
<body>
<%@ include file = "/WEB-INF/views/include/include-nav.jspf" %>
	<h2>bbs 페이지</h2>
	<hr/>
	<section>
		<article class="article_table">
			<table class="table table-hover">
				<c:choose>
					<c:when test="${not empty bbsList}">
						<tr>
							<th>게시글이 없습니다.</th>
						</tr>
					</c:when>
					<c:otherwise>
						<thead>
							<tr>
								<th>No</th>
								<th>제목</th>
								<th>날짜</th>
								<th>저자</th>
								<th>첨부파일</th>
								<th>조회수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${BBS_LIST}" var="BBS" varStatus="i">
							<tr class="bbs_view" data-id="${BBS.b_id}">
								<td>${i.count}</td>
								<td>${BBS.b_subject}</td>
								<td>${BBS.b_date}</td>
								<td>${BBS.b_writer}</td>
								<td>첨부파일</td>
								<td>${BBS.b_count}</td>
							</tr>		
						</c:forEach>
						</tbody>
					</c:otherwise>
				</c:choose>
			</table>
		</article>
		<article>
			<div align="right">
				<a href="${rootPath}/board/insert"><button class="btn btn-primary mb-3 "><i class="fas fa-pen"></i>등록</button></a>
			</div>
		</article>
		<div class="pagination">
			<%@ include file="/WEB-INF/views/include/include-pagination.jspf" %>
		</div>
	</section>
</body>
</html>
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
	
	//$("#search_button").click(function(){
	$(document).on("click","#search_button",function(){
		
		let search = $("#search_input")
		alert(search)
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
		<div class="input-group mt-3 mb-3">
		  <div class="input-group-prepend">
		    <button type="button" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
		      Dropdown button
		    </button>
		    <div class="dropdown-menu">
		      <a class="dropdown-item" href="#">Link 1</a>
		      <a class="dropdown-item" href="#">Link 2</a>
		      <a class="dropdown-item" href="#">Link 3</a>
		    </div>
		  </div>
		  <input type="text" class="form-control" placeholder="Username">
		</div>
		<form >
		  	<select name="kategorie" class="news_button">
			    <option value="allList" selected="selected">전체</option>
			    <option value="title">제목</option>
			    <option value="content">내용</option>
			</select>
			<input name="search" id="search_input">
			<button class="search_button" id="search_button">검색</button>
		</form>
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
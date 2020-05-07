<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/views/include/include-head.jspf" %>
</head>
<body>
<%@ include file = "/WEB-INF/views/include/include-nav.jspf" %>
	<h2>bbs 페이지</h2>
	<hr/>
	<section>
		<article>
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
							<tr>
								<td>1</td>
								<td>1</td>
								<td>1</td>
								<td>1</td>
								<td>1</td>
								<td>1</td>
							</tr>
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
			<button class="btn btn-primary">등록</button>
		</article>
	</section>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<div class="container">
	<h1>Board List</h1>
	<p>
	<a href="boardWrite.do">글쓰기</a>|<a href="boardList.do">글목록</a>
	<h3>총 게시글 수 : ${totalCount}개</h3>
	</p>
</div>

<jsp:include page="/foot.jsp"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<div class="container">
	<h1>Board 글보기</h1>
	<br>
	<a href="boardWrite.do">글쓰기</a>|
	<a href="boardList.do">글목록</a>
	<br><br>
	<table border="1" style="width:90%;margin:auto">
		<tr>
			<td width="20%"><b>글번호</b></td>
			<td width="30%"><b>${board.getNum()}</b></td> <!-- 원래는 이건 -->
			<td width="20%"><b>작성일</b></td>
			<td width="30%">
				<b>
					<fmt:formatDate value="${board.wdate}" pattern="yyyy/MM/dd HH:mm"/>
				</b>
			</td> <!-- get빼고 프로퍼티만 써도된다. -->
		</tr>
		<tr>
			<td width="20%"><b>글쓴이</b></td>
			<td width="30%"><b>${board.userid}</b></td>
			<td width="20%"><b>첨부파일</b></td>
			<td width="30%"><b>
			<a href="Upload/${board.filename}"download><img src="images/attach.jpg" width="15px">${board.filename}</a><br>
			[ ${board.filesize} bytes ]
			</b></td>
		</tr>
		<tr>
			<td width="20%"><b>제목</b></td>
			<td colspan="3">
				${board.subject}
			</td>
		</tr>
		<tr>
			<td width="20%"><b>글내용</b></td>
			<td colspan="3" style="height:300px">
				${board.content}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<a href="boardList.do">글목록</a>|
				<a href="#" onclick="goEdit()">수정</a>| <!-- 자바스크립트 함수 부르려면 이렇게해도되고 -->
				<a href="javascript:goDel()">삭제</a> <!-- 이렇게해도 된다 -->
			</td>
		</tr>
	</table>
</div>
<!-- 수정 또는 삭제를 위한 form----------------------------------- -->
<form name="bf" id="bf">
	<input type="hidden" name="num" value="${board.num}">
</form>
<!-- -------------------------------------------------------- -->
<script>
	function goEdit(){
		bf.action="boardEditForm.do";
		bf.method='post';
		bf.submit();
	}
	function goDel(){
		bf.action="boardDel.do"
		bf.method='post';
		bf.submit();
	}
</script>
<jsp:include page="/foot.jsp"/>
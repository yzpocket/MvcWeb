<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 아래 태그 라이브러리 쓰는법(java인데 html태그처럼 쓸수 있게)
https://tomcat.apache.org/download-taglibs.cgi 아래 jar파일을 받아
WEB-INF/lib에 넣어둔다.
이후 아래 페이지 태그참조 작성, 아래 리스트에서 태그 사용했음.
 -->
 <!-- core태그들 사용반복문 등 들어있음 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 포맷관련 태그.. -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<style>
	#boardWrap{
		width:94%;
		margin:auto;
		padding-top:1em;
	}
	#boardList>li{
		list-style-type: none;
		float: left;
		height: 40px;
		line-height: 40px;
		border-bottom: 1px solid #ddd;
		width: 15%;
	}
	#boardList>li:nth-child(4n+2){
		width: 55%;
		/* whitespace,overflow로 제목 넘치는것 숨기고 말줌임표...로 하는 속성*/
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	#boardList a:link, #boardList a:visited, #boardList a:hover{
		text-decoration: none;
	}
</style>
<div class="container">
	<h1>Board List</h1>
	<br>
	<p>
	<a href="boardWrite.do">글쓰기</a>|<a href="boardList.do">글목록</a>
	<%-- <h3>총 게시글 수 : ${totalCount}개</h3> --%>
<%-- 	${boardArr} --%>
	</p>
	<br>
		<div id="boardWrap">
		<ul id="boardList" class="boardList">
			<li>번호</li>
			<li>제목</li>
			<li>글쓴이</li>
			<li>등록일</li>
			<!-- ----------------------------------- -->
			<c:forEach var="vo" items="${boardArr}">
			<!-- "태그라이브러리"의 forEach 태그(반복문)의 속성들 
				var : 변수명
				items : 자료구조 (ArrayList, Map... 등)
				begin : 시작값
				end : 끝값
				varStatus : 반복문의 상태정보를 담아줄 변수명을 지정
						-count : 반복문횟수 1~
						-index : 인덱스 번호 0~
				-->
			<li>${vo.num}</li>
			<li><a href="boardView.do?num=${vo.num}">${vo.subject}</a></li> 
			<li>
			${vo.userid}
			</li>
			<li>
				<fmt:formatDate value="${vo.wdate}" pattern="yyyy/MM/dd"/>
			</li>
			</c:forEach>
			<!-- ----------------------------------- -->
		</ul>
	</div>
</div>

<jsp:include page="/foot.jsp"/>
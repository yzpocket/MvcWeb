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
	.pageWrap{
		position:relative;
	}
	.paging{
		list-style-type:none;
		position: absolute;
	    top: 50%;
	    left: 50%;
	    transform: translate(-50%, -50%);
	}
	.paging>li{
		float:left;
		padding:5px;
		text-align: center;
		width:2em;
		border: 1px solid #ddd;
		border-radius: 3px;
		margin: 2px;
	}
	.paging>li.current{
	 background-color: navy;
	}
	.paging>li.current a{
	 color:white;
	}
</style>
<!-- [검색 0-1] 검색관련 텍스트 입력창에 대한 유효성 체크 기본 find_check() -->
<script>
	function find_check(){
		let $keyword=$('#findKeyword');
		if(!$keyword.val()){ //인풋의 입력값 밸류가 없다면, val() jquery함수다.
			alert('검색어를 입력하세요');
			$keyword.focus();
			return false;
		}
		return true;
	}
</script>

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
		
		<!-- ------------------ 검색을 위한 세팅 ------------------- -->
		<!-- [검색 0] jsp에 프론트 작업 - 검색 관련 선택 및 텍스트 입력 창을 생성해 준다. -->
		<div id="boardSearch">
		<!-- 검색은 보통 get메서드를 사용한다. -->
			<form name="searchF" id="searchF" action="boardList.do" method="get" onsubmit="return find_check()"> 
				<select name="findType" style="padding:5px">
					<option value="1">제 목</option>
					<option value="2">작성자</option>
					<option value="3">글내용</option>
				</select>
				<input type="text" name="findKeyword" id="findKeyword" class="m2">
				<button style="padding:5px">Search</button>
			</form>
		</div>
		<!-- --------------------------------------------------- -->
		
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
			<li>
				<a href="boardView.do?num=${vo.num}">${vo.subject}</a>
				<c:if test="${vo.filesize > 0}">
					<img src="images/attach.jpg" width="16px">
				</c:if>
			
			
			</li> 
			<li>
			${vo.userid}
			</li>
			<li>
				<fmt:formatDate value="${vo.wdate}" pattern="yyyy/MM/dd"/>
			</li>
			</c:forEach>
			<!-- ----------------------------------- -->
		</ul>
		<!-- 페이징 처리 부분 [4], [5] 완료 한 뒤 다시 BoardListAction.java 액션컨트롤러로 이동------------------- -->
		<div style="clear:both"></div>
		<br><br>
		<div class="pageWrap">
			<ul class="paging">             <!-- [검색 7-2]검색으로 ${qStr} 각 부분에 추가. -->
				<li><a href="boardList.do?cpage=${cpage-1}${qStr}">P</a></li>
				
				<c:forEach var="i" begin="1" end="${pageCount}">
					<c:if test="${cpage==i}">
						<li class="current"><a href="boardList.do?cpage=${i}${qStr}">${i}</a></li>
					</c:if>
					<c:if test="${cpage!=i}">
						<li><a href="boardList.do?cpage=${i}${qStr}">${i}</a></li>
					</c:if>
				</c:forEach>
				
				<li><a href="boardList.do?cpage=${cpage+1}${qStr}">N</a></li>
			</ul>
		</div>
		<br>
		<br>
		<div>
			총 게시글 수 : ${totalCount}개, 현재 <span style="color:red">${cpage}</span> / 총 ${pageCount} pages
		</div>
		
	</div>
</div>

<jsp:include page="/foot.jsp"/>
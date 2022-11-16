<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<div class="container">
	<h1>File Upload Test</h1>
	<!-- enctype 디폴트는 application/x-www-form-urlencoded 파일명만 간
	multipart/form-data 파일 내용까지 가려면 이것 사용해야 한다.-->
	<form name="f" method="POST" enctype="multipart/form-data" action="uploadEnd.do">
		올린이:<input type="text" name="name"><br><br>
		첨부파일:<input type="file" name="fname"><br><br>
		<button>Upload</button>
		
	</form>
</div>

<jsp:include page="/foot.jsp"/>
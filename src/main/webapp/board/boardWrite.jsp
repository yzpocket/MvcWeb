<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<style>
	ul li{
		list-style-type: none;
	}

	#boardFrm li{
		padding: 10px 5px;
		text-align: left;
	}
	#subject, #content, #filename{
		width: 98%;
	}
	#boardFrm input{
		padding: 5px;
	}
	div.bbs{
		width: 90%;
		margin: auto;
	}
	.btn{
		padding: 4px;
		background-color: #efefef;
		border: 1px solid silver;
		width: 200px;
	}
</style>
<!-- jQuery CDN 임포트 // 유효성 체크용 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<!-- CKEditor 4 CDN 임포트 // 글작성 및 편집 에디터 양식 레이아웃!!-->
<script src="https://cdn.ckeditor.com/4.20.0/standard/ckeditor.js"></script>

<script>
	/* CKEditor 쓰는 방법	임포트 후- textarea 를 CKEDITOR로 대체*/
	$(function(){
		CKEDITOR.replace('content');
	})
	
	let board_check=function(){
		if($('#subject').val()==''){ //value값을 val()로 가져옴
			alert('제목을 입력하세요');
			$('#subject').focus();
			return false;
		}
		if(!CKEDITOR.instances.content.getData()){
			alert('글 내용을 입력하세요');
			CKEDITOR.instances.content.focus();
			return false;
		}
		return true;
	}
</script>

<div class="container">
	<h1>Board 글쓰기</h1>
	<div class="bbs">
	<form name="boardF" id="boardFrm" action="boardWriteEnd.do" method="POST" onsubmit="return board_check()">
		<!-- 수정 폼에는 기준이 될 글번호가 히든으로 넘어와야함. -->
		<input type="hidden" name="num" value="">
		<ul>
			<li>제목</li>
			<li>
				<input type="text" name="subject" value="" id="subject" placeholder="Subject">
			</li>
			<li>글내용</li>
			<li>
				<textarea name="content" id="content" rows="10" cols="50" placeholder="Content"></textarea>
			</li>
			<li>첨부파일</li>
			<li>
				<input type="file" name="filename" id="filename">
			</li>
			<li>
				<button class='btn'>글 쓰기</button>
				<button type="reset" class='btn'>다시 쓰기</button>
			</li>
		</ul>
	</form>
	</div>
</div>

<jsp:include page="/foot.jsp"/>
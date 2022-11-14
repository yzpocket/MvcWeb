<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/top.jsp"/>
<div class="container">
	<h1>Index</h1>
	<p>
	<%=request.getAttribute("msg") %> 
	<!-- 가능하면 이런 코드를 안쓰려고함 : jsp는 엄격하고 프론트엔드가 잘 모를 수 있어서 -->
	<!-- 그래서 ${msg} 처럼 키값을 쉽 전달할 수 있다. -->
	<div style='color:tomato;font-size:2em'>
	el 표현식을 이용해서 출력 할 수 있다.<br>
	${msg}	
	</div>
	</p>
</div>

<jsp:include page="/foot.jsp"/>
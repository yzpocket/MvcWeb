<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String myctx=request.getContextPath();
%>
<style>
	.mydiv{
		width:90%;
		margin:auto;
		padding:1em;
		height:130px;
		background-color:#efefef;
		display:table;
	}
	.mycell{
		text-align: center;
		width:100%;
		height:100%;
		display:table-cell;
		vertical-align: middle;
	}
</style>
<!-- foot.jsp -->
        </article>
        <!-- Side bar --------------------------------->
        <aside>
            <!-- Side bar -->
            <nav>		<!-- request에 저장한 값은 => requestScope.key
            				 session에 저장한 값은 => sessionScope.key로 불러온다.
            				 하지만 ${key}로 불러오면 req이후 ses순서로 찾는다. -->
            	<c:if test="${sessionScope.loginUser!=null}">
            	<div class="mydiv">
            		<div class="mycell">
	            		<h3>${loginUser.name}[${loginUser.userid}]</h3> 
	            		<h3>로그인 중...</h3>
	            		<br>
	            		<h4><a href="${pageContext.request.contextPath}/logout.do">로그아웃</a></h4>
            		</div>
            	</div>
            	</c:if>
                <ul>
                    <li><a href="<%=myctx%>/example/ex06.jsp">성적계산</a></li>
                    <li><a href="<%=myctx%>/login/sessionTest.jsp">Session</a></li>
                    <li><a href="<%=myctx%>/login/cookieTest.jsp">Cookie</a></li>
                    <li><a href="<%=myctx%>/login/myPage.jsp">MyPage</a></li>
                </ul>
            </nav>
        </aside>
        <div class="clear"></div> <!-- div float clear blank-->
        <!-- Footer ----------------------------------->
        <footer>
            <!-- Footer -->
            &copy;Copyright/회사소개
        </footer>
    </div>
    <!-- div#wrap .container end -->
</body>
</html>
<!-- layout.html -->
<!-- style을 layout.css 외부파일로 작성해서 참조하도록 하세요 -->
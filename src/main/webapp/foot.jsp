<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String myctx=request.getContextPath();
%>
<!-- foot.jsp -->
        </article>
        <!-- Side bar --------------------------------->
        <aside>
            <!-- Side bar -->
            <nav>
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
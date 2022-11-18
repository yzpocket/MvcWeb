<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String myctx=request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webpage</title>
    <link rel="stylesheet" type="text/css" href="<%=myctx%>/css/layout.css"> <!-- absolute direction would be better -->
    <!-- CDN jquery------------------------------------------------- -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
                <!-- Standard Webpage Form -->
<body> 
    <div id="wrap" class="container">
        
        <!-- Header: Logo Image, Searching, etc ------>
        <header> <!-- Head Section -->
            <a href="<%=myctx%>/index.do"><img src="<%=myctx%>/images/main_logo.png" alt="main_logo"></a>
        </header>
        <div class="clear"></div> <!-- div float clear blank-->
        <!-- Navigation bar : Menu--------------------->
        <nav> <!-- Nav section -->
            <ul>
                <li><a href="<%=myctx%>/index.do">Home</a></li>
                <li><a href="<%=myctx%>/user/boardWrite.do">Board 쓰기</a></li>
                <li><a href="<%=myctx%>/boardList.do">Board 목록</a></li>
                <li><a href="<%=myctx%>/joinForm.do">회원가입</a></li>
                <!-- el 표현식 연산자 : eq (==)와동일함, ne(!=)와 동일함 -->
                <c:choose>
                	<c:when test="${loginUser==null}">
	                	<li><a href="<%=myctx%>/login.do">로그인</a></li>
	                </c:when>
	                <c:otherwise>
	                	<li><a href="<%=myctx%>/logout.do">로그아웃</a></li>
	                </c:otherwise>
				</c:choose>
                
                <li><a href="<%=myctx%>/user/myPage.do">MyPage</a></li>
				
            </ul>
        </nav>
        <div class="clear"></div> <!-- div float clear blank-->
        <!-- Content----------------------------------->
        <article> <!-- Content section -->
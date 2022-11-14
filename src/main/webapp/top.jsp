<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
                <li><a href="<%=myctx%>/boardWrite.do">Board 쓰기</a></li>
                <li><a href="<%=myctx%>/boardList.do">Board 목록</a></li>

            </ul>
        </nav>
        <div class="clear"></div> <!-- div float clear blank-->
        <!-- Content----------------------------------->
        <article> <!-- Content section -->
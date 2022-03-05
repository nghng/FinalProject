<%-- 
    Document   : display
    Created on : Mar 5, 2022, 2:29:35 PM
    Author     : admin
--%>



<%@page import="model.Kindergarten"%>
<%@page import="model.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
      
    </head>
    <header class="header-basic">

        <div class="header-limiter">

            <h1><a href="#">Cổng Thông Tin<span></span></a></h1>

            <nav>
                <a href="#">Trang chủ</a>
                <a href="#">Liên lạc</a>
                <a href="logout" >Đăng xuất</a>

            </nav>
        </div>

    </header>



    <body>


        

        <c:forEach items="${requestScope.kindergartens}" var="k">
            <div>
                <a>Trường mầm non ${k.kname}</a>
                <p>Mã Trường: ${k.kid} </p>

            </div>
        </c:forEach>




        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>

    </body>
</html>

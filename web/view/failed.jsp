<%-- 
    Document   : home
    Created on : Mar 2, 2022, 10:35:39 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lỗi</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="${pageContext.request.contextPath}/css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    </head>
    <header class="header-basic">

        <div class="header-limiter">

            <h1><a href="#">Cổng Thông Tin<span></span></a></h1>

            <nav>
                <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                <a href="mailto: hunglengoc2109@gmail.com">Liên lạc</a>
                <a href="${pageContext.request.contextPath}/logout" >Đăng xuất</a>
            </nav>
        </div>

    </header>



    <body>
        
        <p>Bạn chưa đăng nhập hoặc không có thẩm quyền để truy cập vào trang này</p>
        <a href="${pageContext.request.contextPath}/login">Nhấp vào tôi để đăng nhập hoặc quay về trang chủ</a>
        
        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>

    </body>
</html>

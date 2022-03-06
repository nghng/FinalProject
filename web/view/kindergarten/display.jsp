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
        <link href="css/display/display.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="js/kindergarten/kindergarten.js" type="text/javascript"></script>
     
        <%
            Integer pageindex = (Integer) request.getAttribute("pageindex");
            Integer totalpage = (Integer) request.getAttribute("totalpage");
        %>
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

        <c:choose>
            <c:when test="${requestScope.kindergartens.size() gt 0}">
                <c:forEach items="${requestScope.kindergartens}" var="k">
                    <div>
                        <a href="kindergarten/detail?kid=${k.kid}">Trường mầm non ${k.kname}</a>
                        <p>Mã Trường: ${k.kid} </p>

                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div>Không tồn tại bản ghi này</div>
                <a href="home">Quay về trang tìm kiếm</a>
            </c:otherwise>
        </c:choose>
        <div id="containerbot" class="pagger">  </div>
        <c:if test="${requestScope.pageindex != null && requestScope.totalpage != null }">  

            <script>
                pagger("containerbot",<%=pageindex%>,<%=totalpage%>, 3);
            </script>
        </c:if>







        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>

    </body>
</html>

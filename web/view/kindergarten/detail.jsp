<%-- 
    Document   : detail
    Created on : Mar 6, 2022, 6:12:28 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trường mầm non ${requestScope.kindergarten.kname}</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="../css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="../css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    </head>




    <body>
        <header class="header-basic">

            <div class="header-limiter">

                <h1><a href="#">Cổng Thông Tin<span></span></a></h1>

                <nav>
                    <c:choose>
                        <c:when test="${sessionScope.employee.role.rid != 0}">
                            <a href="${pageContext.request.contextPath}/kindergarten/detail?kid=${sessionScope.employee.kinder.kid}">Trang chủ</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                        </c:otherwise>
                    </c:choose>
                    <a href="mailto: hunglengoc2109@gmail.com">Liên lạc</a>
                    <a href="../logout" >Đăng xuất</a>

                </nav>
            </div>

        </header>

        <c:set var="e" scope="session" value="${sessionScope.employee}"/>
        <div>
            <p><c:choose>
                    <c:when test="${e.role.rid != 0 and e.gender}">Xin chào thầy ${e.fullname}</c:when>
                    <c:when test="${e.role.rid != 0 and e.gender == false}">Xin chào cô ${e.fullname}</c:when>
                    <c:otherwise>Xin chào cán bộ ${e.fullname}</c:otherwise>

                </c:choose>
            </p>
        </div>

        <div id="generalLink">
            <p>Trường mầm non ${requestScope.kindergarten.kname}</p>
            <a href="detail/general/doc?kid=${requestScope.kindergarten.kid}">Tra cứu hồ sơ của trường</a></br>
            <a href="detail/employee">Tra cứu nhân viên của trường</a></br>
            <a href="detail/create">Tạo mới hồ sơ</a></br>
            <a href="detail/personal/doc?eid=${sessionScope.employee.eid}">Xem tất cả hồ sơ của bạn</a></br>

        </div>

        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>

    </body>
</html>


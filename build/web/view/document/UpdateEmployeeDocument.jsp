<%-- 
    Document   : displayEmployee
    Created on : Mar 8, 2022, 1:03:24 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chỉnh sửa hồ sơ cá nhân</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="../../../../css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="../../../../css/home/home.css" rel="stylesheet" type="text/css"/>
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
                    <a href="${pageContext.request.contextPath}/logout" >Đăng xuất</a>

                </nav>
            </div>

        </header>
                    <form method="POST"  enctype="multipart/form-data" >
            <table>
                <tr>
                <tr><td>Pdf file: </td>
                    <td><input type="file" name="file" id="file" /> </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Upload" name="upload" id="upload" /> </td>
                </tr>
            </table>
            <c:if test="${requestScope.error != null}">
                <p>${requestScope.error}</p>
            </c:if>
            <c:if test="${requestScope.success}">
                <p>Cập nhật file thành công</p>
                <a href="view?eid=${param["eid"]}&datetime=${param["datetime"]}&did=${param["did"]}">Nhấn vào đây để xem tài liệu<a/>
                </c:if>

        </form>





        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>
        <script src="../../../js/document/displayEmployeeDocument.js" type="text/javascript"></script>

    </body>
</html>

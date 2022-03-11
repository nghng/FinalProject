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
        <title>Hồ sơ cá nhân</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="../../../css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="../../../css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    </head>


    <body>

        <header class="header-basic">
            <div class="header-limiter">

                <h1><a href="../../../home">Cổng Thông Tin<span></span></a></h1>

                <nav>
                    <a href="../../home">Trang chủ</a>
                    <a href="#">Liên lạc</a>
                    <a href="../../logout" >Đăng xuất</a>

                </nav>
            </div>

        </header>



        <form/>
        <c:choose>
            <c:when test="${requestScope.edocs.size() > 0}">
                <table border="1px">
                    <tr>
                        <td>Mã tài liệu</td>
                        <td>Loại tài liệu</td>
                        <td>Ngày tải lên</td>
                        <td>Nội dung</td>
                        <td>Chỉnh sửa nội dung</td>
                    </tr>
                    <c:forEach items="${requestScope.edocs}" var="e"> 
                        <tr>
                            <td>${e.eid}</td>
                            <td>${e.doc.dname}</td>
                            <td>${e.datetime}</td>
                            <td><a href="doc/view?eid=${param["eid"]}&datetime=${e.datetime}&did=${e.doc.did}" >Xem</a></td>
                            <td><a href="#" onclick="deleteStudent('doc/del?eid=${param["eid"]}&datetime=${e.datetime}&did=${e.doc.did}')" >Xóa</a>   
                                <a href="doc/update?eid=${param["eid"]}&datetime=${e.datetime}&did=${e.doc.did}" >Sửa</a>

                            </td>

                        </tr>
                    </c:forEach>
                </table> 
            </c:when>
            <c:otherwise>
                <p>Nhân viên này chưa có hồ sơ nào cả</p>
            </c:otherwise>
        </c:choose> 



        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>
        <script src="../../../js/document/displayEmployeeDocument.js" type="text/javascript"></script>

    </body>
</html>

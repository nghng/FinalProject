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
        <title>JSP Page</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="../../css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="../../css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="../../js/employee/displayEmployee_update.js" type="text/javascript"></script>
       
    </head>


    <body>

        <header class="header-basic">
            <div class="header-limiter">

                <h1><a href="../../home">Cổng Thông Tin<span></span></a></h1>

                <nav>
                    <a href="../../home">Trang chủ</a>
                    <a href="#">Liên lạc</a>
                    <a href="../../logout" >Đăng xuất</a>

                </nav>
            </div>

        </header>


        <form action="employee" method="GET" id="frmSearch">
            <label for="roleselect">Chọn 1 chức vụ</label>

            <select name="rid" onchange="submitForm();" id="roleselect">
                <option value="-1">----Tất cả----</option>
                <c:forEach items="${requestScope.roles}" var="r">
                    <c:choose>
                        <c:when test="${r.rid == requestScope.rid}">
                            <option
                                selected="selected"
                                value="${r.rid}">${r.rname}</option>
                        </c:when>
                        <c:otherwise>
                            <option

                                value="${r.rid}">${r.rname}</option>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>


            </select>
            <br/>
            <label for="info">Tên nhân viên hoặc mã nhân viên cần tìm</label>
            <input type="text" name="info" id="info"> <br/>
            <input type="submit" value="Tìm">

            <form/>
            <table border="1px">
                <tr>
                    <td>Mã nhân viên</td>
                    <td>Họ và tên</td>
                    <td>Ngày tháng năm sinh</td>
                    <td>Chức vụ</td>
                    <td>Giới tính</td>
                    <td>Trường mầm non</td>
                    <td>Hồ sơ cá nhân</td>

                </tr>
                <c:forEach items="${requestScope.employees}" var="e"> 
                    <tr>
                        <td>${e.eid}</td>
                        <td>${e.fullname}</td>
                        <td>${e.dob}</td>
                        <td>${e.role.rname}</td>

                        <td><c:choose>
                                <c:when test="${e.gender}">
                                    Nam
                                </c:when>
                                <c:otherwise>
                                    Nữ
                                </c:otherwise>
                            </c:choose> </td>
                        <td>${e.kinder.kname}</td>
                        <td><a href="employee/doc?eid=${e.eid}">Tra cứu</a></td>
                    </tr>
                </c:forEach>



            </table> 
            <div id="containerbot" class="pagger">  </div>
            <script>

                pagger(${requestScope.rid}, "containerbot",${requestScope.pageindex},${requestScope.totalpage}, 3);
            </script>


            <div class="footer" style="width: 100%;">
                <p>Liên lạc: hunglengoc2109@gmail.com</p>
            </div>

    </body>
</html>

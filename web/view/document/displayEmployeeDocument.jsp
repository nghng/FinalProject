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
        <link href="${pageContext.request.contextPath}/css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    </head>


    <body>

        <header class="header-basic">
            <div class="header-limiter">

                <h1><a href="../../../home">Cổng Thông Tin<span></span></a></h1>

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



        <form/>
        <c:choose>
            <c:when test="${requestScope.edocs.size() > 0}">
                <table border="1px">
                    <tr>
                        <td>Mã tài liệu</td>
                        <td>Loại tài liệu</td>
                        <td>Ngày tải lên</td>
                        <td>Ngày thay đổi</td>
                        <td>Người thay đổi (mã nhân viên)</td>
                        <td>Nội dung</td>
                        <td>Chỉnh sửa nội dung</td>
                    </tr>
                    <c:forEach items="${requestScope.edocs}" var="e"> 
                        <tr>
                            <td>${e.doc.did}</td>
                            <td>${e.doc.dname}</td>
                            <td>${e.datetime}</td>
                            <c:choose>
                                <c:when test="${e.modifiedDate != null}"><td>${e.modifiedDate}</td> </c:when>
                                <c:otherwise><td>Chưa thay đổi lần nào</td></c:otherwise>
                            </c:choose>
                                <c:choose>
                                    <c:when test="${e.meid == 0}"><td>Chưa có ai thay đổi</td></c:when>
                                    <c:otherwise><td><a href="${pageContext.request.contextPath}/kindergarten/detail/employee?rid=-1&info=${e.meid}">${e.meid}</a></td></c:otherwise>
                                </c:choose>
                                
                                
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
        <script src="${pageContext.request.contextPath}/js/document/displayEmployeeDocument.js" type="text/javascript"></script>

    </body>
</html>

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
        <title>Hồ sơ của trường</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="${pageContext.request.contextPath}/css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="${pageContext.request.contextPath}/js/document/displayKindergartenDocument.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>


    <body>

        <header class="header-basic">
            <div class="header-limiter">

                <h1><a href="">Cổng Thông Tin<span></span></a></h1>

                <nav>
                    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    <a href="mailto: hunglengoc2109@gmail.com">Liên lạc</a>
                    <a href="${pageContext.request.contextPath}/logout" >Đăng xuất</a>

                </nav>
            </div>

        </header>

                    <form method="GET" action="doc" id="form" name="form">
            <label for="rid">Văn bản thuộc cấp:</label>
            <select name="rid" id="rid">
                <option value="-1">Tất cả các cấp</option>
                <c:forEach items="${requestScope.roles}" var="r">
                    <option

                        value="${r.rid}">${r.rname}</option>
                </c:forEach>
            </select>

            <br/>
            <label for="from">Từ ngày: </label><br/>
            <input type="datetime-local" id="from" name="from"> <br/>
            <label for="to">Đến ngày: </label><br/>
            <input type="datetime-local" id="to" name="to"><br><br/>

            <input type="submit" value="Tra" id="submit" ">

        </form>


        <c:choose>
            <c:when test="${requestScope.edocs.size() > 0}">
                <table border="1px">
                    <tr>
                        <td>Mã tài liệu</td>
                        <td>Loại tài liệu</td>
                        <td>Ngày tải lên</td>
                        <td>Ngày thay đổi</td>
                        <td>Người thay đổi (mã NV)</td>
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


                            <td><a href="doc/view?eid=${e.eid}&datetime=${e.datetime}&did=${e.doc.did}" >Xem</a></td>
                            <td><a href="#" onclick="deleteStudent('doc/del?eid=${e.eid}&datetime=${e.datetime}&did=${e.doc.did}')" >Xóa</a>   
                                <a href="doc/update?eid=${e.eid}&datetime=${e.datetime}&did=${e.doc.did}" >Sửa</a>

                            </td>

                        </tr>
                    </c:forEach>
                </table> 
            </c:when>
            <c:otherwise>
                <p>Trường này chưa có tài liệu nào cả</p>
            </c:otherwise>
        </c:choose> 
        <div id="containerbot" class="pagger">  </div>
        <script>

            pagger("containerbot",${requestScope.pageindex},${requestScope.totalpage}, 3, "doc?rid=${requestScope.rid}&from=${requestScope.from}&to=${requestScope.to}");
        </script>


        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>
       
        </body>
        
        </html>

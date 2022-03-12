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
        <title>JSP Page</title>
        <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
        <link href="../../css/home/header-basic.css" rel="stylesheet" type="text/css"/>
        <link href="../../css/home/home.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    </head>




    <body>
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
        <div>
            <form method="POST" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td> Loại tài liệu<td/>
                        <td><select name="did" id="did"></select> <td/>
                    <tr/>

                    <tr>
                        <td>Pdf file: </td>
                        <td><input type="file" name="file" id="file" /> </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td><input type="submit" value="Khởi tạo"></td>
                    </tr>


                </table>




            </form>
        </div>



        <div class="footer" style="width: 100%;">
            <p>Liên lạc: hunglengoc2109@gmail.com</p>
        </div>

    </body>
</html>

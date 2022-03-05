<%-- 
    Document   : login
    Created on : Mar 2, 2022, 8:44:44 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<link href="css/login.css" rel="stylesheet" type="text/css"/>
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
<div class="logo"></div>
<div class="login-block">
    <form action="login" method="POST">
        <h1>Cổng đăng nhập</h1>
        <input type="text" value="" placeholder="Mã nhân viên" id="username" name="eid" />
        <input type="password" value="" placeholder="Mật khẩu" id="password" name="password" />
        <button type="Submit">Đăng nhập</button>
        <c:if test="${sessionScope.isLogin == 1 and sessionScope.employee == null}">
            <p>Mã nhân viên hoặc mật khẩu không chính xác.</p>

        </c:if>
    </form>

</div>



<!--<html>
https://codepen.io/petertoth/pen/qBPwBY reference link
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form action="login" method="POST">
            <table>
                <tr>
                    <td>Eid: </td>
                    <td><input type="text" name="eid" /></td>
                </tr>

                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="password" /></td>
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" value="Login" /></td>
                </tr>




            </table>




</form>
</body>
</html>-->

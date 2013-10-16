<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/signin.css"/>" rel="stylesheet">
</head>
<body>
    <div class="container">
        <form class="form-signin" action="<c:url value="/j_spring_security_check"/>" method="post">
            <h2 class="form-signin-heading">登录</h2>
            <input type="text" class="form-control" placeholder="用户名" autofocus name="j_username">
            <input type="password" class="form-control" placeholder="密码" name="j_password">
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>
    </div>
    <script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                ${message}
            </div>
        </c:if>
        <form action="/save" method="post" enctype="multipart/form-data">
            <legend>账号注册</legend>
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="userName" class="form-control">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="text" name="password" class="form-control">
            </div>
            <div class="form-group">
                <label>电子邮件</label>
                <input type="text" name="email" class="form-control">
            </div>
            <div class="form-group">
                <label>照片</label>
                <input type="file" name="photo" class="form-control">
            </div>
            <div class="form-group">
                <button class="btn btn-primary">注册</button>
            </div>
        </form>

    </div>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: zyuan
  Date: 2020/2/19
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>北京市博物馆预约系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>
<body>
<h1 class="text-center pt-3 pb-3">登录系统</h1>

<div class="container">
    <hr>
    <form action="/order/user_login.action" method="post">
        <input type="hidden" name="page" value="${page}">
        <input type="hidden" name="date" value="${date}">
        <input type="hidden" name="district" value="${district}">
        <input type="hidden" name="museum" value="${museum}">
        <div class="form-group row">
            <h4 class="offset-md-3 col-md-2 text-right">手机号：</h4>
            <div class="col-md-3">
                <input type="text" name="phone" class="form-control" placeholder="请输入11位手机号">
            </div>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">登录系统</button>
        </div>
    </form>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: zyuan
  Date: 2020/2/27
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script>
        function deleteAll() {
            $("#orderForm").submit();
        }
    </script>
</head>
<body>
<div class="text-right pt-3 pr-5">
    <div class="btn-group">
        <button type="button" onclick="location.href='/museum/all_museums.action'"
                class="btn btn-sm btn-outline-primary">我要预约</button>
        <c:if test="${phone == null}">
            <button type="button" onclick="location.href='/order/goto_login.action'"
                    class="btn btn-sm btn-outline-primary">
                登录系统
            </button>
        </c:if>
        <c:if test="${phone != null}">
            <button type="button" onclick="location.href='/order/goto_login.action'"
                    class="btn btn-sm btn-outline-primary">
                切换用户
            </button>
        </c:if>
    </div>
</div>

<h1 class="text-center pt-1 pb-2">北京市博物馆预约系统</h1>

<div class="container">
    <div class="offset-md-1 col-md-10">
        <form class="form-inline">
            <div class="form-group">
                <button type="button" onclick="deleteAll()" class="btn btn-sm btn-outline-primary">
                    批量删除
                </button>
            </div>
        </form>
        <hr>
        <form id="orderForm" action="del_orders.action" method="post">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th></th>
                        <th>订单编号</th>
                        <th>参观日期</th>
                        <th>博物馆</th>
                        <th>手机号码</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="o">
                        <tr>
                            <td class="text-right">
                                <input type="checkbox" class="form-check-input" name="orders" value="${o.orderId}">
                            </td>
                            <td>${o.orderId}</td>
                            <td>
                                <fmt:formatDate value="${o.visitDate}" pattern="yyyy年MM月dd日"/>
                            </td>
                            <td>${o.museum.museumName}</td>
                            <td>${o.phoneNum}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>

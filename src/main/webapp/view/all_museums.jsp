<%--
  Created by IntelliJ IDEA.
  User: zyuan
  Date: 2020/2/19
  Time: 9:51
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
    <script type="text/javascript">
        function addOrder(museumId) {
            $.ajax({
                type: 'post',
                url: '/order/add_order.action?museumId='+museumId+"&visitDate="+$("#dateSelect").val(),
                dataType: "json",
                success: function (data) {   //成功后回调
                    if (data.success == 0) {
                        location.href="/order/goto_login.action?page=${pageNum}&district=${districtStr}&date="+$("#dateSelect").val();
                    }
                    else if (data.success == 1){
                        alert("预约成功");
                        $("#remain"+data.museumId).html('余票:<span style="color:red">'+data.remainNum+'</span>');
                    }
                    else {
                        alert(data.message);
                    }
                },
                error: function (error) {    //失败后回调
                    alert("connect error.");
                }
            });
        }

        //js翻页：为了能动态获取日期下拉列表框的值
        function gotoNextPage(page) {
            //alert($("#dateSelect").val());
            location.href = "all_museums.action?page="+page+"&district=${districtStr}&date="+$("#dateSelect").val();
        }

        function gotoLogin() {
            location.href = "/order/goto_login.action?page=${pageNum}&district=${districtStr}&date="+$("#dateSelect").val();
        }

        $(function() {
            //日期下拉列表框绑定change事件
            $("#dateSelect").change(function () {
                $.ajax({
                    type: 'get',
                    url: '/order/all_remains.action?museumIds=${museumIds}&visitDate='+$("#dateSelect").val(),
                    dataType: "json",
                    success: function (data) {   //成功后回调
                        $.each(data, function(key, value){
                            if (value < 0) { //闭馆
                                $("#remain"+key).text("今日闭馆");
                            }
                            else {
                                $("#remain"+key).html('余票:<span style="color:red">'+value+'</span>');
                            }
                        });
                    },
                    error: function (error) {    //失败后回调
                        alert("connect error.");
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="text-right pt-3 pr-5">
    <c:if test="${phone == null}">
        <button type="button" onclick="gotoLogin()" class="btn btn-sm btn-outline-primary">
            登录系统
        </button>
    </c:if>
    <c:if test="${phone != null}">
        <button type="button" onclick="location.href='/order/get_orders.action'" class="btn btn-sm btn-outline-primary">
            我的预约
        </button>
    </c:if>
</div>

<h1 class="text-center pt-1 pb-3">北京市博物馆预约系统</h1>

<div class="container">
    <div class="row">
        <div class="col-md-9">
            <form class="form-inline" role="form" action="all_museums.action" method="post">
                <div class="form-group">
                    <label>所属区域：</label>

                    <c:forEach items="${districts}" var="d">
                        <c:set var="status" value=""/>
                        <c:forEach items="${districtIds}" var="i">
                            <c:if test="${d.districtId == i}">
                                <c:set var="status" value="checked"/>
                            </c:if>
                        </c:forEach>

                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" name="district" id="box${d.districtId}"
                                   value="${d.districtId}" ${status}>
                            <label class="custom-control-label" for="box${d.districtId}">${d.name}</label>
                        </div>
                        &nbsp;&nbsp;
                    </c:forEach>
                </div>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <div class="form-group">
                    <button type="submit" class="btn btn-sm btn-outline-primary">查询</button>
                </div>
            </form>
        </div>

        <div class="col-md-3">
            <form class="form-inline">
                <div class="form-group">
                    <label>预约日期：</label>
                    <select class="form-control form-control-sm" name="visitDate" id="dateSelect">
                        <c:forEach items="${visitList}" var="d">
                            <option value="${d}" ${d == visitDate? "selected":""}>${d}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>
        </div>
    </div>
    <hr>
    <ul class="list-unstyled">
        <c:forEach items="${museums}" var="m">
        <li class="media my-4">
            <img src="${m.image}" class="align-self-center mr-3" alt="...">
            <div class="media-body">
                <h3 class="mt-0 mb-2">
                    <a style="color:black" target="_blank" href="get_museum.action?museumId=${m.museumId}">${m.museumName}</a>
                </h3>
                <div class="row">
                    <div class="col-md-9">
                        ${m.museumInfo}
                    </div>
                    <div class="col-md-2 text-center">
                        <h4 id="remain${m.museumId}">
                            <c:if test="${m.dayOff == 0}">
                                余票:<span style="color:red">${m.capacity-m.orderNum}</span>
                            </c:if>
                            <c:if test="${m.dayOff != 0}">
                                今日闭馆
                            </c:if>
                        </h4>

                        <button type="button" onclick="addOrder(${m.museumId})" class="btn btn-outline-primary">
                            预约参观
                        </button>
                    </div>
                </div>
            </div>
        </li>
        </c:forEach>
    </ul>

    <nav class="pb-5 pt-3">
        <c:set var="params" value="district=${districtStr}&visitDate=${visitDate}"/>

        <ul class="pagination justify-content-center">
            <c:if test="${pageNum == 1}">
                <li class="page-item disabled">
                    <a class="page-link" href="javascript:void(0);">首页</a>
                </li>
            </c:if>
            <c:if test="${pageNum != 1}">
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0);" onclick="gotoNextPage(1)">首页</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${pageCount}" var="i">
                <c:if test="${pageNum == i}">
                    <li class="page-item active">
                        <a class="page-link" href="javascript:void(0);">${i}</a>
                    </li>
                </c:if>
                <c:if test="${pageNum != i}">
                    <li class="page-item">
                        <a class="page-link" href="javascript:void(0);" onclick="gotoNextPage(${i})">${i}</a>
                    </li>
                </c:if>
            </c:forEach>

            <c:if test="${pageNum == pageCount}">
                <li class="page-item disabled">
                    <a class="page-link" href="javascript:void(0);">末页</a>
                </li>
            </c:if>
            <c:if test="${pageNum != pageCount}">
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0);" onclick="gotoNextPage(${pageCount})">末页</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
</body>
</html>

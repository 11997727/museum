<%--
  Created by IntelliJ IDEA.
  User: zyuan
  Date: 2020/2/28
  Time: 8:38
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
    <script type="text/javascript" src="/js/timeUtils.js"></script>
    <script type="text/javascript">
        function gotoLogin() {
            window.open("/order/goto_login.action");
        }

        function addComment() {
            $.ajax({
                type: 'post',
                url: '/comment/add_comment.action?museumId=${museum.museumId}&content='+$("#commentText").val(),
                dataType: "json",
                success: function (data) {   //成功后回调
                    if (data.success == 0) {
                        alert("请登录后再评论");
                    }
                    else if (data.success == 1){
                        alert("评论成功");
                        getComments(1); //重新加载评论
                        $("#commentText").val("");
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

        //显示评论列表
        function showList(list) {
            var content = "";

            $.each(list, function (i, comment) {
                //将中间四位转为星号
                var phone = comment.phoneNum.substr(0,3)+"*"+comment.phoneNum.substr(7);

                content += ('<hr><div class="row"><div class="col-md-3"><p>用户：'+phone+'<br/>时间：'+
                    dateFormat(comment.commentDate)+'</p></div><div class="col-md-9">'+comment.content+
                    "</div></div>");
            });

            $("#commentsList").html(content);
        }

        //显示页码
        function showPage(page, total) {
            var pagnition = "";

            if (page == 1) {
                pagnition = '<li class="page-item disabled"><a class="page-link" href="javascript:void(0);">上一页</a></li>';
            }
            else {
                pagnition = '<li class="page-item"><a class="page-link" href="javascript:void(0);" onclick="getComments('+
                    (page-1)+')">上一页</a></li>';
            }

            for(i = 1; i <= total; i++) {
                if (i == page) {
                    pagnition += '<li class="page-item active"><a class="page-link" href="javascript:void(0);">'+i+'</a></li>';
                }
                else {
                    pagnition += '<li class="page-item"><a class="page-link" href="javascript:void(0);" onclick="getComments('+
                        i+')">'+i+'</a></li>';
                }
            }

            if (page == total) {
                pagnition += '<li class="page-item disabled"><a class="page-link" href="javascript:void(0);">下一页</a></li>';

            }
            else {
                pagnition += '<li class="page-item"><a class="page-link" href="javascript:void(0);" onclick="getComments('+
                total+')">下一页</a></li>';
            }

            $("#commentsPage").html(pagnition);
        }

        //请求评论列表
        function getComments(page) {
            $.ajax({
                type: 'post',
                url: '/comment/get_comments.action?museumId=${museum.museumId}&page='+page,
                dataType: "json",
                success: function (data) {   //成功后回调
                    showList(data.comments); //列表
                    showPage(data.pageNum, data.pageCount); //页码
                },
                error: function (error) {    //失败后回调
                    alert("connect error.");
                }
            });
        }

        $(function() {
            getComments(1);
        })
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

<div class="container" style="margin-top:30px">
    <hr>

    <div class="row">
        <div class="col-md-3">
            <img src="${museum.image}" class="align-self-center mr-3" alt="..."><br/>
            <p class="pt-2">
                开馆时间：09:00-16:00
            </p>
        </div>
        <div class="col-md-9">
            <h2 class="pb-2">${museum.museumName}</h2>
            ${museum.museumDesc}

            <h2 class="pb-2 pt-5">观众评论</h2>
            <div class="small" id="commentsList">
            </div>

            <div class="offset-md-4 col-md-4 pb-3 pt-2">
                <ul class="pagination pagination-sm" id="commentsPage">
                </ul>
            </div>

            <h2 class="pb-2 pt-2">发表评论</h2>

            <div class="row pb-5">
                <div class="col-md-9">
                    <textarea class="form-control" rows="5" id="commentText"></textarea>
                </div>
                <div class="col-md-3 text-center pt-5">
                    <button type="button" id="btn" onclick="addComment()" class="btn btn-outline-primary">提交评论</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

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
            <div class="alert alert-info">
                    ${message}
            </div>
        </c:if>
        <h3 class="page-header">
            ${kaola.productName}
                <a href="/product/${kaola.id}/edit" class="btn btn-info pull-right">编辑</a>
                <a href="javascript:;" id="delLink" rel="${kaola.id}" class="btn btn-danger pull-right">删除</a>
        </h3>
        <ul>
            <li>产地 ： ${kaola.place}</li>
            <li>市场价： ${kaola.marketPrice}</li>
            <li>考拉价: ${kaola.price}</li>
            <li>评论数量: ${kaola.commentNum}</li>
            <li>类型 ： ${kaola.kaolaType.typeName}</li>
        </ul>
        <a href="/product">返回列表</a>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script>
        $(function(){

            $("#delLink").click(function () {
                var id = $(this).attr("rel");
                if(confirm("确定要删除吗")) {
                    window.location.href = "/product/"+id+"/delete";
                }
            });


        });
    </script>
</body>
</html>
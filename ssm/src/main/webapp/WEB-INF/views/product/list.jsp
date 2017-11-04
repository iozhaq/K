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
        <a href="/product/new" class="btn btn-primary">添加</a>
        <table class="table">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>产地</th>
                    <th>市场价</th>
                    <th>考拉价</th>
                    <th>评论数量</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageInfo.list}" var="product">
                    <tr>
                        <td><a href="/product/${product.id}">${product.productName}</a></td>
                        <td>${product.place}</td>
                        <td>${product.marketPrice}</td>
                        <td>${product.price}</td>
                        <td>${product.commentNum}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <ul id="pagination-demo" class="pagination-sm"></ul>

    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.twbsPagination.js"></script>
    <script>
        $(function(){
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: ${pageInfo.pages},
                visiblePages: 10,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?p={{number}}"
            });
        });
    </script>
</body>
</html>
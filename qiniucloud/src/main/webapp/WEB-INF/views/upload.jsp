<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
</head>
<body>
    <h3>UploadFile</h3>
    <form action="http://upload-z1.qiniup.com" method="post" enctype="multipart/form-data">
        <input type="hidden" name="token" value="${uploadToken}">
        <input type="file" name="file">
        <input type="hidden" name="x:pid" value="1009" >
        <button>上传</button>
    </form>
</body>
</html>
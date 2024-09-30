<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SSSS</title>
</head>
<body>
<%--<%@ include file="/WEB-INF/views/sidebar.jsp" %>--%>
<div class="main-content" style="margin-left:200px; padding:20px;">

    <form action="/banhtrungthu/update" method="post">

        id:<input type="number" name="id"><br>

        <button type="submit">Cập nhật</button>
    </form>

</div>

</body>
</html>
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
<button><a href="/banhtrungthu/add">them moi</a></button>
<%--<a href="/kiemtra/add">them moi</a>--%>
<table>
    <thead>
    <tr>
        <th>ssss</th>
        <th>ssss</th>
        <th>ssss</th>
        <th>ssss</th>
        <th>ssss</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="o" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${o.id}</td>
            <td>${o.ten}</td>
            <td>${o.ssss}</td>
            <td>${o.ssss?"ngon":"khong"}</td>
            <td>
                <a href="/banhtrungthu/update/${o.id}">Sửa</a>
                <a href="/banhtrungthu/delete/${o.id}">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
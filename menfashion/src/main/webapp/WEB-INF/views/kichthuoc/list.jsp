<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <title>Danh sách kích thước</title>
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="main-content" style="margin-left:200px; padding:20px;">
    <div style="width: fit-content;">
        <button><a href="/kich-thuoc/add">Thêm kích thước</a></button> <br> <br>
        <div style="background: #dbefef">
            ${rp.message}
        </div> <br>
        <form method="get" action="/kich-thuoc">
            <input type="text" name="key" value="${key}" placeholder="mã, tên">
            <button type="submit">Tìm</button>
        </form> <br>

        <table border="1">
            <thead>
            <tr>
                <th>#</th>
                <th>Mã</th>
                <th>Tên</th>
                <th>Trạng thái</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listInfo.list}" var="o" varStatus="status">
                <tr>
                    <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                    <td>${o.ma}</td>
                    <td>${o.ten}</td>
                    <td>${o.trangThai}</td>
                    <td>
                        <a href="/kich-thuoc/update/${o.id}">Sửa</a>
                        <a href="/kich-thuoc/delete/${o.id}">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%@ include file="/WEB-INF/views/pagination.jsp" %>
    </div>

</div>

</body>
</html>
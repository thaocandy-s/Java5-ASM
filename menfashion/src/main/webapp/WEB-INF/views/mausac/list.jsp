<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Danh sách màu sắc</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="container">
    <h2 class="header">Danh sách màu sắc</h2>

    <div class="mb-3">
        <a href="/mau-sac/add" class="btn">Thêm màu sắc</a>
    </div>

    <c:if test="${not empty rp.message}">
        <div class="alert">
                ${rp.message}
        </div>
    </c:if>

    <form method="get" action="/mau-sac" class="form-inline">
        <input type="text" name="key" value="${key}" placeholder="Mã, tên">
        <button type="submit" class="btn">Tìm</button>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Mã</th>
            <th>Tên</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listInfo.list}" var="o" varStatus="status">
            <tr>
                <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                <td>${o.ma}</td>
                <td>${o.ten}</td>
                <td style="text-align: center">
                    <span class="${o.trangThai == 'ACTIVE' ? 'tag-green' : 'tag-red'}">
                            ${o.trangThai}
                    </span>
                </td>
                <td class="action-btns">
                    <a href="/mau-sac/update/${o.id}" class="edit" title="Sửa">
                        <i class="fas fa-edit"></i> <!-- Icon Sửa -->
                    </a>
                    <a href="/mau-sac/delete/${o.id}" class="delete" title="Xóa">
                        <i class="fas fa-trash"></i> <!-- Icon Xóa -->
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%@ include file="/WEB-INF/views/pagination.jsp" %>
</div>
</body>
</html>

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container">
    <h2 class="header">Danh sách kích thước</h2>

    <div class="mb-3">
        <a href="/kich-thuoc/add" class="btn">Thêm kích thước</a>
    </div>

    <c:if test="${not empty rp.message}">
        <div class="alert alert-info">
                ${rp.message}
        </div>
    </c:if>

    <form method="get" action="/kich-thuoc" class="form-inline">
        <input type="text" name="key" value="${key}" class="form-control mr-2" placeholder="Mã, tên">
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
                    <a href="/kich-thuoc/update/${o.id}" class="edit" title="Sửa">
                        <i class="fas fa-edit"></i> <!-- Icon Sửa -->
                    </a>
                    <a href="/kich-thuoc/delete/${o.id}" class="delete" title="Xóa">
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
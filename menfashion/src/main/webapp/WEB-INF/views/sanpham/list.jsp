<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container mt-5">
    <h2 class="mb-4">Danh sách sản phẩm</h2>

    <div class="mb-3">
        <a href="/san-pham/add" class="btn btn-primary">Thêm sản phẩm</a>
    </div>

    <c:if test="${rp.message != null}">
        <div class="alert alert-info">
                ${rp.message}
        </div>
    </c:if>

    <form class="form-inline mb-3" method="get" action="/san-pham">
        <input type="text" class="mr-2" name="key" value="${key}" placeholder="mã, tên">
        <button type="submit" class="btn btn-secondary">Tìm</button>
    </form>

    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th>#</th>
            <th>Mã</th>
            <th>Tên</th>
<%--            <th>Số sản phẩm chi tiết</th>--%>
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
<%--                <td>${o.soSanPhamChiTiet}</td>--%>
                <td style="text-align: center">
                    <span class="${o.trangThai == 'ACTIVE' ? 'tag-green' : 'tag-red'}">
                            ${o.trangThai}
                    </span>
                </td>
                <td class="action-btns">
                    <a href="/san-pham/update/${o.id}" class="edit" title="Sửa">
                        <i class="fas fa-edit"></i> <!-- Icon Sửa -->
                    </a>
                    <a href="/san-pham/delete/${o.id}" class="delete" title="Xóa">
                        <i class="fas fa-trash"></i> <!-- Icon Xóa -->
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%@ include file="/WEB-INF/views/pagination.jsp" %>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

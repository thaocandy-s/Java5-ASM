<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Danh sách sản phẩm chi tiết</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="container-fruit mt-4" style="margin-left: 220px">
    <h2 class="h2">Danh sách sản phẩm chi tiết</h2>
    <div>
        <a href="/san-pham-chi-tiet/add" class="btn btn-primary">Thêm sản phẩm chi tiết</a>
    </div>

    <c:if test="${rp.message != null}">
        <div class="alert alert-info mt-3">
                ${rp.message}
        </div>
    </c:if>

    <form method="get" action="/san-pham-chi-tiet" class="form-inline my-3">
        <input type="text" name="key" style="width: 400px" class="mr-2" value="${key}" placeholder="Mã, tên SP, tên màu, tên KT">
        <button type="submit" class="btn btn-secondary">Tìm</button>
    </form>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>#</th>
            <th>Mã SPCT</th>
            <th>Tên sản phẩm</th>
            <th>Màu sắc</th>
            <th>Kích thước</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listInfo.list}" var="o" varStatus="status">
            <tr>
                <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                <td>${o.ma}</td>
                <td>${o.tenSanPham}</td>
                <td>${o.tenMauSac}</td>
                <td>${o.tenKichThuoc}</td>
                <td>${o.soLuong}</td>
                <td>${o.donGia}</td>
                <td style="text-align: center">
                    <span class="${o.trangThai == 'ACTIVE' ? 'tag-green' : 'tag-red'}">
                            ${o.trangThai}
                    </span>
                </td>
                <td class="action-btns">
                    <a href="/san-pham-chi-tiet/update/${o.id}" class="edit" title="Sửa">
                        <i class="fas fa-edit"></i> <!-- Icon Sửa -->
                    </a>
                    <a href="/san-pham-chi-tiet/delete/${o.id}" class="delete" title="Xóa">
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

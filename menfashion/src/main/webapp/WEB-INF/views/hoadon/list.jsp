<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Danh sách hóa đơn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div  class="container-fruit mt-4" style="margin-left: 220px">
    <h2 class="mb-4">Danh sách hóa đơn</h2>

    <c:if test="${not empty rp.message}">
        <div class="alert alert-info">
                ${rp.message}
        </div>
    </c:if>

    <form method="get" action="/hoa-don" class="form-inline mb-4">
        <input type="text" name="key" class="mr-2" value="${key}" placeholder="Tìm mã, mã KH, tên KH, sđt KH, mã NV, tên NV">
        <button type="submit" class="btn btn-primary">Tìm</button>
    </form>

    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th>#</th>
            <th>Mã</th>
            <th>Khách Hàng</th>
            <th>Nhân Viên</th>
            <th>Ngày mua hàng</th>
            <th>Tổng giá</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${map}" var="o" varStatus="status">
            <tr>
                <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                <td>${o.key.id}</td>
                <td>${o.key.khachHang.ma} - ${o.key.khachHang.ten} - ${o.key.khachHang.soDienThoai}</td>
                <td>${o.key.nhanVien.ma} - ${o.key.nhanVien.ten}</td>
                <td>${o.key.ngayMuaHang}</td>
                <td>${o.value}</td>
                <td class="action-btns">
                    <a href="/hoa-don/chi-tiet/${o.key.id}" title="Chi tiết">
                        <i class="fa-sharp-duotone fa-solid fa-circle-info"></i> <!-- Icon Sửa -->
                    </a>
<%--                    <a href="/hoa-don/update/${o.id}" class="edit" title="Sửa">--%>
<%--                        <i class="fas fa-edit"></i> <!-- Icon Sửa -->--%>
<%--                    </a>--%>
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

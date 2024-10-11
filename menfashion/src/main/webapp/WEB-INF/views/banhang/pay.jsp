<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Thanh toán</title>
    <!-- Bootstrap CDN for styling -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-danger text-white">
            <h4 class="mb-0">Thông tin hóa đơn</h4>
        </div>
        <div class="card-body">
            <p><strong>Ngày mua hàng:</strong> ${ngayMuaHang}</p>
            <p><strong>Nhân viên:</strong> ${nhanVien.ma} - ${nhanVien.ten}</p>
            <p><strong>Khách hàng:</strong> ${khachHang.ma} - ${khachHang.ten} - ${khachHang.soDienThoai}</p>
        </div>
    </div>

    <div class="mt-4">
        <h3>Chi tiết hóa đơn</h3>
        <table class="table table-hover table-bordered">
            <thead class="thead-light">
            <tr>
                <th>#</th>
                <th>Sản phẩm chi tiết</th>
                <th>Đơn giá</th>
                <th>Số lượng mua</th>
                <th>Tổng giá</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listSP}" varStatus="status" var="o">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${o.key.ma} - ${o.key.sanPham.ten}</td>
                    <td>${o.key.donGia} đ</td>
                    <td>${o.value}</td>
                    <td>${o.value * o.key.donGia} đ</td>
                </tr>
            </c:forEach>
            <tr>
                <th colspan="4" class="text-right">Thành tiền:</th>
                <th>${amount} đ</th>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="text-right mt-4">
        <form:form action="/ban-hang/pay" method="post" modelAttribute="cart">
            <button type="submit" class="btn btn-success btn-lg">Thanh toán</button>
        </form:form>
    </div>
</div>

<!-- Bootstrap JS and dependencies (optional) -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

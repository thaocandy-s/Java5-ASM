<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết hóa đơn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container mt-5" style="width: 900px">
    <h2>Thông tin chi tiết hóa đơn</h2>
    <hr>

    <!-- Thông tin chung về hóa đơn -->
    <div class="card">
        <div class="card-header">
            <h4>Hóa đơn #${data.hoaDon.id}</h4>
        </div>
        <div class="card-body">
            <p><strong>Nhân viên:</strong> ${data.hoaDon.nhanVien.ten} (${data.hoaDon.nhanVien.tenDangNhap})</p>
            <p><strong>Khách hàng:</strong> ${data.hoaDon.khachHang.ten} - SĐT: ${data.hoaDon.khachHang.soDienThoai}</p>
            <p><strong>Ngày mua hàng:</strong> ${data.hoaDon.ngayMuaHang}</p>
            <p><strong>Tổng giá:</strong> ${data.tongGia}</p>
            <a href="/hoa-don/chi-tiet/pdf/${data.hoaDon.id}" class="btn btn-primary" target="_blank">In Hóa Đơn</a>
        </div>
    </div>

    <!-- Danh sách chi tiết hóa đơn -->
    <div class="mt-4">
        <h4>Danh sách sản phẩm trong hóa đơn</h4>
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th>#</th>
                <th>Tên sản phẩm</th>
                <th>Kích thước</th>
                <th>Màu sắc</th>
                <th>Mã SPCT</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="hdct" items="${data.listHDCT}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${hdct.sanPhamChiTiet.sanPham.ten}</td>
                    <td>${hdct.sanPhamChiTiet.kichThuoc.ten}</td>
                    <td>${hdct.sanPhamChiTiet.mauSac.ten}</td>
                    <td>${hdct.sanPhamChiTiet.ma}</td>
                    <td>${hdct.soLuong}</td>
                    <td>${hdct.donGia}</td>
                    <td>${hdct.soLuong * hdct.donGia}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7" style="text-align: right; padding-right: 15px; font-weight: bolder">Tổng giá</td>
                <td style="font-weight: bolder">${data.tongGia}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Thêm sản phẩm chi tiết</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container mt-4">
    <h2 class="mb-4">Thêm sản phẩm chi tiết</h2>

    <c:if test="${rp.isHasError}">
        <div class="alert-danger">
                ${rp.message}
        </div>
    </c:if>

    <form action="/san-pham-chi-tiet/add" method="post">
        <input type="hidden" name="id" hidden>

        <div class="form-group">
            <label for="ma">Mã sản phẩm chi tiết:</label>
            <input type="text" id="ma" class="form-control" name="ma" value="${rp.data.ma}">
        </div>

        <div class="form-group">
            <label for="idSanPham">Sản phẩm:</label>
            <select id="idSanPham" class="form-control" name="idSanPham">
                <c:forEach items="${listSP}" var="o">
                    <option value="${o.id}" ${rp.data.idSanPham == o.id ? "selected" : ""}>${o.ten}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="idKichThuoc">Kích thước:</label>
            <select id="idKichThuoc" class="form-control" name="idKichThuoc">
                <c:forEach items="${listKT}" var="o">
                    <option value="${o.id}" ${rp.data.idKichThuoc == o.id ? "selected" : ""}>${o.ten}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="idMauSac">Màu sắc:</label>
            <select id="idMauSac" class="form-control" name="idMauSac">
                <c:forEach items="${listMau}" var="o">
                    <option value="${o.id}" ${rp.data.idMauSac == o.id ? "selected" : ""}>${o.ten}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="soLuong">Số lượng:</label>
            <input type="number" id="soLuong" class="form-control" name="soLuong" value="${rp.data.soLuong}">
        </div>

        <div class="form-group">
            <label for="donGia">Đơn giá:</label>
            <input type="number" step="any" id="donGia" class="form-control" name="donGia" value="${rp.data.donGia}">
        </div>

        <div class="form-group">
            <label>Trạng thái hoạt động:</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="active" name="trangThai" value="ACTIVE" checked ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="active">Có</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="inactive" name="trangThai" value="INACTIVE" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="inactive">Không</label>
            </div>
        </div>

        <button type="submit" class="btn btn-success">Thêm</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

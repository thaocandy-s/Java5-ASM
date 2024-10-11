<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa khách hàng</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

<div class="container mt-5">
    <h2 class="mb-4">Thêm khách hàng</h2>

    <c:if test="${rp.isHasError}">
        <div class="alert-danger">
                ${rp.message}
        </div>
    </c:if>

    <form action="/khach-hang/add" method="post">
        <input type="hidden" name="id" hidden>

        <div class="form-group">
            <label for="ma">Mã:</label>
            <input type="text" class="form-control" id="ma" name="ma" value="${rp.data.ma}">
        </div>

        <div class="form-group">
            <label for="ten">Tên:</label>
            <input type="text" class="form-control" id="ten" name="ten" value="${rp.data.ten}">
        </div>

        <div class="form-group">
            <label for="soDienThoai">Số điện thoại:</label>
            <input type="text" class="form-control" id="soDienThoai" name="soDienThoai" value="${rp.data.soDienThoai}">
        </div>

        <div class="form-group">
            <label>Trạng thái hoạt động:</label><br>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" id="active" name="trangThai" value="ACTIVE" checked ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="active">Có</label>
            </div>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" id="inactive" name="trangThai" value="INACTIVE" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="inactive">Không</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

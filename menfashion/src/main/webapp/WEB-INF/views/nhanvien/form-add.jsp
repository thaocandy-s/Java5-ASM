<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Thêm Nhân Viên</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="container mt-4">
    <h2 class="header">Thêm Nhân Viên</h2>

    <c:if test="${not empty rp.message}">
        <div class="alert alert-info">
                ${rp.message}
        </div>
    </c:if>

    <form action="/nhan-vien/add" method="post">
        <input type="number" name="id" hidden value="${rp.data.id}">

        <div class="form-group">
            <label for="ma">Mã:</label>
            <input type="text" class="form-control" id="ma" name="ma" value="${rp.data.ma}" required>
        </div>
        <div class="form-group">
            <label for="ten">Tên:</label>
            <input type="text" class="form-control" id="ten" name="ten" value="${rp.data.ten}" required>
        </div>
        <div class="form-group">
            <label for="tenDangNhap">Tên đăng nhập:</label>
            <input type="text" class="form-control" id="tenDangNhap" name="tenDangNhap" value="${rp.data.tenDangNhap}" required>
        </div>
        <div class="form-group">
            <label for="matKhau">Mật khẩu:</label>
            <input type="password" class="form-control" id="matKhau" name="matKhau" value="${rp.data.matKhau}" required>
        </div>

        <div class="form-group">
            <label>Trạng thái hoạt động:</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai" checked value="ACTIVE" id="active" ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="active">Có</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai" value="INACTIVE" id="inactive" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}>
                <label class="form-check-label" for="inactive">Không</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
</div>
</body>
</html>

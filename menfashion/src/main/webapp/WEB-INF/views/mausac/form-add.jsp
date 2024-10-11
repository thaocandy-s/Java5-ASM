<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SSSS</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="container">
    <h1>THÊM MÀU SẮC</h1>

    <c:if test="${rp.isHasError}">
        <div class="alert alert-danger" role="alert">
                ${rp.message}
        </div>
    </c:if>
    <form action="/mau-sac/add" method="post">

        <input type="number" name="id" hidden value="${rp.data.id}">

        <div class="form-group">
            <label for="ma">Mã:</label>
            <input type="text" class="form-control" name="ma" value="${rp.data.ma}" required>
        </div>

        <div class="form-group">
            <label for="ten">Tên:</label>
            <input type="text" class="form-control" name="ten" value="${rp.data.ten}" required>
        </div>

        <div class="form-group">
            <label>Trạng thái hoạt động:</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai" value="ACTIVE"
                       checked ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}>
                <label class="form-check-label">Có</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai"
                       value="INACTIVE" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}>
                <label class="form-check-label">Không</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

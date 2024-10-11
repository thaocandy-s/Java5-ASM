<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa hóa đơn</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="container mt-4">
    <h2 class="mb-4">Sửa hóa đơn</h2>

    <c:if test="${rp.isHasError}">
        <div class="alert alert-danger">
                ${rp.message}
        </div>
    </c:if>

    <form action="/hoa-don/update" method="post">

        <div class="form-group">
            <label for="id">Mã hóa đơn:</label>
            <input type="text" id="id" class="form-control" name="id" value="${rp.data.id}" readonly>
        </div>

        <div class="form-group">
            <label for="idKhachHang">Khách hàng:</label>
            <select id="idKhachHang" class="form-control" name="idKhachHang">
                <c:forEach items="${listKhachHang}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idKhachHang == o.id ? "selected": ""}>${o.ma} - ${o.ten}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="idNhanVien">Nhân viên:</label>
            <select id="idNhanVien" class="form-control" name="idNhanVien">
                <c:forEach items="${listNhanVien}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idNhanVien == o.id ? "selected": ""}>${o.ma} - ${o.ten}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Sửa</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

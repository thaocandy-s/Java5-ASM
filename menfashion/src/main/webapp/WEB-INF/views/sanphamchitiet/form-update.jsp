<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa sản phẩm chi tiết</title>
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="main-content" style="margin-left:200px; padding:20px;">
    <c:if test="${rp.isHasError}" >
        <div style="background: #fca4a9">
                ${rp.message}
        </div>
    </c:if>

    <form action="/san-pham-chi-tiet/update" method="post">
        <input type="number" name="id" hidden value="${rp.data.id}"><br>

        <div class="form-control">
            Mã:<input type="text" name="ma" value="${rp.data.ma}"><br>
        </div>
        <div class="form-control">
            Sản phẩm:
            <select value="${rp.data.idSanPham}" name="idSanPham">
                <c:forEach items="${listSP}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idSanPham == o.id ? "selected": ""} >${o.ten}</option>
                </c:forEach>
            </select><br>
        </div>
        <div class="form-control">
            Kích thước:
            <select value="${rp.data.idKichThuoc}" name="idKichThuoc">
                <c:forEach items="${listKT}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idKichThuoc == o.id ? "selected" : ""} >${o.ten}</option>
                </c:forEach>
            </select><br>
        </div>
        <div class="form-control">
            Màu sắc:
            <select value="${rp.data.idMauSac}" name="idMauSac">
                <c:forEach items="${listMau}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idMauSac == o.id ? "selected": ""} >${o.ten}</option>
                </c:forEach>
            </select><br>
        </div>
        <div class="form-control">
            Số lượng:<input type="number" name="soLuong" value="${rp.data.soLuong}"><br>
        </div>
        <div class="form-control">
            Đơn giá:<input type="number" name="donGia" value="${rp.data.donGia}"><br>
        </div>


        <div class="form-control">
            Trạng thái hoạt động:
            <input type="radio" name="trangThai" value="ACTIVE" checked ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}> Có |
            <input type="radio" name="trangThai" value="INACTIVE" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}> Không
        </div>

        <button type="submit">Sửa</button>
    </form>

</div>

</body>
</html>
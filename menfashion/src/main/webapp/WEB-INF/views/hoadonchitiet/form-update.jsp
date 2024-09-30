<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa hóa đơn chi tiết</title>
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="main-content" style="margin-left:200px; padding:20px;">
    <c:if test="${rp.isHasError}" >
        <div style="background: #fca4a9">
                ${rp.message}
        </div>
    </c:if>

    <form action="/hoa-don-chi-tiet/update" method="post">

        <div class="form-control">
            Mã:<input type="text" name="id" value="${rp.data.id}" readonly><br>
        </div>
        <div class="form-control">
            Mã hóa đơn:<input min="1" type="number" name="idHoaDon" value="${rp.data.idHoaDon}"><br>
<%--            <select value="${rp.data.idHoaDon}" name="idHoaDon">--%>
<%--                <c:forEach items="${listHoaDon}" var="o" varStatus="status">--%>
<%--                    <option value="${o.id}" ${rp.data.idHoaDon == o.id ? "selected": ""} >${o.id} - ${o.khachHang.ten}</option>--%>
<%--                </c:forEach>--%>
<%--            </select><br>--%>
        </div>

        <div class="form-control">
            Sản phẩm chi tiết:
            <select value="${rp.data.idSanPhamChiTiet}" name="idSanPhamChiTiet">
                <c:forEach items="${listSPCT}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idSanPhamChiTiet == o.id ? "selected": ""} >${o.ma} - ${o.sanPham.ten} - ${o.donGia}</option>
                </c:forEach>
            </select><br>
        </div>
        <div class="form-control">
            Số lượng:<input type="number" min="1" name="soLuong" value="${rp.data.soLuong}"><br>
        </div>


        <button type="submit">Sửa</button>
    </form>

</div>

</body>
</html>
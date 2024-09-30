<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <title>Danh sách hóa đơn chi tiết</title>
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

HÓA ĐƠN

<div class="main-content" style="margin-left:200px; padding:20px;">
    <div style="width: fit-content;">
        <div style="background: #dbefef">
            ${rp.message}
        </div> <br>

        <form method="get" action="/hoa-don-chi-tiet">
            <input type="text" name="key" value="${key}" placeholder="mã, mã HD, mã SPCT">
            <button type="submit">Tìm</button>
        </form> <br>

        <table border="1">
            <thead>
            <tr>
                <th>#</th>
                <th>Mã HĐCT</th>
                <th>Hóa đơn</th>
                <th>Sản phẩm</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>Tổng giá</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listInfo.list}" var="o" varStatus="status">
                <tr>
                    <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                    <td>${o.id}</td>
                    <td>${o.hoaDon.id} - ${o.hoaDon.ngayMuaHang}</td>
                    <td>${o.sanPhamChiTiet.ma}</td>
                    <td>${o.soLuong}</td>
                    <td>${o.donGia}</td>
                    <td>${o.soLuong*o.donGia}</td>

                    <td>
<%--                        <a href="/hoa-don-chi-tiet/info/${o.id}">Hóa đơn CT</a>--%>

                        <a href="/hoa-don-chi-tiet/update/${o.id}">Sửa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%@ include file="/WEB-INF/views/pagination.jsp" %>
    </div>

</div>

</body>
</html>
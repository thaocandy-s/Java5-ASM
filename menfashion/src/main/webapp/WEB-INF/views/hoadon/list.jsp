<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <title>Danh sách hóa đơn</title>
</head>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>

HÓA ĐƠN

<div class="main-content" style="margin-left:200px; padding:20px;">
    <div style="width: fit-content;">
        <div style="background: #dbefef">
            ${rp.message}
        </div> <br>

        <form method="get" action="/hoa-don">
            <input type="text" name="key" value="${key}" placeholder="mã, mã KH, tên KH, sđt KH, mã NV, tên NV">
            <button type="submit">Tìm</button>
        </form> <br>

        <table border="1">
            <thead>
            <tr>
                <th>#</th>
                <th>Mã</th>
                <th>Khách Hàng</th>
                <th>Nhân Viên</th>
                <th>Ngày mua hàng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listInfo.list}" var="o" varStatus="status">
                <tr>
                    <td>${5*(listInfo.currentPage-1)+status.index+1}</td>
                    <td>${o.id}</td>
                    <td>${o.khachHang.ma} - ${o.khachHang.ten} - ${o.khachHang.soDienThoai}</td>
                    <td>${o.nhanVien.ma} - ${o.nhanVien.ten}</td>
                    <td>${o.ngayMuaHang}</td>
                    <td>
<%--                        <a href="/hoa-don-chi-tiet/info/${o.id}">Hóa đơn CT</a>--%>

                        <a href="/hoa-don/update/${o.id}">Sửa</a>
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
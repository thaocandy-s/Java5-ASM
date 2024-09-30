<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>>>> Thanh toán</title>
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="main-content" style="margin-left:200px; padding:20px;">
    <div style="background: #fca4a9">
        Thông tin hóa đơn
    </div>
    <form:form action="/ban-hang/pay" method="post" modelAttribute="cart">
        Ngày: ${ngayMuaHang} ; <br> <br>
        Nhân viên: ${nhanVien.ma} - ${nhanVien.ten};   <br> <br>
        Khách hàng: ${khachHang.ma} - ${khachHang.ten} - ${khachHang.soDienThoai};   <br> <br>
        <hr>
        <h3>Chi tiết hóa đơn</h3>
        <table>
            <tr>
                <th>#</th>
                <th>Sản phẩm chi tiết</th>
                <th>Đơn giá</th>
                <th>Số lượng mua</th>
                <th>Tổng giá</th>
            </tr>

            <c:forEach items="${listSP}" varStatus="status" var="o">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${o.key.ma} - ${o.key.sanPham.ten}</td>
                    <td>${o.key.donGia}</td>

                    <td>${o.value}</td>
                    <td>${o.value * o.key.donGia}</td>

                </tr>
            </c:forEach>
            <tr>
                <th colspan="4">Thành tiền: </th>
                <th>${amount}</th>
            </tr>

        </table>
        <button type="submit">Thanh toán</button>
    </form:form>

</div>

</body>
</html>
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


    <title>Dash board</title>
</head>

<style>
    .form{
        display: flex;
        justify-content: flex-start;
    }
    .form > *{
        padding: 5px;
    }
    .check-box,
    .radio{
        width: 20px;
        height: 20px;
    }
</style>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div>Bán hàng</div>
<div class="main-content" style="margin-left:200px; padding:20px;">

    <h2>${paid.message}</h2>
    <h2>${mess}</h2>


    <form:form class="form" action="/ban-hang/pay/confirm" method="post" modelAttribute="cart">
        <div>
            <h3>Danh sách sản phẩm đang bán</h3>
            <table border="1">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Mã SPCT</th>
                    <th>Tên sản phẩm</th>
                    <th>Màu sắc</th>
                    <th>Kích thước</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Chọn</th>
                    <th>Nhập số lượng</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listSPCT}" var="o" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${o.ma}</td>
                        <td>${o.sanPham.ten}</td>
                        <td>${o.mauSac.ten}</td>
                        <td>${o.kichThuoc.ten}</td>
                        <td>${o.soLuong}</td>
                        <td>${o.donGia}</td>
                        <td>
                           <input class="check-box" type="checkbox" name="sanPhamsInCart" value="${o.id}"
                               <c:if test="${not empty cart.mapSanPham[o.id]}">checked</c:if>
                           >
                        </td>
                        <td>
                            <input width="50px" type="number" min="0" max="${o.soLuong}" name="soLuongs[${o.id}]"
                            value="${not empty cart.mapSanPham[o.id] ? cart.mapSanPham[o.id] : 0}"
                            >
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <h3>Danh sách khách hàng</h3>
            <table border="1">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Mã</th>
                    <th>Tên</th>
                    <th>Số điện thoại</th>
                    <th>Chọn</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listKH}" var="o" varStatus="status">
                    <c:if test="${o.trangThai == 'ACTIVE'}">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>${o.ma}</td>
                            <td>${o.ten}</td>
                            <td>${o.soDienThoai}</td>
                            <td>
                                <input class="radio" type="radio" name="idKhachHang" value="${o.id}" ${cart.idKhachHang == o.id ? "checked" : ""}>
                            </td>
                        </tr>
                    </c:if>

                </c:forEach>
                </tbody>
            </table>


            <br>
            <button type="submit">Tiếp theo</button>
        </div>


    </form:form>




</div>

</body>
</html>
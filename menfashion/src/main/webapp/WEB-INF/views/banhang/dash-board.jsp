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

    <!-- Bootstrap CDN for styling -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<style>


    .form {
        display: flex;
        justify-content: space-between;
        gap: 20px;
    }

    .form > div {
        flex: 1;
        padding: 10px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background-color: #f9f9f9; /* Màu sáng hơn cho bảng */
        font-size: 13px; /* Font nhỏ hơn */
    }

    table, th, td {
        border: 1px solid #ddd;
    }

    th {
        background-color: #e0e0e0; /* Màu sáng cho tiêu đề bảng */
        position: sticky;
        top: 0;
        z-index: 10; /* Giữ tiêu đề cố định */
    }

    th, td {
        padding: 8px;
        text-align: center;
    }

    /* Tùy chỉnh thanh cuộn */
    .scrollable-table {
        max-height: 500px; /* Chiều cao cố định cho bảng */
        overflow-y: auto;
        scrollbar-width: thin; /* Độ dày của thanh cuộn (Firefox) */
        scrollbar-color: #888 #e0e0e0; /* Màu thanh cuộn và nền (Firefox) */
    }

    /* Tùy chỉnh thanh cuộn cho Chrome, Edge, Safari */
    .scrollable-table::-webkit-scrollbar {
        width: 8px;
    }

    .scrollable-table::-webkit-scrollbar-track {
        background-color: #e0e0e0;
    }

    .scrollable-table::-webkit-scrollbar-thumb {
        background-color: #888;
        border-radius: 4px;
    }

    .scrollable-table::-webkit-scrollbar-thumb:hover {
        background-color: #555;
    }

    /* Đảm bảo checkbox và radio có kích thước phù hợp */
    .check-box, .radio {
        width: 20px;
        height: 20px;
    }

</style>

<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div>Bán hàng</div>
<div class="main-content" style="margin-left:200px; padding:20px;">

    <c:if test="${paid.message != ''}">
        <h4 class="alert-info">${paid.message}</h4>
    </c:if>
    <c:if test="${mess != ''}">
        <h4 class="alert-danger">${mess}</h4>
    </c:if>


    <form:form class="form" action="/ban-hang/pay/confirm" method="post" modelAttribute="cart">
        <div>
            <h3>Danh sách sản phẩm đang bán</h3>
            <!-- Bảng cuộn có header cố định -->
            <div class="scrollable-table">
                <table class="table table-bordered">
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
                            <td>${status.index + 1}</td>
                            <td>${o.ma}</td>
                            <td>${o.sanPham.ten}</td>
                            <td>${o.mauSac.ten}</td>
                            <td>${o.kichThuoc.ten}</td>
                            <td>${o.soLuong}</td>
                            <td>${o.donGia}</td>
                            <td>
                                <input class="check-box" type="checkbox" name="sanPhamsInCart" value="${o.id}"
                                       <c:if test="${not empty cart.mapSanPham[o.id]}">checked</c:if>>
                            </td>
                            <td>
                                <input width="50px" type="number" name="soLuongs[${o.id}]"
                                       value="${not empty cart.mapSanPham[o.id] ? cart.mapSanPham[o.id] : 0}">
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div>
            <h3>Danh sách khách hàng</h3>
            <!-- Bảng cuộn có header cố định -->
            <div class="scrollable-table">
                <table class="table table-bordered">
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
                                <td>${status.index + 1}</td>
                                <td>${o.ma}</td>
                                <td>${o.ten}</td>
                                <td>${o.soDienThoai}</td>
                                <td>
                                    <input class="radio" type="radio" name="idKhachHang" value="${o.id}"
                                        ${cart.idKhachHang == o.id ? "checked" : ""}>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <br>
            <button type="submit" class="btn btn-primary">Tiếp theo</button>
        </div>
    </form:form>

</div>

</body>
</html>

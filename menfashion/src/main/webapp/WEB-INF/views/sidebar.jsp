<%@page pageEncoding="UTF-8" %>
<div class="head">
    <div>${currentUser.ma} - ${currentUser.tenDangNhap}</div>
</div>


<div class="sidebar">
    <a href="/ban-hang">Bán hàng</a>

    <a href="/san-pham">Sản phẩm</a>
    <a href="/san-pham-chi-tiet">Sản phẩm chi tiết</a>
    <a href="/mau-sac">Màu sắc</a>
    <a href="/kich-thuoc">Kích thước</a>
    <a href="/khach-hang">Khách hàng</a>

    <c:if test="${isAdmin}">
        <a href="/nhan-vien">Nhân viên</a>
        <a href="/hoa-don">Hóa đơn</a>
        <a href="/hoa-don-chi-tiet">Hóa đơn chi tiết</a>
    </c:if>


</div>

<style>
    .sidebar {
        height: 100%; /* Chiều cao 100% màn hình */
        width: 200px; /* Độ rộng của sidebar */
        position: fixed; /* Sidebar cố định */
        top: 0;
        left: 0;
        background-color: #333; /* Màu nền */
        padding-top: 20px; /* Khoảng cách phía trên */
    }

    .sidebar a {
        padding: 15px;
        text-decoration: none;
        font-size: 18px;
        color: white; /* Màu chữ */
        display: block; /* Hiển thị dưới dạng block */
    }

    .sidebar a:hover {
        background-color: rgba(86, 110, 124, 0.57); /* Màu nền khi hover */
    }

    .pagination {
        margin-top: 10px;
        width: 100%;
        text-align: center;

    }

    .pagination > * {
        margin-right: 5px;
        border: solid 1px;
        border-radius: 10px;
        padding: 3px;
        padding-inline: 10px;
    }

    .form-control {
        margin-bottom: 10px;
    }

    .head {
        display: flex;
        justify-content: center;
        justify-items: center;
        color: azure;
        margin: 0;
        background-color: #333;
        width: 100%;
        height: 50px;
        padding: 10px;
    }
</style>

<%@page pageEncoding="UTF-8" %>
<div class="head">
    <div>${currentUser.ma} - ${currentUser.tenDangNhap}</div>
</div>


<div class="sidebar">
    <div class="logo">MAN FASHION</div>

    <a href="/ban-hang">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart"
             viewBox="0 0 16 16">
            <path d="M0 1.5A.5.5 0 0 1 .5 1h1a.5.5 0 0 1 .485.379L2.89 5H14.5a.5.5 0 0 1 .491.592l-1.5 7A.5.5 0 0 1 13 13H4a.5.5 0 0 1-.491-.408L1.01 2H.5a.5.5 0 0 1-.5-.5zm3.14 6 1.2 6h7.32l1.2-6H3.14zM5.5 12a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm6 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"></path>
        </svg>
        Bán hàng
    </a>
    <a href="${pageContext.request.contextPath}/san-pham">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box-seam"
             viewBox="0 0 16 16">
            <path d="M3.67 3.7L8 5.383l4.33-1.683L8 2.017 3.67 3.7zM2.5 4.5v7l4.5 1.75v-7L2.5 4.5zm5 8.25L12.5 11.5v-7l-4.5 1.75v7zm6-8.25l-4.5 1.75v7l4.5-1.75v-7z"></path>
        </svg>
        Sản phẩm
    </a>
    <a href="${pageContext.request.contextPath}/san-pham-chi-tiet">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-list"
             viewBox="0 0 16 16">
            <path d="M14 4.5V14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4.5h13zm0-1H1.5A1.5 1.5 0 0 0 0 5v9a2 2 0 0 0 2 2h11a2 2 0 0 0 2-2V5a1.5 1.5 0 0 0-1.5-1.5zm-9 2a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h1zm0 3a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h1zM4.5 8v1h1V8h-1zm0 3v1h1v-1h-1z"></path>
            <path d="M7 5a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1H7zm0 3a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1H7zm0 3a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1H7z"></path>
        </svg>
        Sản phẩm chi tiết
    </a>
    <a href="${pageContext.request.contextPath}/mau-sac">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-palette"
             viewBox="0 0 16 16">
            <path d="M11.5 1a5.998 5.998 0 0 0-5.664 4.002A4.007 4.007 0 0 1 2.022 8c.203-.68.56-1.223 1.016-1.653A5.999 5.999 0 0 0 8 1.707a5.999 5.999 0 0 0-5.99 6.186 2.83 2.83 0 0 0 1.47 4.47C4.475 12.98 5.488 13 6.5 13c.735 0 1.354-.06 1.935-.202.425.127.88.202 1.379.202 1.785 0 3.5-1.576 3.5-3.5 0-.94-.38-1.894-1.005-2.618a4.01 4.01 0 0 1 .9-.48 5.998 5.998 0 0 0 3.43-5.286A5.993 5.993 0 0 0 11.5 1z"></path>
        </svg>
        Màu sắc
    </a>
    <a href="${pageContext.request.contextPath}/kich-thuoc">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-rulers"
             viewBox="0 0 16 16">
            <path d="M12 1.5V0h-1.5v1H8V0H6.5v1H4V0H2.5v1H1v.5h2.5v1H4v2h2V1.5h.5V2H8v1.5h.5V2H12V1.5zm-2.5 7V5.5H8V6H6.5V4.5H6v1.5H4.5V5.5H4v1.5H2.5V7H4v1.5H2.5V9H4v1.5h1.5V8H6v1.5H7v1H9v1.5H10v-1.5H12v1.5H13.5V10h1v-.5h1V9h-2.5v-1H11.5V7H12v-.5h1.5V5H12V4h-1.5v-.5H10V4H8.5V5H6.5v1.5H8V6H9v2.5z"></path>
        </svg>
        Kích thước
    </a>
    <a href="${pageContext.request.contextPath}/khach-hang">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person"
             viewBox="0 0 16 16">
            <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
            <path d="M14 14s-1-2-6-2-6 2-6 2V12s1-1 6-1 6 1 6 1v2z"></path>
        </svg>
        Khách hàng
    </a>

    <c:if test="${isAdmin}">
        <a href="${pageContext.request.contextPath}/nhan-vien">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people"
                 viewBox="0 0 16 16">
                <path d="M13 14s-1-1-5-1-5 1-5 1v1h10v-1zM6.5 10a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5zm5 0a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5zm-5 1c1.933 0 5 .67 5 2v1h-10v-1c0-1.33 3.067-2 5-2z"></path>
            </svg>
            Nhân viên
        </a>
        <a href="${pageContext.request.contextPath}/hoa-don">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-receipt"
                 viewBox="0 0 16 16">
                <path d="M2 1v14l2-2 2 2 2-2 2 2 2-2 2 2V1H2zm12 0h1v13.535l-.793-.792a1 1 0 0 0-1.414 0l-.793.792H2.707l-.793-.792a1 1 0 0 0-1.414 0L1 14.535V1h1v12.59L2 12.59l1 .707v-12.59h8v12.59l1-.707 1 .707V1zm-1 0h1v12h-1V1z"></path>
            </svg>
            Hóa đơn
        </a>

<%--        <a href="/hoa-don-chi-tiet">--%>
<%--            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"--%>
<%--                 class="bi bi-file-earmark-text" viewBox="0 0 16 16">--%>
<%--                <path d="M5 10v-.5a.5.5 0 0 1 .5-.5H7v-1H5.5A.5.5 0 0 1 5 7.5V7h4v.5a.5.5 0 0 1-.5.5H7v1h1.5a.5.5 0 0 1 .5.5v.5h-4zm1-4a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1H6z"/>--%>
<%--                <path d="M14 4.5V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.293a1 1 0 0 1 .707.293l4 4a1 1 0 0 1 .293.707zm-1-.293L9.293 1H3v13h10V4.207z"/>--%>
<%--            </svg>--%>
<%--            Hóa đơn chi tiết--%>
<%--        </a>--%>
    </c:if>
</div>


<style>
    body {
        background-color: #f5f5f5;
        padding: 0;
        margin: 0;
        box-sizing: border-box;
    }

    body * {
        font-family: Tahoma, serif;
    }

    .logo {
        text-align: center;
        padding: 10px;
        height: 50px;
        box-shadow: gray 0 1px 5px;

    }

    .sidebar {
        height: 100%; /* Chiều cao 100% màn hình */
        width: 200px; /* Độ rộng của sidebar */
        position: fixed; /* Sidebar cố định */
        z-index: 100;
        top: 0;
        left: 0;
        background-color: white;
        box-shadow: gray 0 1px 5px;
    }

    body:not(.logo),
    body:not(.head),
    body:not(.sidebar)
    {
        font-size: 14px; /* Font nhỏ hơn */
    }


    .sidebar a {
        padding: 15px;
        text-decoration: none;
        font-size: 18px;
        color: black; /* Màu chữ */
        display: block; /* Hiển thị dưới dạng block */
    }

    .sidebar a:hover {
        background-color: #fcb1a3; /* Màu nền khi hover */
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
        margin: 0;
        box-shadow: gray 0 1px 5px;
        background-color: white;
        width: 100%;
        height: 50px;
        padding: 10px;
        position: sticky;
        top: 0;
        left: 0;
    }

    .active {
        border-right: 3px solid #ef7761; /* Màu sắc của đường viền */
    }



    .container {
        max-width: 800px;
        margin: 30px auto;
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        animation: fadeIn 0.5s ease forwards; /* Thêm hiệu ứng fade in */
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }



    .header {
        text-align: center;
        margin-bottom: 20px;
    }

    .btn {
        background-color: #ee7c68;
        color: white;
        border: none;
        padding: 10px 15px;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
        text-decoration: none;
    }

    .btn:hover {
        background-color: #f58f7d;
    }

    .alert {
        background-color: #dbefef;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 20px;
        color: #333;
    }

    .form-inline {
        margin-bottom: 20px;
    }

    .form-inline input {
        padding: 10px;
        border: 1px solid #d9d9d9;
        border-radius: 4px;
        width: auto;
        margin-right: 10px;
    }

    .table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    .table th, .table td {
        border: 1px solid #d9d9d9;
        padding: 8px;
        text-align: left;
    }

    .table th {
        background-color: #f0f0f0;
    }

    .table tbody tr:hover {
        background-color: #fafafa;
    }

    .action-btns {
        text-align: center;
    }

    .action-btns a {
        text-decoration: none;
        margin-right: 10px;
    }

    .action-btns .edit {
        color: #faad14; /* Yellow */
    }

    .action-btns .delete {
        color: #ff4d4f; /* Red */
    }

    .tag-green {
        border-radius: 7px;
        padding: 2px 7px;
        border: 1px solid #389e0d;
        color: #389e0d;
        background-color: #f6ffed;
        text-transform: lowercase;
    }

    .tag-red {
        border-radius: 7px;
        padding: 2px 7px;
        border: 1px solid #D4380D;
        color: #D4380D;
        background-color: #fff2e8;
        text-transform: lowercase;

    }

</style>

<script>
    const currentPath = window.location.pathname;
    const links = document.querySelectorAll('.sidebar a');
    links.forEach(link => {
        const linkHref = link.getAttribute('href');
        if (currentPath === linkHref || currentPath.startsWith(linkHref)) {
            link.classList.add('active');
        }
    });

</script>


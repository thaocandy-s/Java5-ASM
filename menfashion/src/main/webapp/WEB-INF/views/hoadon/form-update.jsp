<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa hóa đơn</title>
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="main-content" style="margin-left:200px; padding:20px;">
    <c:if test="${rp.isHasError}" >
        <div style="background: #fca4a9">
                ${rp.message}
        </div>
    </c:if>

    <form action="/hoa-don/update" method="post">

        <div class="form-control">
            Mã:<input type="text" name="id" value="${rp.data.id}" readonly><br>
        </div>
        <div class="form-control">
            Khách hàng:
            <select value="${rp.data.idKhachHang}" name="idKhachHang">
                <c:forEach items="${listKhachHang}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idKhachHang == o.id ? "selected": ""} >${o.ma} - ${o.ten}</option>
                </c:forEach>
            </select><br>
        </div>

        <div class="form-control">
            Nhân viên:
            <select value="${rp.data.idNhanVien}" name="idNhanVien">
                <c:forEach items="${listNhanVien}" var="o" varStatus="status">
                    <option value="${o.id}" ${rp.data.idNhanVien == o.id ? "selected": ""} >${o.ma} - ${o.ten}</option>
                </c:forEach>
            </select><br>
        </div>

        <button type="submit">Sửa</button>
    </form>

</div>

</body>
</html>
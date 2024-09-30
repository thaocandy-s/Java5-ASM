<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sửa khách hàng</title>
</head>
<body>
<%@ include file="/WEB-INF/views/sidebar.jsp" %>
<div class="main-content" style="margin-left:200px; padding:20px;">
    <c:if test="${rp.isHasError}" >
        <div style="background: #fca4a9">
                ${rp.message}
        </div>
    </c:if>

    <form action="/mau-sac/update" method="post">
        <input type="number" name="id" hidden value="${rp.data.id}"><br>

        <div class="form-control">
            Mã:<input type="text" name="ma" value="${rp.data.ma}" readonly><br>
        </div>
        <div class="form-control">
            Tên:<input type="text" name="ten" value="${rp.data.ten}"><br>
        </div>

        <div class="form-control">
            Trạng thái hoạt động:
            <input type="radio" name="trangThai" value="ACTIVE" ${rp.data.trangThai == 'ACTIVE' ? 'checked' : ''}> Có |
            <input type="radio" name="trangThai" value="INACTIVE" ${rp.data.trangThai == 'INACTIVE' ? 'checked' : ''}> Không
        </div>

        <button type="submit">Sửa</button>
    </form>

</div>

</body>
</html>
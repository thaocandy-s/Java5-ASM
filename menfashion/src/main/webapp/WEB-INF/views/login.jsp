<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .error{
        color: red;
    }
</style>
<body>

    <h1>LOGIN PAGE</h1>

    <c:if test="${rp.isHasError}">
        <div style="background: #fca4a9">
                ${rp.message}
        </div>
    </c:if>

    <form:form action="" method="post" modelAttribute="loginReq">
        Tên đăng nhập: <input value="${username}" type="text" name="username"> <br>
        <form:errors cssClass="error" path="username" />  <br>
        Mật khẩu: <input value="${password}" type="password" name="password"> <br>
        <form:errors cssClass="error" path="password" />  <br>

        <button type="submit">Submit</button>
    </form:form>

</body>
</html>

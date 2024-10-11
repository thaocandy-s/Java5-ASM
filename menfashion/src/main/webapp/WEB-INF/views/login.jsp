<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MAN FASHION - Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f8ff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 350px;
            text-align: left;
        }
        .login-container h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .error {
            color: red;
            font-size: 12px;
            text-align: left;
        }
        button[type="submit"] {
            background-color: #ff7f50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        button[type="submit"]:hover {
            background-color: #ff6347;
        }
        .message {
            background-color: #fca4a9;
            color: #333;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h1 style="text-align: center">MAN FASHION - LOGIN</h1>

    <c:if test="${rp.isHasError}">
        <div class="message">
                ${rp.message}
        </div>
    </c:if>

    <form:form action="" method="post" modelAttribute="loginReq">
        <label for="username">Tên đăng nhập</label>
        <input value="${username}" type="text" id="username" name="username">
        <form:errors cssClass="error" path="username" /> <br> <br>

        <label for="password">Mật khẩu</label>
        <input value="${password}" type="password" id="password" name="password">
        <form:errors cssClass="error" path="password" /> <br> <br>

        <button type="submit">Đăng nhập</button>
    </form:form>
</div>

</body>
</html>

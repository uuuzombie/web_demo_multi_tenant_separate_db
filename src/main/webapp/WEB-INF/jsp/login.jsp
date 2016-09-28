<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common.jsp"%>

<html>
<head>
    <title>login</title>
</head>
<body>

    <form:form action="login" method="post">
        <div>
            <input type="text" name="userName" placeholder="用户名">
        </div>
        <div>
            <input type="password" name="password" placeholder="密码">
        </div>
        <div>
            <input type="submit" value="登  录">
        </div>
    </form:form>

</body>
</html>

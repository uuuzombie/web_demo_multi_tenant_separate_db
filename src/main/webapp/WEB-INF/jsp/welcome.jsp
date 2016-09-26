<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common.jsp"%>
<html>
<head>
    <title>welcome</title>
</head>
<body>

    <div>
        <form:form id="tenant_user" modelAttribute="tenantUser" method="post">
            <form:input path="userName" type="text" value="${tenantUser.userName}" ></form:input>

            <br><br>
            Tenant<br>
            name : ${tenantUser.tenant.name} <br>
            token : ${tenantUser.tenant.token} <br>
            dbName : <form:input path="tenant.dbName" type="text" value="${tenantUser.tenant.dbName}" ></form:input>

        </form:form>

        <form:form action="logout">
            <div>
                <input type="submit" value="注 销" >
            </div>
        </form:form>

    </div>
</body>
</html>

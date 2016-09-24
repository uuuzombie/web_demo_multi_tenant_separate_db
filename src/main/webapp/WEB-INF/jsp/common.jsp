<!DOCTYPE HTML>

<!-- jstl -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- spring tags -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%--js--%>
<script src="/static/js/lib.js"></script>
<script src="/static/js/business.js"></script>
<script src="/static/js/plugins.js"></script>


<!-- custom css -->
<link href="/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="/static/css/mod.css" rel="stylesheet" type="text/css" />
<link href="/static/css/page.css" rel="stylesheet" type="text/css" />



<!-- web root -->
<c:set var="root_path" value="${pageContext.request.contextPath}" />
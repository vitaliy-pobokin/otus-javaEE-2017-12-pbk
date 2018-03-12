<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="${ctx}/styles/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/styles/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/styles/styles.css" rel="stylesheet">
</head>
<body>
<%@include file="WEB-INF/views/header.jsp"%>
<%@include file="WEB-INF/views/nav.jsp"%>
<div style="width: 1170px; display:flex; flex-wrap: wrap; margin-left: auto; margin-right: auto;">
    <div style="width: 100%; max-width: 100%;" >
        <div class="frame" style="padding: 20px">
            <h2>Hello World!</h2>
        </div>
    </div>
</div>
<%@include file="WEB-INF/views/footer.jsp"%>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="hw4App">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>My Company</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="${ctx}/styles/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/styles/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/styles/styles.css" rel="stylesheet">
    <base href="${ctx}/">
    <script type="text/javascript">
        var contextPath = "${ctx}";
    </script>
</head>

<body ng-controller="ApplicationController">
<%@include file="WEB-INF/views/header.jsp"%>
<%@include file="WEB-INF/views/nav.jsp"%>
<div ui-view="content"></div>
<%@include file="WEB-INF/views/footer.jsp"%>
<script src="${ctx}/scripts/lib/angular.min.js"></script>
<script src="${ctx}/scripts/lib/angular-ui-router.min.js"></script>
<script src="${ctx}/scripts/lib/stateEvents.min.js"></script>
<script src="${ctx}/scripts/lib/angular-resource.min.js"></script>
<script src="${ctx}/scripts/lib/ngStorage.min.js"></script>
<script src="${ctx}/scripts/lib/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script src="${ctx}/scripts/lib/jquery.min.js"></script>
<script src="${ctx}/scripts/app/app.js"></script>
<script src="${ctx}/scripts/app/controllers/login_controller.js"></script>
<script src="${ctx}/scripts/app/controllers/application_controller.js"></script>
<script src="${ctx}/scripts/app/controllers/employee_controller.js"></script>
<script src="${ctx}/scripts/app/controllers/departments_controller.js"></script>
<script src="${ctx}/scripts/app/controllers/side_bar_controller.js"></script>
<script src="${ctx}/scripts/app/services.js"></script>
</body>
</html>
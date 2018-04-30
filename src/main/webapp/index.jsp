<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="hw4App">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>My Company</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="${ctx}/static/styles/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/styles/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/static/styles/styles.css" rel="stylesheet">
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
<script src="${ctx}/static/scripts/lib/angular.min.js"></script>
<script src="${ctx}/static/scripts/lib/angular-ui-router.min.js"></script>
<script src="${ctx}/static/scripts/lib/stateEvents.min.js"></script>
<script src="${ctx}/static/scripts/lib/angular-resource.min.js"></script>
<script src="${ctx}/static/scripts/lib/ngStorage.min.js"></script>
<script src="${ctx}/static/scripts/lib/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script src="${ctx}/static/scripts/lib/jquery.min.js"></script>
<script src="${ctx}/static/scripts/lib/Chart.min.js"></script>
<script src="${ctx}/static/scripts/lib/angular-chart.min.js"></script>
<script src="${ctx}/static/scripts/app/app.js"></script>
<script src="${ctx}/static/scripts/app/controllers/login_controller.js"></script>
<script src="${ctx}/static/scripts/app/controllers/application_controller.js"></script>
<script src="${ctx}/static/scripts/app/controllers/employee_controller.js"></script>
<script src="${ctx}/static/scripts/app/controllers/departments_controller.js"></script>
<script src="${ctx}/static/scripts/app/controllers/side_bar_controller.js"></script>
<script src="${ctx}/static/scripts/app/controllers/statistic_controller.js"></script>
<script src="${ctx}/static/scripts/app/services.js"></script>
</body>
</html>
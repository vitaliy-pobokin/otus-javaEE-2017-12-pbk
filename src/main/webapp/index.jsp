<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="hw4App">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>My Company</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="styles/bootstrap.min.css" rel="stylesheet">
    <link href="styles/font-awesome.min.css" rel="stylesheet">
    <link href="styles/styles.css" rel="stylesheet">
    <base href="/">
</head>

<body ng-controller="ApplicationController">
<div ui-view="header"></div>
<div ui-view="nav"></div>
<div ui-view="content"></div>
<div ui-view="footer"></div>
<script src="scripts/lib/angular.min.js"></script>
<script src="scripts/lib/angular-ui-router.min.js"></script>
<script src="scripts/lib/stateEvents.min.js"></script>
<script src="scripts/lib/angular-resource.min.js"></script>
<script src="scripts/lib/ngStorage.min.js"></script>
<script src="scripts/lib/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script src="scripts/lib/jquery.min.js"></script>
<script src="scripts/app/app.js"></script>
<script src="scripts/app/controllers/login_controller.js"></script>
<script src="scripts/app/controllers/application_controller.js"></script>
<script src="scripts/app/controllers/employee_controller.js"></script>
<script src="scripts/app/controllers/departments_controller.js"></script>
<script src="scripts/app/controllers/side_bar_controller.js"></script>
<script src="scripts/app/services.js"></script>
</body>
</html>
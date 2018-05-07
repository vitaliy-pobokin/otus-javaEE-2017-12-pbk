<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse container" style="border-radius:0px">
    <div class="row">
        <div class="col-sm-12">
            <div class="navbar-header">
                <a class="navbar-brand navbar-item" ui-sref="app"><i class="fa fa-film" aria-hidden="true"></i> MyCompany</a>
            </div>
            <ul class="nav navbar-nav navbar-left">
                <li class="navbar-item"><a ui-sref="app.news">News</a></li>
                <li class="navbar-item"><a ui-sref="app.our_employees">Our employees</a></li>
                <li class="navbar-item"><a ui-sref="app.about">About company</a></li>
                <li class="navbar-item" ><a ui-sref="app.scripts">Run scripts</a></li>
                <li class="navbar-item" ><a ui-sref="app.employees">Employees</a></li>
                <li class="navbar-item" ><a ui-sref="app.departments">Departments</a></li>
                <li class="navbar-item" ><a ui-sref="app.statistic">Statistic</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right" ng-controller="LoginController as ctrl">
                <li class="navbar-item" ng-if="!currentUser"><a ui-sref="app.login"><i class="fa fa-sign-in"></i> Login</a></li>
                <li class="navbar-item" ng-if="currentUser"><a href="" ng-click="ctrl.logout()" data-toggle="tooltip" data-placement="top" title="Logout"><span>Hello, {{currentUser.username}}! </span><i class="fa fa-sign-out"></i></a></li>
            </ul>
        </div>
    </div>
</nav>
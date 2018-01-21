<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.examples.pbk.otus.model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accounts</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">id</th>
                <th scope="col">username</th>
                <th scope="col">password</th>
                <th scope="col">role</th>
            </tr>
            </thead>
            <tbody>
            <% List<Account> accounts = (ArrayList<Account>) request.getAttribute("accounts");
                for (Account a : accounts) {%>
            <tr>
                <th scope="row"><%=a.getId()%></th>
                <td><%=a.getUsername()%></td>
                <td><%=a.getPassword()%></td>
                <td><%=a.getRole()%></td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
    <div class="row">
        <a href="<%=request.getContextPath()%>" class="btn btn-outline-secondary col-sm-4">На главную</a>
    </div>
</div>
</body>
</html>

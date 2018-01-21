<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.examples.pbk.otus.model.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Departments</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">city</th>
            </tr>
            </thead>
            <tbody>
            <% List<Department> departments = (ArrayList<Department>) request.getAttribute("departments");
                for (Department d : departments) {%>
            <tr>
                <th scope="row"><%=d.getId()%></th>
                <td><%=d.getName()%></td>
                <td><%=d.getCity()%></td>
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

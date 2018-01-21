<%@ page import="org.examples.pbk.otus.model.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.2.1.min.js"></script>
    <style>
        tr:hover {
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <table class="table table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">name</th>
                        <th scope="col">email</th>
                        <th scope="col">phone</th>
                        <th scope="col">hire date</th>
                        <th scope="col">job</th>
                        <th scope="col">salary</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Employee> employees = (ArrayList<Employee>) request.getAttribute("employees");
                        for (Employee e : employees) {%>
                    <tr class='clickable-row' data-href="<%=request.getContextPath()%>/employee?id=<%=e.getId()%>">
                        <th scope="row"><%=e.getId()%></th>
                        <td><%=e.getName()%></td>
                        <td><%=e.getEmail()%></td>
                        <td><%=e.getPhone()%></td>
                        <td><%=e.getHireDate()%></td>
                        <td><%=e.getJob()%></td>
                        <td><%=e.getSalary()%></td>
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
<script>
    jQuery(document).ready(function($) {
        $(".clickable-row").click(function() {
            window.location = $(this).data("href");
        });
    });
</script>
</html>

<%@ page import="org.examples.pbk.otus.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%Employee emp = (Employee) request.getAttribute("employee");%>
    <title><%=emp.getName()%></title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <form>
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">Employee Name</label>
                <div class="col-sm-6">
                    <input class="form-control" type="text" id="name" name="name" placeholder="Employee Name" value="<%=emp.getName()%>">
                </div>
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Employee Email</label>
                <div class="col-sm-6">
                    <input class="form-control" type="email" id="email" name="email" placeholder="Employee Email" value="<%=emp.getEmail()%>">
                </div>
            </div>
            <div class="form-group row">
                <label for="phone" class="col-sm-2 col-form-label">Employee Phone</label>
                <div class="col-sm-6">
                    <input class="form-control" type="tel" id="phone" name="phone" placeholder="Employee Phone" value="<%=emp.getPhone()%>">
                </div>
            </div>
            <div class="form-group row">
                <label for="job" class="col-sm-2 col-form-label">Employee Job</label>
                <div class="col-sm-6">
                    <input class="form-control" type="text" id="job" name="job" placeholder="Employee Job" value="<%=emp.getJob()%>">
                </div>
            </div>
            <div class="form-group row">
                <label for="salary" class="col-sm-2 col-form-label">Employee Job</label>
                <div class="col-sm-6">
                    <input class="form-control" type="number" id="salary" name="salary" placeholder="Employee Salary" value="<%=emp.getSalary()%>">
                </div>
            </div>
            <div class="row">
                <button type="submit" class="btn btn-outline-secondary col-sm-2" formmethod="post">Save</button>
            </div>
        </form>
        <form>
            <div class="form-group row">
                <input type="hidden" name="delete" value="<%=emp.getId()%>">
                <button type="submit" formmethod="post" class="btn btn-outline-secondary col-sm-2">Delete</button>
            </div>
        </form>
    </div>
</body>
</html>

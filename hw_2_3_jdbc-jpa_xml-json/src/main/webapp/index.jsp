<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JDBC example</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <div class="row">
                    <h2>ДЗ №2 (JDBC)</h2>
                    <h3>Обычный порядок шагов для демонстрации работы приложения</h3>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/drop_tables" class="btn btn-outline-secondary col-sm-10">1. Drop Tables</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/create_tables" class="btn btn-outline-secondary col-sm-10">2. Create Tables</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/upload_accounts" class="btn btn-outline-secondary col-sm-10">3. Upload Accounts</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/upload_departments" class="btn btn-outline-secondary col-sm-10">4. Upload Departments</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/upload_employees" class="btn btn-outline-secondary col-sm-10">5. Upload Employees</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/employee" class="btn btn-outline-secondary col-sm-10">6. Update/Delete Employee By Clicking on them</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/create_procedure" class="btn btn-outline-secondary col-sm-10">7. Create Stored Procedure</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/max_salary" class="btn btn-outline-secondary col-sm-10">8. Show Employee with MAX salary</a>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="row">
                    <h2>ДЗ №3 (XML и JSON)</h2>
                    <h3>Обычный порядок шагов для демонстрации работы приложения</h3>
                    <h4>Для выполнения пункта 1 необходимо выполнить шаги 1-5 (2-5) из ДЗ №2</h4>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/marshal_employees" class="btn btn-outline-secondary col-sm-10">1. Export employees to XML</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/above_average" class="btn btn-outline-secondary col-sm-10">2. Find employees in employees.xml file with above average salary</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/xml_to_json" class="btn btn-outline-secondary col-sm-10">3. XML to JSON converting</a>
                </div>
                <div class="row">
                    <a href="<%=request.getContextPath()%>/json" class="btn btn-outline-secondary col-sm-10">4. Employees with odd id from employees.json</a>
                </div>
            </div>
        </div>

    </div>
</body>
</html>

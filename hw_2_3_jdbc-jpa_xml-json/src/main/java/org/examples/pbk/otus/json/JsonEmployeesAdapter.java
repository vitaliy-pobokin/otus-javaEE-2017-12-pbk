package org.examples.pbk.otus.json;

import org.examples.pbk.otus.model.Account;
import org.examples.pbk.otus.model.Department;
import org.examples.pbk.otus.model.Employee;
import org.examples.pbk.otus.model.EmployeeWrapper;

import javax.json.*;
import javax.json.bind.adapter.JsonbAdapter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JsonEmployeesAdapter implements JsonbAdapter<EmployeeWrapper, JsonObject> {
    @Override
    public JsonObject adaptToJson(EmployeeWrapper e) throws Exception {
        return Json.createObjectBuilder()
                .add("employee", Json.createArrayBuilder(e.getEmployees()))
                .build();
    }

    @Override
    public EmployeeWrapper adaptFromJson(JsonObject adapted) throws Exception {
        EmployeeWrapper e = new EmployeeWrapper();
        JsonObject outer = adapted.getJsonObject("employees");
        JsonArray jsonArray = outer.getJsonArray("employee");
        List<Employee> employeeList = new ArrayList<>();
        for (JsonValue value : jsonArray) {
            JsonObject employeeJson = value.asJsonObject();
            Employee employee = new Employee();
            employee.setId((long) employeeJson.getInt("id"));
            employee.setName(employeeJson.getString("name"));
            employee.setEmail(employeeJson.getString("email"));
            employee.setPhone(employeeJson.getString("phone"));
            employee.setJob(employeeJson.getString("job"));
            employee.setSalary(employeeJson.getInt("salary"));
            Date hireDate = new JsonDateAdapter().adaptFromJson(employeeJson.getString("hiredate"));
            employee.setHireDate(hireDate);

            JsonObject departmentJson = employeeJson.getJsonObject("department");
            Department department = new Department();
            department.setId((long) departmentJson.getInt("id"));
            department.setCity(departmentJson.getString("city"));
            department.setName(departmentJson.getString("name"));
            employee.setDepartment(department);

            JsonObject accountJson = employeeJson.getJsonObject("account");
            Account account = new Account();
            account.setId((long) accountJson.getInt("id"));
            account.setUsername(accountJson.getString("username"));
            account.setPassword(accountJson.getString("password"));
            account.setRole(accountJson.getString("role"));
            employee.setAccount(account);

            employeeList.add(employee);
        }
        e.setEmployees(employeeList);
        return e;
    }
}

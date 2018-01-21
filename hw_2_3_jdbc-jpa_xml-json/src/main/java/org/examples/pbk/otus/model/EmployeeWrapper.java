package org.examples.pbk.otus.model;

import org.examples.pbk.otus.model.Employee;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement (name = "employees")
public class EmployeeWrapper {
    private List<Employee> employees;

    @XmlElement (name = "employee", required = true)
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

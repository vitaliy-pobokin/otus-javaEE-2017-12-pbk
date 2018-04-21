package org.examples.pbk.otus.javaee.hw8.model;

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

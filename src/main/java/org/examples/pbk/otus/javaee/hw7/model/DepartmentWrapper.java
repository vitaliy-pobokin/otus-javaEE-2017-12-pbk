package org.examples.pbk.otus.javaee.hw7.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement (name = "departments")
public class DepartmentWrapper {
    private List<Department> departments;

    @XmlElement (name = "department", required = true)
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}

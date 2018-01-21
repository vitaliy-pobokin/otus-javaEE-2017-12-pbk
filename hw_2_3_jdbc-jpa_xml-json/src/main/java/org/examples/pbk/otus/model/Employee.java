package org.examples.pbk.otus.model;

import org.examples.pbk.otus.xml.XmlDateAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.util.Objects;

@Entity (name = "Employee")
@XmlRootElement
public class Employee {
    @Id
    @Column (name = "EmpId", nullable = false)
    private Long id;

    @Column (name = "EmpName", nullable = false)
    private String name;

    @Column (name = "EmpEmail")
    private String email;

    @Column (name = "EmpPhone")
    private String phone;

    @Column (name = "EmpHireDate", nullable = false)
    private Date hireDate;

    @ManyToOne (optional = false)
    @JoinColumn (name = "EmpDepId", referencedColumnName = "DepId", nullable = false)
    private Department department;

    @Column (name = "EmpJob", nullable = false)
    private String job;

    @Column (name = "EmpSalary", nullable = false)
    private int salary;

    @OneToOne (cascade = CascadeType.REMOVE)
    @JoinColumn (name = "EmpAccId", referencedColumnName = "AccId")
    private Account account;

    @XmlAttribute (name = "id", required = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement (name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement (name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement (name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement (name = "hiredate", required = true)
    @XmlJavaTypeAdapter(XmlDateAdapter.class)
    @JsonbProperty("hiredate")
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @XmlElement (name = "department", required = true)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @XmlElement (name = "job", required = true)
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @XmlElement (name = "salary", required = true)
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @XmlElement (name = "account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate=" + hireDate +
                ", department=" + department +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary &&
                Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(hireDate, employee.hireDate) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(job, employee.job) &&
                Objects.equals(account, employee.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, email, phone, hireDate, department, job, salary, account);
    }
}

package org.examples.pbk.otus.javaee.hw5.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table (name = "Employee")
@SequenceGenerator(name="emp_seq", sequenceName = "emp_seq", initialValue=11)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonbProperty("hiredate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        builder.add("id", id)
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("hiredate", dateFormat.format(hireDate))
                .add("department", department.toJson())
                .add("job", job)
                .add("salary", salary);
        return builder.build();
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

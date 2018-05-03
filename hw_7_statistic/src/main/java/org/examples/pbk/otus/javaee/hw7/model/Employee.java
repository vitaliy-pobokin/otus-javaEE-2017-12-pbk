package org.examples.pbk.otus.javaee.hw7.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.examples.pbk.otus.javaee.hw7.xml.XmlDateAdapter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
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

    @Column (name = "EmpAge", nullable = false)
    private int age;

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

    @XmlAttribute(name = "id", required = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "age", required = true)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement(name = "email", required = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "phone", required = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement(name = "hiredate", required = true)
    @XmlJavaTypeAdapter(XmlDateAdapter.class)
    @JsonProperty("hireDate")
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @XmlElement(name = "department", required = true)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @XmlElement(name = "job", required = true)
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @XmlElement(name = "salary", required = true)
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @XmlElement(name = "account", required = false)
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
                .add("age", age)
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
                ", age='" + age + '\'' +
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
                Objects.equals(age, employee.age) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(hireDate, employee.hireDate) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(job, employee.job) &&
                Objects.equals(account, employee.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, age, email, phone, hireDate, department, job, salary, account);
    }
}

package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.model.Account;
import org.examples.pbk.otus.model.Department;
import org.examples.pbk.otus.model.Employee;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Jdbc
public class JdbcEmployeeDao implements EmployeeDao {

    @Inject
    private DataSource dataSource;

    /*public JdbcEmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Override
    public List<Employee> findAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT EmpId, EmpName, EmpEmail, " +
                             "EmpPhone, EmpHireDate, EmpJob, EmpSalary, " +
                             "DepId, DepName, DepCity, " +
                             "AccId, AccUsername, AccPassword, AccRole " +
                             "FROM Employee " +
                             "LEFT JOIN Department ON DepId = EmpDepId " +
                             "LEFT JOIN Account ON AccId = EmpAccId")) {
            ResultSet rs = ps.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("EmpId"));
                employee.setName(rs.getString("EmpName"));
                employee.setEmail(rs.getString("EmpEmail"));
                employee.setPhone(rs.getString("EmpPhone"));
                employee.setHireDate(rs.getDate("EmpHireDate"));
                employee.setJob(rs.getString("EmpJob"));
                employee.setSalary(rs.getInt("EmpSalary"));
                if (rs.getLong("DepId") != 0) {
                    Department department = new Department();
                    department.setId(rs.getLong("DepId"));
                    department.setName(rs.getString("DepName"));
                    department.setCity(rs.getString("DepCity"));
                    employee.setDepartment(department);
                }
                if (rs.getLong("AccId") != 0) {
                    Account account = new Account();
                    account.setId(rs.getLong("AccId"));
                    account.setUsername(rs.getString("AccUsername"));
                    account.setPassword(rs.getString("AccPassword"));
                    account.setRole(rs.getString("AccRole"));
                    employee.setAccount(account);
                }
                list.add(employee);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee findById(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT EmpId, EmpName, EmpEmail, " +
                     "EmpPhone, EmpHireDate, EmpJob, EmpSalary, " +
                     "DepId, DepName, DepCity, " +
                     "AccId, AccUsername, AccPassword, AccRole " +
                     "FROM Employee " +
                     "LEFT JOIN Department ON DepId = EmpDepId " +
                     "LEFT JOIN Account ON AccId = EmpAccId " +
                     "WHERE EmpId = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("EmpId"));
                employee.setName(rs.getString("EmpName"));
                employee.setEmail(rs.getString("EmpEmail"));
                employee.setPhone(rs.getString("EmpPhone"));
                employee.setHireDate(rs.getDate("EmpHireDate"));
                employee.setJob(rs.getString("EmpJob"));
                employee.setSalary(rs.getInt("EmpSalary"));
                if (rs.getLong("DepId") != 0) {
                    Department department = new Department();
                    department.setId(rs.getLong("DepId"));
                    department.setName(rs.getString("DepName"));
                    department.setCity(rs.getString("DepCity"));
                    employee.setDepartment(department);
                }
                if (rs.getLong("AccId") != 0) {
                    Account account = new Account();
                    account.setId(rs.getLong("AccId"));
                    account.setUsername(rs.getString("AccUsername"));
                    account.setPassword(rs.getString("AccPassword"));
                    account.setRole(rs.getString("AccRole"));
                    employee.setAccount(account);
                }
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Employee employee) {
        try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Employee " +
                "(EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, employee.getId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getPhone());
            ps.setDate(5, employee.getHireDate());
            ps.setLong(6, employee.getDepartment().getId());
            ps.setString(7, employee.getJob());
            ps.setInt(8, employee.getSalary());
            ps.setLong(9, employee.getAccount().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Employee SET " +
                     "EmpName = ?, EmpEmail = ?, " +
                     "EmpPhone = ?, EmpHireDate = ?, " +
                     "EmpDepId = ?, EmpJob = ?, " +
                     "EmpSalary = ?, EmpAccId = ? " +
                     "WHERE EmpId = ?")) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPhone());
            ps.setDate(4, employee.getHireDate());
            ps.setLong(5, employee.getDepartment().getId());
            ps.setString(6, employee.getJob());
            ps.setInt(7, employee.getSalary());
            ps.setLong(8, employee.getAccount().getId());
            ps.setLong(9, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Employee WHERE EmpId = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

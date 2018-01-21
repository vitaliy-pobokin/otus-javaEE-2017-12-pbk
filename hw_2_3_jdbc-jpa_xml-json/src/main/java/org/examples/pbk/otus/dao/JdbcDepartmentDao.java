package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.model.Department;

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
public class JdbcDepartmentDao implements DepartmentDao {

    @Inject
    private DataSource dataSource;

    /*public JdbcDepartmentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Override
    public List<Department> findAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Department")) {
            ResultSet rs = ps.executeQuery();
            List<Department> list = new ArrayList<>();
            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getLong("DepId"));
                department.setName(rs.getString("DepName"));
                department.setCity(rs.getString("DepCity"));
                list.add(department);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department findById(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Department WHERE DepId = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getLong("DepId"));
                department.setName(rs.getString("DepName"));
                department.setCity(rs.getString("DepCity"));
                return department;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Department department) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Department (DepId, DepName, DepCity) VALUES (?, ?, ?)")) {
            ps.setLong(1, department.getId());
            ps.setString(2, department.getName());
            ps.setString(3, department.getCity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Department department) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Department SET DepName = ?, DepCity = ? WHERE DepId = ?")) {
            ps.setString(1, department.getName());
            ps.setString(2, department.getCity());
            ps.setLong(3, department.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Department WHERE DepId = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

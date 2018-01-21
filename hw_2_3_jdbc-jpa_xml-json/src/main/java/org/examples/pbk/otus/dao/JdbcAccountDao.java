package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.model.Account;

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
public class JdbcAccountDao implements AccountDao {

    @Inject
    private DataSource dataSource;

    /*public JdbcAccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Override
    public List<Account> findAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Account")) {
            List<Account> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("AccId"));
                account.setUsername(rs.getString("AccUsername"));
                account.setPassword(rs.getString("AccPassword"));
                account.setRole(rs.getString("AccRole"));
                list.add(account);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findById(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Account WHERE AccId = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("AccId"));
                account.setUsername(rs.getString("AccUsername"));
                account.setPassword(rs.getString("AccPassword"));
                account.setRole(rs.getString("AccRole"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Account account) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (?, ?, ?, ?)")) {
            ps.setLong(1, account.getId());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Account SET AccUsername = ?, AccPassword = ?, AccRole = ? WHERE AccId = ?")) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole());
            ps.setLong(4, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Account WHERE AccId = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

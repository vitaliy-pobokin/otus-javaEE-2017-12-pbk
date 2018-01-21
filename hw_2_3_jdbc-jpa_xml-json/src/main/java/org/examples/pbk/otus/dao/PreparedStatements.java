package org.examples.pbk.otus.dao;

import oracle.sql.NUMBER;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class PreparedStatements {

    @Inject
    DataSource dataSource;

    private static final String SQL_CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE Employee (" +
                    "EmpId NUMBER(8) NOT NULL," +
                    "EmpName VARCHAR2(50) NOT NULL," +
                    "EmpEmail VARCHAR2(50)," +
                    "EmpPhone VARCHAR2(20)," +
                    "EmpHireDate DATE NOT NULL," +
                    "EmpJob VARCHAR2(50) NOT NULL," +
                    "EmpSalary DECIMAL(10, 2) NOT NULL," +
                    "EmpDepId NUMBER(8) NOT NULL," +
                    "EmpAccId NUMBER(8)," +
                    "CONSTRAINT PKEmployee PRIMARY KEY (EmpId)," +
                    "CONSTRAINT FKEmpDepId FOREIGN KEY (EmpDepId) REFERENCES Department (DepId))";

    private static final String SQL_CREATE_TABLE_DEPARTMENT =
            "CREATE TABLE Department (" +
                    "DepId NUMBER(8) NOT NULL," +
                    "DepName VARCHAR2(50) NOT NULL," +
                    "DepCity VARCHAR2(20) NOT NULL," +
                    "CONSTRAINT PKDepartment PRIMARY KEY (DepId))";

    private static final String SQL_CREATE_TABLE_ACCOUNT =
            "CREATE TABLE Account (" +
                    "AccId NUMBER(8) NOT NULL," +
                    "AccUsername VARCHAR2(20) NOT NULL," +
                    "AccPassword VARCHAR2(50) NOT NULL," +
                    "AccRole CHAR(4) NOT NULL," +
                    "CONSTRAINT PKAccount PRIMARY KEY (AccId))";

    private static final String SQL_DROP_TABLE_EMPLOYEE =
            "DROP TABLE Employee";

    private static final String SQL_DROP_TABLE_DEPARTMENT =
            "DROP TABLE Department";

    private static final String SQL_DROP_TABLE_ACCOUNT =
            "DROP TABLE Account";

    private static final String SQL_CREATE_PROCEDURE_MAX_SALARY =
            "CREATE OR REPLACE " +
                    "PROCEDURE max_salary_employee (MaxId OUT NUMBER) AS " +
                    "BEGIN\n" +
                    "  SELECT EmpId INTO MaxId FROM Employee WHERE EmpSalary = (SELECT MAX(EmpSalary) FROM Employee);" +
                    "END max_salary_employee;";

    private static final String SQL_GET_MAX_SALARY_EMPLOYEE = "{call max_salary_employee(?)}";

    public void createTables() {
        createDepartmentTable();
        createAccountTable();
        createEmployeeTable();
    }

    public void dropTables() {
        dropEmployeeTable();
        dropAccountTable();
        dropDepartmentTable();
    }

    public void createMaxSalaryProcedure() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(SQL_CREATE_PROCEDURE_MAX_SALARY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getMaxSalaryEmployeeId() {
        long id = -1;
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall(SQL_GET_MAX_SALARY_EMPLOYEE)) {
            cs.registerOutParameter(1, Types.NUMERIC);
            cs.execute();
            id = cs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void createEmployeeTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE_TABLE_EMPLOYEE)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDepartmentTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE_TABLE_DEPARTMENT)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAccountTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE_TABLE_ACCOUNT)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropEmployeeTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DROP_TABLE_EMPLOYEE)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropDepartmentTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DROP_TABLE_DEPARTMENT)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropAccountTable() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DROP_TABLE_ACCOUNT)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

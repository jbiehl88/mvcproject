package com.jordan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jordan.model.Employee;

public class UserDAO {
	 private String jdbcURL = "jdbc:mysql://localhost:3306/onecorp?useSSL=false";
	    private String jdbcEmployeename = "root";
	    private String jdbcPassword = "";

	    private static final String INSERT_EmployeeS_SQL = "INSERT INTO Employee" + "  (name, password) VALUES " +
	        " (?, ?);";

	    private static final String SELECT_Employee_BY_ID = "select id,name,password from Employee where id =?";
	    private static final String SELECT_ALL_EmployeeS = "select * from Employee";
	    private static final String DELETE_EmployeeS_SQL = "delete from Employee where id = ?;";
	    private static final String UPDATE_EmployeeS_SQL = "update Employee set name = ?,password= ?, where id = ?;";

	    public UserDAO() {}

	    protected Connection getConnection() {
	        Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");//MySQL database version 8.0
	            connection = DriverManager.getConnection(jdbcURL, jdbcEmployeename, jdbcPassword);
	            System.out.println("connection good!!!!!!!!!");
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return connection;
	    }

	    public void insertEmployee(Employee employee) throws SQLException {
	        System.out.println(INSERT_EmployeeS_SQL);
	        // try-with-resource statement will auto close the connection.
	        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EmployeeS_SQL)) {
	            preparedStatement.setString(1, employee.getEmpName());
	            preparedStatement.setString(2, employee.getPassword());
	            //preparedStatement.setString(3, Employee.getCountry());
	            System.out.println(preparedStatement);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    public Employee selectEmployee(int id) {
	        Employee Employee = null;
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Employee_BY_ID);) {
	            preparedStatement.setInt(1, id);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String name = rs.getString("name");
	                String password = rs.getString("password");
	                //String country = rs.getString("country");
	                Employee = new Employee(id, name, password);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return Employee;
	    }

	    public List < Employee > selectAllEmployees() {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List < Employee > Employee = new ArrayList < > ();
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EmployeeS);) {
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String password = rs.getString("password");
	                //String country = rs.getString("country");
	                Employee.add(new Employee(id, name, password));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return Employee;
	    }

	    public boolean deleteEmployee(int id) throws SQLException {
	        boolean rowDeleted;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_EmployeeS_SQL);) {
	            statement.setInt(1, id);
	            rowDeleted = statement.executeUpdate() > 0;
	        }
	        return rowDeleted;
	    }

	    public boolean updateEmployee(Employee employee) throws SQLException {
	        boolean rowUpdated;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_EmployeeS_SQL);) {
	            statement.setString(1, employee.getEmpName());
	            statement.setString(2, employee.getPassword());
	            //statement.setString(3, Employee.getCountry());
	            statement.setInt(3, employee.getEmpId());

	            rowUpdated = statement.executeUpdate() > 0;
	        }
	        return rowUpdated;
	    }

	    private void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	
	    
}

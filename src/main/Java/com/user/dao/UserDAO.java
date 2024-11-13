package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDAO {
	
	private String jdbcURL="jdbc:mysql://localhost:30006/OnlineExamSystem";
	private String jdbcUserName="root";
	private String jdbcPassword="1234";
	
	
	private static final String INSERT_USER_SQL="INSERT INTO users"+"(username,role,password) VALUES "+"(?,?,?,?);";
	private static final String SELECT_USER_BY_ID="SELECT * FROM USERS WHERE ID=?;";
	private static final String SELECT_ALL_USERS="SELECT * FROM USERS;";
	private static final String DELETE_USERS_SQL="DELETE FROM USERS WHERE ID=?;";
	private static final String UPDATE_USERS_SQL="UPDATE USERS SET USERNAME=?,ROLE=?,PASSWORD=? where ID=?;";
	
	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Connection getConnection()
	{
		Connection connection=null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertUser(User user)
	{
		UserDAO dao=new UserDAO();
		
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USER_SQL);
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setString(2,user.getRole());
			preparedStatement.setString(3,user.getPassword());
			
			preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public User selectUser(int id)
	{
		User user=new User();
		UserDAO dao=new UserDAO();

		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1,id);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				user.setId(id);
				user.setUsername(resultSet.getString("Username"));
				user.setRole(resultSet.getString("Role"));
				user.setPassword(resultSet.getString("Password"));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
		}
	
	
	
	public List<User>selectAllUsers()
	{
		List<User> users=new ArrayList<User>();
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement prepareStatement=connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet resultSet=prepareStatement.executeQuery();
			
			
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String Username=resultSet.getString("Username");
				String role=resultSet.getString("Role");
				String Password=resultSet.getString("Password");
				
				users.add(new User(id,Username,role,Password));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return users;
		}
	
	public boolean deleteUser(int id)
	{
		boolean status=false;
		UserDAO dao=new UserDAO();
		
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement prepareStatement=connection.prepareStatement(DELETE_USERS_SQL);
			prepareStatement.setInt(1, id);
			
			status=prepareStatement.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return status;
		
		}
	public boolean updateUser(User user)
	{
		boolean rowUpdated=false;
		UserDAO dao=new UserDAO();
		
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USERS_SQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getRole());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, user.getid());
			
			rowUpdated=preparedStatement.executeUpdate()>0;
	}
		catch(Exception e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}
}
	
	



package com.anhkhoa.resource;

import com.anhkhoa.database.MysqlConnection;
import com.anhkhoa.model.User;
import com.anhkhoa.repository.DataRepository;
import com.anhkhoa.repository.TempUserRepository;
import com.google.gson.Gson;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {
	private Gson gson = new Gson();
	private TempUserRepository userRepo = new TempUserRepository();

	@GET
	@Path("/v0")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		List<User> userList = userRepo.getUsers();
//		String json = gson.toJson(userList);
		Entity<List<User>> json = Entity.json(userList);
		return json.getEntity();
	}

	@GET
	@Path("/v0/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") int id) {
		return userRepo.getUser(id);
	}
	
	/*
	 * 
	 * v0 for example, v... for data from database
	 * 
	 */
	
	@GET
	@Path("/v1")
	@Produces(MediaType.APPLICATION_JSON)
	public String readUsers() {
		List<User> userList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		try {
			Connection connection = new MysqlConnection().getConnection();
			String queryString = "SELECT * FROM `public-api`.user;";
			PreparedStatement pst = connection.prepareStatement(queryString);
			ResultSet results = pst.executeQuery();
			
			while (results.next()) {
				int id = results.getInt("id");
				String name = results.getString("name");
				String gender = results.getString("gender");
				String status = results.getString("status");
//				User user = new User(id, name, gender, status);
				userList.add(new User(id, name, gender, status));
			}
		} catch (Exception e) {
			errorList.add("Error: Read data fail! - " + e.getMessage());
			DataRepository<String> errorRepo = new DataRepository<>(404, errorList);
			return gson.toJson(errorRepo);
		}
		// Entity<List<User>> entity = Entity.json(userList);
		// System.out.println(entity);
		// return entity.getEntity();\
		DataRepository<User> dataRepo = new DataRepository<>(200, userList);
		return gson.toJson(dataRepo); 
	}
	
	@GET
	@Path("/v1/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String readUser(@PathParam("id") int id) {
		List<User> userList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		try (Connection connection = new MysqlConnection().getConnection()) {
			String queryString = "SELECT * FROM `public-api`.user WHERE user.id = ?;";
			PreparedStatement pst = connection.prepareStatement(queryString);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			
			while (result.next()) {
				int userId = result.getInt("id");
				String name = result.getString("name");
				String gender = result.getString("gender");
				String status = result.getString("status");
				userList.add(new User(userId, name, gender, status));
			}
		} catch (Exception e) {
			errorList.add("Error: Read data fail! - " + e.getMessage());
			DataRepository<String> errorRepo = new DataRepository<>(404, errorList);
			return gson.toJson(errorRepo);
		}
		DataRepository<User> dataRepo = new DataRepository<>(200, userList);
 		return gson.toJson(dataRepo);
	}
	
	// Check if a user's name has existed by name
	private static Boolean checkUserExist(Connection conn, String name) throws SQLException {
		String queryString = "SELECT * FROM `public-api`.user WHERE name = ?;";
		PreparedStatement pst = conn.prepareStatement(queryString);
		pst.setString(1, name);
		ResultSet results = pst.executeQuery();
		if (results.next()) {
			return true;
		}
		return false;
	}
	
	// Check if a user's name has existed by id
	private static Boolean checkUserExist(Connection conn, Integer id) throws SQLException {
		String queryString = "SELECT * FROM `public-api`.user WHERE id = ?;";
		PreparedStatement pst = conn.prepareStatement(queryString);
		pst.setInt(1, id);
		ResultSet results = pst.executeQuery();
		if (results.next()) {
			return true;
		}
		return false;
	}

	@POST
	@Path("/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(User user) {
		List<String> responseList = new ArrayList<>();
		try (Connection connection = new MysqlConnection().getConnection()) {
			if (!checkUserExist(connection, user.getName())) {
				String queryString = "INSERT INTO `public-api`.user(name, gender, status) VALUES (?, ?, ?);";
				PreparedStatement pst = connection.prepareStatement(queryString);
				pst.setString(1, user.getName());
				pst.setString(2, user.getGender());
				pst.setString(3, user.getStatus());
				int row = pst.executeUpdate();
				responseList.add((row > 0) ? "Create user successfully!" : "Failed to create user!");
			} else {
				responseList.add("User existed!");
			}
		} catch (Exception e) {
			responseList.add("Error: Failed to create user! - " + e.getMessage());
			return gson.toJson(new DataRepository<>(400, responseList));
		}
		return gson.toJson(new DataRepository<>(200, responseList));
	}
	
	@PUT
	@Path("/v1/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/*
	 * Update user's name
	 */
	public String updateUser(@PathParam("id") int id, User user) {
		List<String> responseList = new ArrayList<>();
		try (Connection connection = new MysqlConnection().getConnection()) {
			if (checkUserExist(connection, id)) {
				String queryString = "UPDATE `public-api`.user SET name = ? WHERE id = ?;";
				PreparedStatement pst = connection.prepareStatement(queryString);
				pst.setString(1, user.getName());
				pst.setInt(2, id);
				Integer row = pst.executeUpdate();
				responseList.add((row > 0) ? "Update user's name successfully!" : "Failed to update user's name!");
			} else {
				responseList.add("User doesn't exist!");
			}
		} catch (Exception e) {
			responseList.add("Error: Failed to update user's name - " + e.getMessage());
			return gson.toJson(new DataRepository<>(400, responseList));
		}
		return gson.toJson(new DataRepository<>(200, responseList));
	}
	
	@DELETE
	@Path("/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteUser(User user) {
		List<String> responseList = new ArrayList<>();
		try (Connection connection = new MysqlConnection().getConnection()) {
			if (checkUserExist(connection, user.getName())) {
				String queryString = "DELETE FROM `public-api`.user WHERE name = ?;";
				PreparedStatement pst = connection.prepareStatement(queryString);
				pst.setString(1, user.getName());
				Integer row = pst.executeUpdate();
				responseList.add((row > 0) ? "Delete user successfully!" : "Failed to delete user!");
			} else {
				responseList.add("User doesn't exist!");
			}
		} catch (Exception e) {
			responseList.add("Error: Failed to delete user - " + e.getMessage());
			return gson.toJson(new DataRepository<>(400, responseList));
		}
		return gson.toJson(new DataRepository<>(200, responseList));
	}
}

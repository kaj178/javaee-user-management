package com.anhkhoa.resource;

import com.anhkhoa.database.MysqlConnection;
import com.anhkhoa.model.User;
import com.anhkhoa.repository.DataRepository;
import com.anhkhoa.repository.UserRepository;
import com.google.gson.Gson;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {
	private Gson gson = new Gson();
	private UserRepository userRepo = new UserRepository();

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
			DataRepository<String> errorRepo = new DataRepository<>(500, errorList);
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
		try {
			Connection connection = new MysqlConnection().getConnection();
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
			DataRepository<String> errorRepo = new DataRepository<>(500, errorList);
			return gson.toJson(errorRepo);
		}
		DataRepository<User> dataRepo = new DataRepository<>(200, userList);
 		return gson.toJson(dataRepo);
	}

	@POST
	@Path("/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User user) {
		try {
			
		} catch (Exception e) {
			System.out.println("Error: Create user fail!" + e.getMessage());
			e.printStackTrace();
		}
		return userRepo.add(user);
	}
}

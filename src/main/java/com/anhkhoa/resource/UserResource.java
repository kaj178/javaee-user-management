package com.anhkhoa.resource;

import com.anhkhoa.database.MysqlConnection;
import com.anhkhoa.model.User;
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
	public Entity<List<User>> readUsers() {
		List<User> userList = new ArrayList<>();
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
			System.out.println("Error: Read data fail! - " + e.getMessage());
			e.printStackTrace();
		}
		Entity<List<User>> entity = Entity.json(userList);
		// System.out.println(entity);
		// return entity.getEntity();\
		return entity; 
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User user) {
//		Client client = ClientBuilder.newClient();
//		WebTarget target = client.target("http://localhost:8080/demorest/webapi/users");
//
//		Response response = target.request(MediaType.APPLICATION_JSON)
//								.post(Entity.json(user));
//		User createdUser = response.readEntity(User.class);
//		return Response.ok(createdUser, MediaType.APPLICATION_JSON).build();
		return userRepo.add(user);
	}
}

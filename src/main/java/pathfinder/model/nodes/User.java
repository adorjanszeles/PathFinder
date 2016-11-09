package pathfinder.model.nodes;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pathfinder.common.RoleEnum;

@NodeEntity
public class User {

	private Integer age;

	private String email;
	private String name;
	@JsonIgnore
	private String password;
	private RoleEnum role;

	@GraphId
	private Long userId;

	@JsonIgnore
	@Relationship(type = "OWNER", direction = Relationship.INCOMING)
	private List<Vehicle> vehicles;

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public RoleEnum getRole() {
		return role;
	}

	public Long getUserId() {
		return userId;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

}

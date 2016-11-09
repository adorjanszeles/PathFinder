package pathfinder.model.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Jármű entitás
 * 
 * @author Kiss László
 */
@NodeEntity
public class Vehicle {

	/**
	 * Magasság
	 */
	private Long height;

	/**
	 * Hosszúság
	 */
	private Long length;

	/**
	 * Tulajdonos
	 */
	@JsonIgnore
	private User owner;

	/**
	 * Renszám
	 */
	private String plateNumber;

	@GraphId
	private Long vehicleId;

	/**
	 * Súly
	 */
	private Long weight;

	/**
	 * Szélesség
	 */
	private Long width;

	public Long getHeight() {
		return this.height;
	}

	public Long getLength() {
		return this.length;
	}

	public User getOwner() {
		return this.owner;
	}

	public String getPlateNumber() {
		return this.plateNumber;
	}

	public Long getVehicleId() {
		return this.vehicleId;
	}

	public Long getWeight() {
		return this.weight;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

}

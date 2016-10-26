package pathfinder.model.nodes;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * ROUTE kapcsolat 2 City node között.
 * 
 * @author Kiss László
 *
 */
@RelationshipEntity(type = "ROUTE")
public class Route {

	/**
	 * Cél város
	 */
	@EndNode
	private City destinationCity;

	/**
	 * Maximális magasság
	 */
	private Long maxHeight;

	/**
	 * Maximális hosszúság
	 */
	private Long maxLength;

	private Long maxWeight;

	private Long maxWidth;

	private String name;

	@GraphId
	private Long routeId;

	@StartNode
	private City startingCity;

	public City getDestinationCity() {
		return this.destinationCity;
	}

	public Long getMaxHeight() {
		return this.maxHeight;
	}

	public Long getMaxLength() {
		return this.maxLength;
	}

	public Long getMaxWeight() {
		return this.maxWeight;
	}

	public Long getMaxWidth() {
		return this.maxWidth;
	}

	public String getName() {
		return this.name;
	}

	public Long getRouteId() {
		return this.routeId;
	}

	public City getStartingCity() {
		return this.startingCity;
	}

	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}

	public void setMaxHeight(Long maxHeight) {
		this.maxHeight = maxHeight;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public void setMaxWeight(Long maxWeight) {
		this.maxWeight = maxWeight;
	}

	public void setMaxWidth(Long maxWidth) {
		this.maxWidth = maxWidth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public void setStartingCity(City startingCity) {
		this.startingCity = startingCity;
	}

}

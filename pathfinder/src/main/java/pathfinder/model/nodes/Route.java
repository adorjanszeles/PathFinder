package pathfinder.model.nodes;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="ROUTE")
public class Route {

	@GraphId
	private Long routeId;
	
	@StartNode
	private City startingCity;
	
	@EndNode
	private City destinationCity;
	
	private Long maxHeight;
	private Long maxWidth;
	private Long maxLength;
	private Long maxWeight;
	
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	public City getStartingCity() {
		return startingCity;
	}
	public void setStartingCity(City startingCity) {
		this.startingCity = startingCity;
	}
	public City getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}
	public Long getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(Long maxHeight) {
		this.maxHeight = maxHeight;
	}
	public Long getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(Long maxWidth) {
		this.maxWidth = maxWidth;
	}
	public Long getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}
	public Long getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(Long maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	
}

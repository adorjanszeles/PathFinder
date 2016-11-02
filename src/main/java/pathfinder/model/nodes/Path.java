package pathfinder.model.nodes;

import java.util.List;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class Path {

	private City end;

	private List<Route> routes;

	private City start;

	private Long sumLength;

	private Vehicle vehicle;

	public City getEnd() {
		return this.end;
	}

	public List<Route> getRoutes() {
		return this.routes;
	}

	public City getStart() {
		return this.start;
	}

	public Long getSumLength() {
		return this.sumLength;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setEnd(City end) {
		this.end = end;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public void setStart(City start) {
		this.start = start;
	}

	public void setSumLength(Long sumLength) {
		this.sumLength = sumLength;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}

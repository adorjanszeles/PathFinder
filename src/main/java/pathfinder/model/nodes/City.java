package pathfinder.model.nodes;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class City {

	@GraphId
	private Long cityId;

	private String name;

	private List<Route> routesFromCity;

	private List<Route> routesToCity;

	public Long getCityId() {
		return this.cityId;
	}

	public String getName() {
		return this.name;
	}

	public List<Route> getRoutesFromCity() {
		return this.routesFromCity;
	}

	public List<Route> getRoutesToCity() {
		return this.routesToCity;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoutesFromCity(List<Route> routesFromCity) {
		this.routesFromCity = routesFromCity;
	}

	public void setRoutesToCity(List<Route> routesToCity) {
		this.routesToCity = routesToCity;
	}

}

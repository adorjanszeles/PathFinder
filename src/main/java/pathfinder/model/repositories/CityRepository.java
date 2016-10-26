package pathfinder.model.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;

/**
 * {@link City} node kezelésére szolgáló repository interface.
 * 
 * @author Kiss László
 *
 */
@Repository
public interface CityRepository extends GraphRepository<City> {

	/**
	 * A kapott paraméterhez tartozó városból vezető utak lekérdezése.
	 * 
	 * @param cityId
	 * @return
	 */
	@Query("MATCH (from:City)-[r:ROUTE]->(to:City) WHERE ID(from)={cityId} return r")
	List<Route> findRoutesFromCity(@Param("cityId") Long cityId);

	/**
	 * A kapott paraméterhez tartozó városba vezető utak lekérdezése.
	 * 
	 * @param cityId
	 * @return
	 */
	@Query("MATCH (from:City)-[r:ROUTE]->(to:City) WHERE ID(to)={cityId} return r")
	List<Route> findRoutesToCity(@Param("cityId") Long cityId);
}

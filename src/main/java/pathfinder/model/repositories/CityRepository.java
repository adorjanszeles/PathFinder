package pathfinder.model.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;

import java.util.List;

/**
 * {@link City} node kezelésére szolgáló repository interface.
 * 
 * @author Kiss László
 *
 */
@Repository
public interface CityRepository extends GraphRepository<City> {

	@Query("MATCH (c:City) WHERE c.name CONTAINS {name} RETURN c")
	List<City> findByName(@Param("name") String name);

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

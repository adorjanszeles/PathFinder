package pathfinder.model.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Route;

/**
 * Úvonal kezelésére szolgáló Repository interface.
 * 
 * @author Kiss László
 *
 */
@Repository
public interface RouteRepository extends GraphRepository<Route> {

	/**
	 * Útvonal keresése név alapján.
	 * 
	 * @param name
	 * @return
	 */
	@Query("MATCH (c1:City)-[r:ROUTE]->(c2:City) WHERE r.name CONTAINS {name} RETURN r")
	List<Route> findByName(@Param("name") String name);

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

	/**
	 * Útkeresés 2 város között egy adott jármű számára.
	 * 
	 * @param startCityId
	 * @param endCityId
	 * @param vehicleId
	 * @return
	 */
	@Query("MATCH (v:Vehicle), (c1:City)-[r:ROUTE*]->(c2:City) "
			+ "WHERE ID(c1)={startCityId} AND ID(c2)={endCityId} AND ID(v)={vehicleId} "
			+ "AND ALL(x in r WHERE x.maxHeight >= v.height AND x.maxWidth >= v.width "
			+ "AND x.maxLength >= v.length AND x.maxWeight >= v.weight) "
			+ "RETURN c1 as start, c2 as end, r as routes, v as vehicle, "
			+ "REDUCE(length=0, rel in r | length + rel.length) as sumLength ORDER BY sumLength ASC LIMIT 1 ")
	Path getPathForVehicle(@Param("startCityId") Long startCityId, @Param("endCityId") Long endCityId,
			@Param("vehicleId") Long vehicleId);

}

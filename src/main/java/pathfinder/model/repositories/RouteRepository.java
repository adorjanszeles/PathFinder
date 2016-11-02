package pathfinder.model.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Route;

@Repository
public interface RouteRepository extends GraphRepository<Route> {

	@Query("MATCH (v:Vehicle), (c1:City)-[r:ROUTE*]->(c2:City) "
			+ "WHERE ID(c1)={startCityId} AND ID(c2)={endCityId} AND ID(v)={vehicleId} "
			+ "AND ALL(x in r WHERE x.maxHeight >= v.height AND x.maxWidth >= v.width AND x.maxLength >= v.length AND x.maxWeight >= v.weight)"
			+ " RETURN c1 as start, c2 as end, r as routes,"
			+ " reduce(length=0, rel in r | length + rel.length) as sumLength order by sumLength asc limit 1 ")
	Path getPathForVehicle(@Param("startCityId") Long startCityId, @Param("endCityId") Long endCityId,
			@Param("vehicleId") Long vehicleId);
	
}

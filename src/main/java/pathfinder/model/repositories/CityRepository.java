package pathfinder.model.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.City;

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

}

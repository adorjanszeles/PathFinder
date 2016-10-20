package pathfinder.model.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.City;

@Repository
public interface CityRepository extends GraphRepository<City> {
	//
}

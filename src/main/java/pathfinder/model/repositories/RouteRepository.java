package pathfinder.model.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.Route;

@Repository
public interface RouteRepository extends GraphRepository<Route> {

}

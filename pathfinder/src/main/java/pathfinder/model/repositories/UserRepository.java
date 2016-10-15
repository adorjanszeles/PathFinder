package pathfinder.model.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import pathfinder.model.entities.User;

public interface UserRepository extends GraphRepository<User> {

	
	
}

package pathfinder.model.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.User;

/**
 * {@link User} node kezelésére szolgáló repository interface.
 * 
 * @author Kiss László
 *
 */
@Repository
public interface UserRepository extends GraphRepository<User> {
	//
}

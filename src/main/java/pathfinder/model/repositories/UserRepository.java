package pathfinder.model.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pathfinder.common.RoleEnum;
import pathfinder.model.nodes.User;

import java.util.List;

/**
 * {@link User} node kezelésére szolgáló repository interface.
 * 
 * @author Kiss László
 *
 */
@Repository
public interface UserRepository extends GraphRepository<User> {

	/**
	 * Felhasználó keresése név alapján
	 * 
	 * @param userName
	 * @return
	 */
	@Query("MATCH (u:User) WHERE u.name={userName} RETURN u")
	User findByUserName(@Param("userName") String userName);

	/**
	 * Felhasználók keresése név, email vagy role alapján.
	 * 
	 * @param name
	 * @param email
	 * @param roles
	 * @return
	 */
	@Query("MATCH (u:User) WHERE u.name=~{name} AND u.email=~{email} AND u.role IN {roles} RETURN u")
	List<User> searchUsers(@Param("name") String name, @Param("email") String email,
			@Param("roles") List<RoleEnum> roles);
}

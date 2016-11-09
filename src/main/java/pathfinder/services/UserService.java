package pathfinder.services;

import java.util.List;

import pathfinder.model.nodes.User;

/**
 * Userek kezelésére szolgáló Service.
 * 
 * @author Kiss László
 *
 */
public interface UserService {

	/**
	 * User törlése.
	 * 
	 * @param userId
	 */
	void deleteUser(Long userId);

	/**
	 * User keresése id alapján.
	 * 
	 * @param userId
	 * @return
	 */
	User findById(Long userId);

	/**
	 * User keresése név alapján.
	 * 
	 * @param userName
	 * @return
	 */
	User findByUserName(String userName);

	/**
	 * Összes user lekérdezése.
	 * 
	 * @return
	 */
	List<User> getAllUser();

	/**
	 * Bejelentkezett user lekérdezése.
	 * 
	 * @return
	 */
	User getLoggedInUser();

	/**
	 * User módosítása.
	 * 
	 * @param userId
	 * @param user
	 * @return
	 */
	User modifyUser(Long userId, User user);

	/**
	 * User mentése.
	 * 
	 * @param user
	 * @return
	 */
	User saveUser(User user);

	/**
	 * User keresése név, email vagy role alapján.
	 * 
	 * @param searchUserEntity
	 * @return
	 */
	List<User> searchUserByParams(User searchUserEntity);

}

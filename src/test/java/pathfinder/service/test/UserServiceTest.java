package pathfinder.service.test;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import pathfinder.common.RoleEnum;
import pathfinder.exceptions.badrequest.UserBadRequestException;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.exceptions.unauthorized.UnauthorizedException;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;

/**
 * Felhasználókat kezelő Service tesztelése.
 * 
 * @author Kiss László
 *
 */
public class UserServiceTest extends PathfinderServiceTestBase {

	@Autowired
	UserService userService;

	/**
	 * Nem létező felhasználó törlése.
	 */
	@Test(expected = UserNotFoundException.class)
	public void deleteNotExistingUserTest() {
		this.userService.deleteUser(Long.MAX_VALUE);
	}

	/**
	 * Felhasználó törlése.
	 */
	@Test
	public void deleteTest() {
		User newUser = this.createNewUser();
		Long userId = newUser.getUserId();
		this.userService.deleteUser(userId);
		Assert.isNull(this.userRepository.findOne(userId));
	}

	/**
	 * Összes felhasználó lekérdezése.
	 */
	@Test
	public void findAllUserTest() {
		User firstUser = this.createNewUser();
		User secondUser = this.createNewUser();
		List<User> foundUsers = this.userService.getAllUser();
		Assert.notNull(foundUsers);
		Assert.isTrue(!foundUsers.isEmpty());
		List<Long> foundUserIds = foundUsers.stream().map(user -> user.getUserId()).collect(Collectors.toList());
		Assert.isTrue(foundUserIds.contains(firstUser.getUserId()) && foundUserIds.contains(secondUser.getUserId()));
	}

	/**
	 * Felhasználó keresése nem létező id-val.
	 */
	@Test(expected = UserNotFoundException.class)
	public void findByIdNotFoundTest() {
		this.userService.findById(Long.MAX_VALUE);
	}

	/**
	 * Azonosító alapú keresés.
	 */
	@Test
	public void findByIdTest() {
		User newUser = this.createNewUser();
		User foundUser = this.userService.findById(newUser.getUserId());
		Assert.notNull(foundUser);
		Assert.notNull(foundUser.getUserId());
		Assert.isTrue(foundUser.getUserId().equals(newUser.getUserId()));
	}

	/**
	 * Felhasználó keresése név alapján.
	 */
	@Test
	public void findByUserNameTest() {
		User newUser = this.createNewUser();
		User foundUser = this.userService.findByUserName(newUser.getName());
		Assert.isTrue(newUser.getUserId().equals(foundUser.getUserId()));
	}

	/**
	 * Bejelentkezett felhasználó elkérése.
	 */
	@Test
	public void getLoggedInUserTest() {
		User loggedInUser = this.userService.getLoggedInUser();
		Assert.isTrue(this.loggedInUser.getUserId().equals(loggedInUser.getUserId()));
	}

	/**
	 * Nem létező felhasználó módosítása.
	 */
	@Test(expected = UserNotFoundException.class)
	public void modifyNotExistingUserTest() {
		this.userService.modifyUser(Long.MAX_VALUE, this.createNewUser());
	}

	/**
	 * User módosítása hibás email címmel.
	 */
	@Test(expected = UserBadRequestException.class)
	public void modifyUserBadRequestTest() {
		User newUser = this.createNewUser();
		User modifiedUser = new User();
		modifiedUser.setAge(newUser.getAge());
		modifiedUser.setName(newUser.getName());
		modifiedUser.setPassword(newUser.getPassword());
		modifiedUser.setRole(newUser.getRole());
		modifiedUser.setEmail(null);
		this.userService.modifyUser(newUser.getUserId(), modifiedUser);
	}

	/**
	 * Felhasználó módosítása.
	 */
	@Test
	public void modifyUserTest() {
		User newUser = this.createNewUser();
		User modifiedUser = new User();
		modifiedUser.setAge(newUser.getAge());
		modifiedUser.setName(newUser.getName());
		modifiedUser.setPassword(newUser.getPassword());
		modifiedUser.setRole(newUser.getRole());
		String modifiedEmail = "modified@email.hu";
		modifiedUser.setEmail(modifiedEmail);
		modifiedUser = this.userService.modifyUser(newUser.getUserId(), modifiedUser);
		Assert.notNull(modifiedUser);
		Assert.isTrue(modifiedEmail.equals(modifiedUser.getEmail()));
	}

	/**
	 * Jogosulatlan módosítás.
	 */
	@Test(expected = UnauthorizedException.class)
	public void modifyUserUnauthorizedTest() {
		this.authenticateAsUser();
		User newUser = this.createNewUser();
		User modifiedUser = new User();
		modifiedUser.setAge(newUser.getAge());
		modifiedUser.setName(newUser.getName());
		modifiedUser.setPassword(newUser.getPassword());
		modifiedUser.setRole(newUser.getRole());
		modifiedUser.setEmail("modified@email.hu");
		this.userService.modifyUser(newUser.getUserId(), modifiedUser);
	}

	/**
	 * Mentés név nélkül.
	 */
	@Test(expected = UserBadRequestException.class)
	public void saveBadRequestTest() {
		User user = new User();
		user.setAge(25);
		user.setEmail("test@test.hu");
		user.setPassword("testpassword");
		this.userService.saveUser(user);
	}

	/**
	 * Felhasználó létrehozása.
	 */
	@Test
	public void saveTest() {
		User user = new User();
		user.setAge(25);
		user.setEmail("test@test.hu");
		user.setName("TestName");
		user.setPassword("testpassword");
		User persistedUser = this.userService.saveUser(user);
		Assert.notNull(persistedUser);
		Assert.notNull(persistedUser.getUserId());
	}

	/**
	 * Keresés.
	 */
	@Test
	public void searchUserTest() {
		User firstUser = new User();
		String name = "searchTestName";
		firstUser.setName(name);
		firstUser.setAge(20);
		firstUser.setEmail("first@email.hu");
		firstUser.setPassword("password");
		firstUser.setRole(RoleEnum.ROLE_USER);
		firstUser = this.userRepository.save(firstUser);

		User secondUser = new User();
		secondUser.setName("secondUserName");
		secondUser.setAge(20);
		String email = "searchEmail";
		secondUser.setEmail(email);
		secondUser.setPassword("password");
		secondUser.setRole(RoleEnum.ROLE_USER);
		secondUser = this.userRepository.save(secondUser);

		User thirdUser = new User();
		thirdUser.setName("thirdUserName");
		thirdUser.setAge(20);
		thirdUser.setEmail("third@email.hu");
		thirdUser.setPassword("password");
		thirdUser.setRole(RoleEnum.ROLE_ADMIN);
		thirdUser = this.userRepository.save(thirdUser);

		User searchUser = new User();
		searchUser.setName(name);

		List<User> foundUsers = this.userService.searchUserByParams(searchUser);
		Assert.notNull(foundUsers);
		List<Long> foundUserIds = foundUsers.stream().map(user -> user.getUserId()).collect(Collectors.toList());
		Assert.isTrue(foundUserIds.contains(firstUser.getUserId()));

		searchUser.setName(null);
		searchUser.setEmail(email);

		foundUsers = this.userService.searchUserByParams(searchUser);
		Assert.notNull(foundUsers);
		foundUserIds = foundUsers.stream().map(user -> user.getUserId()).collect(Collectors.toList());
		Assert.isTrue(foundUserIds.contains(secondUser.getUserId()));

		searchUser.setEmail(null);
		searchUser.setRole(RoleEnum.ROLE_ADMIN);

		foundUsers = this.userService.searchUserByParams(searchUser);
		Assert.notNull(foundUsers);
		foundUserIds = foundUsers.stream().map(user -> user.getUserId()).collect(Collectors.toList());
		Assert.isTrue(foundUserIds.contains(thirdUser.getUserId()));
	}

}

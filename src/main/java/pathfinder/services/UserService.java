package pathfinder.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import pathfinder.common.RoleEnum;
import pathfinder.exceptions.badrequest.UserBadRequestException;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.model.repositories.UserRepository;

/**
 * {@link User} node kezelésére szolgáló Service osztály.
 * 
 * @author Kiss László
 *
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void deleteUser(Long userId) {
		User persistedUser = this.userRepository.findOne(userId);
		if (persistedUser == null) {
			throw new UserNotFoundException();
		}
		this.userRepository.delete(persistedUser);
	}

	private User doSaveUser(User persistedUser, User userFromUI) {
		persistedUser.setAge(userFromUI.getAge());
		persistedUser.setEmail(userFromUI.getEmail());
		persistedUser.setName(userFromUI.getName());
		persistedUser.setPassword(userFromUI.getPassword());
		persistedUser.setRole(userFromUI.getRole());
		return this.userRepository.save(persistedUser);
	}

	public User findById(Long userId) {
		User result = this.userRepository.findOne(userId);
		if (result == null) {
			throw new UserNotFoundException();
		}
		return result;
	}

	public User findByUserName(String userName) {
		// TODO itt kellene a db-ből lekérni a user-t. Meg valahogy bele kéne
		// tenni egy init scriptel az admin-t.
		return this.userRepository.findByUserName(userName);
	}

	public List<User> getAllUser() {
		List<User> result = new ArrayList<User>();
		Iterator<User> iterator = this.userRepository.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public User modifyUser(Long userId, User user) {
		this.validateUser(user);
		User persistedUser = this.userRepository.findOne(userId);
		if (persistedUser == null) {
			throw new UserNotFoundException();
		}
		return this.doSaveUser(persistedUser, user);
	}

	public User saveUser(User user) {
		user.setRole(RoleEnum.ROLE_USER);
		this.validateUser(user);
		User persistedUser = new User();
		return this.doSaveUser(persistedUser, user);
	}

	public List<User> searchUserByParams(User searchUserEntity) {
		// TODO keresés a név, email cím és role alapján
		String name = searchUserEntity.getName() != null ? searchUserEntity.getName() : ".*";
		String email = searchUserEntity.getEmail() != null ? searchUserEntity.getEmail() : ".*";
		List<RoleEnum> roles = searchUserEntity.getRole() != null ? Arrays.asList(searchUserEntity.getRole())
				: Arrays.asList(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_USER);
		return this.userRepository.searchUsers(name, email, roles);
	}

	private void validateUser(User user) {
		if (user.getRole() == null || StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getName())
				|| user.getRole() == null) {
			throw new UserBadRequestException();
		}
	}
}

package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pathfinder.common.RoleEnum;
import pathfinder.exceptions.UserNotFoundException;
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

	public User findById(Long userId) {
		User result = this.userRepository.findOne(userId);
		if (result == null) {
			throw new UserNotFoundException();
		}
		return result;
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
		User persistedUser = this.userRepository.findOne(userId);
		if (persistedUser == null) {
			throw new UserNotFoundException();
		}
		// TODO validációk
		persistedUser.setAge(user.getAge());
		persistedUser.setEmail(user.getEmail());
		persistedUser.setName(user.getName());
		persistedUser.setPassword(user.getPassword());
		return this.userRepository.save(persistedUser);
	}

	public User saveUser(User user) {
		user.setRole(RoleEnum.USER);
		// TODO validációk
		return this.userRepository.save(user);
	}

}

package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pathfinder.model.nodes.User;
import pathfinder.model.repositories.UserRepository;

@Service
@Transactional(readOnly = false,rollbackFor=Exception.class)
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void saveUser(User user) {
		userRepository.save(user);
	}

	public User findById(Long userId) {
		return userRepository.findOne(userId);
	}
	
	public List<User> getAllUser() {
		List<User> result = new ArrayList<User>();
		Iterator<User> iterator = userRepository.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}
	
	public User modifyUser(User user) {
		User persistedUser = this.userRepository.findOne(user.getUserId());
		persistedUser.setAge(user.getAge());
		persistedUser.setEmail(user.getEmail());
		persistedUser.setName(user.getName());
		persistedUser.setPassword(user.getPassword());
		return userRepository.save(persistedUser);
	}
	
	public void deleteUser(User user) {
		User persistedUser = userRepository.findOne(user.getUserId());
		Assert.notNull(persistedUser);
		userRepository.delete(persistedUser);
	}
	
}

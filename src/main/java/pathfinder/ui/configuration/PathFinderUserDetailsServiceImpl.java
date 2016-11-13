package pathfinder.ui.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;

import java.util.List;

/**
 * Spring security user service. Innen tölti be a spring a usereket.
 * 
 * @author Széles Adorján Date: 2016. 11. 03.
 */
public class PathFinderUserDetailsServiceImpl implements UserDetailsService {
	private UserService userService;

	public PathFinderUserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.findByUserName(userName);
		if (user == null) {
			throw new UserNotFoundException();
		}
		String role = user.getRole().name();
		String username = user.getName();
		String password = user.getPassword();
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
		return new org.springframework.security.core.userdetails.User(username, password, authorities);
	}
}

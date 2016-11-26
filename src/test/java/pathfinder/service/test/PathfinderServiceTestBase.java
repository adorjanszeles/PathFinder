package pathfinder.service.test;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pathfinder.application.Application;
import pathfinder.common.RoleEnum;
import pathfinder.config.PathFinderNeo4jConfiguration;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.model.repositories.CityRepository;
import pathfinder.model.repositories.RouteRepository;
import pathfinder.model.repositories.UserRepository;
import pathfinder.model.repositories.VehicleRepository;

@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class PathfinderServiceTestBase {

	@Autowired
	protected CityRepository cityRepository;

	protected User loggedInUser;

	@Autowired
	PathFinderNeo4jConfiguration pathfinderNeo4jConfiguration;

	@Autowired
	protected RouteRepository routeRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected VehicleRepository vehicleRepository;

	protected void authenticateAsAdmin() {
		User user = new User();
		user.setAge(25);
		user.setEmail("test@test.hu");
		user.setName("TestAdmin");
		user.setPassword("testpassword");
		user.setRole(RoleEnum.ROLE_ADMIN);
		this.userRepository.save(user);
		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRole().getName());
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		this.loggedInUser = user;
	}

	protected void authenticateAsUser() {
		User user = new User();
		user.setAge(25);
		user.setEmail("test@test.hu");
		user.setName("TestUser");
		user.setPassword("testpassword");
		user.setRole(RoleEnum.ROLE_USER);
		this.userRepository.save(user);
		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRole().getName());
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		this.loggedInUser = user;
	}

	protected City createNewCity() {
		City city = new City();
		city.setName("TestCityName");
		return this.cityRepository.save(city);
	}

	protected Route createNewRoute() {
		Route route = new Route();
		route.setLength(100L);
		route.setMaxHeight(100L);
		route.setMaxLength(100L);
		route.setMaxWeight(100L);
		route.setMaxWidth(100L);
		route.setName("TestRouteName");
		route.setDestinationCity(this.createNewCity());
		route.setStartingCity(this.createNewCity());
		return this.routeRepository.save(route);
	}

	protected User createNewUser() {
		User user = new User();
		user.setAge(25);
		user.setEmail("test@test.hu");
		user.setName("TestName");
		user.setPassword("testpassword");
		user.setRole(RoleEnum.ROLE_USER);
		return this.userRepository.save(user);
	}

	protected Vehicle createNewVehicle() {
		Vehicle vehicle = new Vehicle();
		vehicle.setHeight(100L);
		vehicle.setLength(100L);
		vehicle.setPlateNumber("ABC123");
		vehicle.setWeight(100L);
		vehicle.setWidth(100L);
		vehicle.setOwner(this.createNewUser());
		return this.vehicleRepository.save(vehicle);
	}

	@Before
	public void setUp() {
		try {
			this.pathfinderNeo4jConfiguration.getSession().query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r",
					new HashMap<>());
			this.authenticateAsAdmin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			this.pathfinderNeo4jConfiguration.getSession().query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r",
					new HashMap<>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

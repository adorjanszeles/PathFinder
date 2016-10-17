package pathfinder.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pathfinder.config.PfNeo4jConfiguration;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;

@Configuration
@Import(PfNeo4jConfiguration.class)
@RequestMapping("/")
public class PathfinderController extends WebMvcConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) throws IOException {
        SpringApplication.run(PathfinderController.class, args);
    }
	
	@RequestMapping(value= "/hello", method = RequestMethod.GET, produces = {"application/json"} )
	public @ResponseBody String printHello() {
		return "hello";
	}
	
	@RequestMapping(value= "/user", method = RequestMethod.GET, produces = {"application/json"} )
	public @ResponseBody List<User> getUsers() {
		return userService.getAllUser();
	}
	
	@RequestMapping(value="/user", method = RequestMethod.POST, consumes = {"application/json"})
	public @ResponseBody void saveUser(@RequestBody User user) {
		userService.saveUser(user);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = {"application/json"})
	public @ResponseBody User modifyUser(@RequestBody User user) {
		return userService.modifyUser(user);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.DELETE, consumes = {"application/json"})
	public @ResponseBody void deleteUser(@RequestBody User user) {
		userService.deleteUser(user);
	}

}

package pathfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pathfinder.config.PfNeo4jConfiguration;
import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.UserService;
import pathfinder.services.VehicleService;

import java.util.List;

@Configuration
@Import(PfNeo4jConfiguration.class)
@RequestMapping("/")
public class PathfinderController extends WebMvcConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleService vehicleService;
	
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
	
	@RequestMapping(value= "/vehicle", method = RequestMethod.GET, produces = {"application/json"} )
	public @ResponseBody List<Vehicle> getVehicles() {
		return vehicleService.getAllVehicles();
	}
	
	@RequestMapping(value="/vehicle", method = RequestMethod.POST, consumes = {"application/json"})
	public @ResponseBody void saveVehicle(@RequestBody Vehicle vehicle) {
		vehicleService.saveVehicle(vehicle);
	}
	
	@RequestMapping(value = "/vehicle", method = RequestMethod.PUT, consumes = {"application/json"})
	public @ResponseBody Vehicle modifyVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.modifyVehicle(vehicle);
	}
	
	@RequestMapping(value = "/vehicle", method = RequestMethod.DELETE, consumes = {"application/json"})
	public @ResponseBody void deleteVehicle(@RequestBody Vehicle vehicle) {
		vehicleService.deleteVehicle(vehicle);
	}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:index.jsf";
    }

    @RequestMapping(value = "/starter", method = RequestMethod.GET)
    public String redirectTOStart() {
        return "redirect:content/starter.jsf";
    }

}

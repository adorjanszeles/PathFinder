package pathfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
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

@Configuration
@Import(PfNeo4jConfiguration.class)
@RequestMapping("/")
public class PathfinderController extends WebMvcConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private VehicleService vehicleService;

	/**
	 * Felhasználó törlése. TODO Csak admin joggal!
	 * @param user
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}

	/**
	 * Jármű törlése
	 * @param vehicle
	 */
	@RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
		this.vehicleService.deleteVehicle(vehicleId);
	}

	/**
	 * Egy felhasználó adatainak lekérdezése.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody User getUser(@PathVariable("userId") Long userId) {
		return this.userService.findById(userId);
	}

	/**
	 * Összes felhasználó lekérdezése.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<User> getUsers() {
		return this.userService.getAllUser();
	}

	/**
	 * Egy jármű adatainak lekérdezése. TODO csak saját jármű vagy admin jog
	 * 
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Vehicle getVehicle(@PathVariable("vehicleId") Long vehicleId) {
		return this.vehicleService.getVehicleById(vehicleId);
	}

	/**
	 * Összes jármű lekérdezése
	 * 
	 * @return
	 */
	@RequestMapping(value = "/vehicle", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Vehicle> getVehicles() {
		// Csak admin joggal!!
		return this.vehicleService.getAllVehicles();
	}

	/**
	 * 1 User-hez tartozó járművek lekérdezése TODO: csak saját járművek vagy admin jog
	 * 
	 * @param vehicle
	 */
	@RequestMapping(value = "/user/{userId}/vehicles", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Vehicle> getVehiclesOfUser(@PathVariable("userId") Long userId) {
		return this.vehicleService.getVehiclesOfUser(userId);
	}

	/**
	 * Felhasználó adatainak módosítása.
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = { "application/json" })
	public @ResponseBody User modifyUser(@RequestBody User user) {
		// Kezelni kell, hogy csak admin joggal lehet más usert módosítani!
		return this.userService.modifyUser(user);
	}

	/**
	 * Jármű módosítása TODO validáció: vagy a bejelentkezett felhasználó az owner, vagy
	 * adminhoz tartozik!
	 * 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value = "/vehicle", method = RequestMethod.PUT, consumes = { "application/json" })
	public @ResponseBody Vehicle modifyVehicle(@RequestBody Vehicle vehicle) {
		return this.vehicleService.modifyVehicle(vehicle);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToWelcomePage() {
		return "redirect:login/login.jsf";
	}

	/**
	 * Felhasználó mentése.
	 * 
	 * @param user
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = { "application/json" })
	public @ResponseBody void saveUser(@RequestBody User user) {
		this.userService.saveUser(user);
	}

	/**
	 * Új jármű felvétele TODO a tulajdonos felületről jön, vagy a bejelentkezett
	 * felhasználó lesz?
	 * 
	 * @param vehicle
	 */
	@RequestMapping(value = "/vehicle", method = RequestMethod.POST, consumes = { "application/json" })
	public @ResponseBody void saveVehicle(@RequestBody Vehicle vehicle) {
		this.vehicleService.saveVehicle(vehicle);
	}

}

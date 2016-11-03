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
import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Route;
import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.CityService;
import pathfinder.services.PathFinderService;
import pathfinder.services.RouteService;
import pathfinder.services.UserService;
import pathfinder.services.VehicleService;
import pathfinder.ui.configuration.SpringSecurityConfiguration;

@Configuration
@Import(value = { PfNeo4jConfiguration.class, SpringSecurityConfiguration.class })
@RequestMapping("/")
public class PathfinderController extends WebMvcConfigurerAdapter {

	@Autowired
	private CityService cityService;

	@Autowired
	private PathFinderService pathfinderService;

	@Autowired
	private RouteService routeService;

	@Autowired
	private UserService userService;

	@Autowired
	private VehicleService vehicleService;

	/**
	 * Város törlése TODO csak admin joggal
	 * 
	 * @param cityId
	 */
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteCity(@PathVariable("cityId") Long cityId) {
		this.cityService.deleteCity(cityId);
	}

	/**
	 * Útvonal törlése TODO csak admin joggal
	 * 
	 * @param routeId
	 */
	@RequestMapping(value = "/route/{routeId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteRoute(@PathVariable("routeId") Long routeId) {
		this.routeService.deleteRoute(routeId);
	}

	/**
	 * Felhasználó törlése. TODO Csak admin joggal!
	 * 
	 * @param user
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}

	/**
	 * Jármű törlése
	 * 
	 * @param vehicle
	 */
	@RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
		this.vehicleService.deleteVehicle(vehicleId);
	}

	/**
	 * Összes város lekérdezése.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/city", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<City> getCities() {
		return this.cityService.getAllCities();
	}

	/**
	 * Egy város és a hozzá tartozó utak lekérdezése.
	 * 
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody City getCityWithRoutes(@PathVariable("cityId") Long cityId) {
		return this.cityService.getCityWithRoutes(cityId);
	}

	/**
	 * Útvonal keresése
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "/path", method = RequestMethod.POST)
	public @ResponseBody Path getPath(@RequestBody Path path) throws NoPathForThisParameters {
		return this.pathfinderService.getPath(path.getStart(), path.getEnd(), path.getVehicle());
	}

	/**
	 * Összes út lekérdezése.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/route", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Route> getRoutes() {
		return this.routeService.getAllRoute();
	}

	/**
	 * Egy út és a hozzá tartozó városok lekérdezése.
	 * 
	 * @param routeId
	 * @return
	 */
	@RequestMapping(value = "/route/{routeId}", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Route getRouteWithCities(@PathVariable("routeId") Long routeId) {
		return this.routeService.getRouteWithCities(routeId);
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
	 * 1 User-hez tartozó járművek lekérdezése TODO: csak saját járművek vagy
	 * admin jog
	 * 
	 * @param vehicle
	 */
	@RequestMapping(value = "/user/{userId}/vehicles", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Vehicle> getVehiclesOfUser(@PathVariable("userId") Long userId) {
		return this.vehicleService.getVehiclesOfUser(userId);
	}

	/**
	 * Város módosítása TODO csak admin joggal
	 * 
	 * @param cityId
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public @ResponseBody City modifyCity(@PathVariable("cityId") Long cityId, @RequestBody City city) {
		return this.cityService.modifyCity(cityId, city);
	}

	/**
	 * Útvonal módosítása TODO csak admin joggal
	 * 
	 * @param routeId
	 * @param route
	 * @return
	 */
	@RequestMapping(value = "/route/{routeId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public @ResponseBody Route modifyRoute(@PathVariable("routeId") Long routeId, @RequestBody Route route) {
		return this.routeService.modifyRoute(routeId, route);
	}

	/**
	 * Felhasználó adatainak módosítása.
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public @ResponseBody User modifyUser(@PathVariable("userId") Long userId, @RequestBody User user) {
		// Kezelni kell, hogy csak admin joggal lehet más usert módosítani!
		return this.userService.modifyUser(userId, user);
	}

	/**
	 * Jármű módosítása TODO validáció: vagy a bejelentkezett felhasználó az
	 * owner, vagy adminhoz tartozik!
	 * 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public @ResponseBody Vehicle modifyVehicle(@PathVariable("vehicleId") Long vehicleId,
			@RequestBody Vehicle vehicle) {
		return this.vehicleService.modifyVehicle(vehicleId, vehicle);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToWelcomePage() {
		return "redirect:login/login.jsf";
	}

	/**
	 * Új város felvétele TODO csak admin joggal
	 * 
	 * @param city
	 */
	@RequestMapping(value = "/city", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public @ResponseBody City saveCity(@RequestBody City city) {
		return this.cityService.saveCity(city);
	}

	/**
	 * Új útvonal mentése TODO csak admin joggal
	 * 
	 * @param route
	 */
	@RequestMapping(value = "/route", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public @ResponseBody Route saveRoute(@RequestBody Route route) {
		return this.routeService.saveRoute(route);
	}

	/**
	 * Felhasználó mentése.
	 * 
	 * @param user
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public @ResponseBody User saveUser(@RequestBody User user) {
		return this.userService.saveUser(user);
	}

	/**
	 * Új jármű felvétele TODO a tulajdonos felületről jön, vagy a
	 * bejelentkezett felhasználó lesz?
	 * 
	 * @param vehicle
	 */
	@RequestMapping(value = "/vehicle", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public @ResponseBody Vehicle saveVehicle(@RequestBody Vehicle vehicle) {
		return this.vehicleService.saveVehicle(vehicle);
	}

}

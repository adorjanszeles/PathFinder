package pathfinder.service.test;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import pathfinder.exceptions.badrequest.RouteBadRequestException;
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.exceptions.notfound.RouteNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.services.RouteService;

/**
 * Útvonalakat kezelő Service tesztelése.
 * 
 * @author Kiss László
 *
 */
public class RouteServiceTest extends PathfinderServiceTestBase {

	@Autowired
	RouteService routeService;

	/**
	 * Nem létező útvonal törlése.
	 */
	@Test(expected = RouteNotFoundException.class)
	public void deleteNotExistingRouteTest() {
		this.routeService.deleteRoute(Long.MAX_VALUE);
	}

	/**
	 * Útvonal törlése.
	 */
	@Test
	public void deleteRouteTest() {
		Route newRoute = this.createNewRoute();
		Long routeId = newRoute.getRouteId();
		this.routeService.deleteRoute(routeId);
		Assert.isNull(this.routeRepository.findOne(routeId));
	}

	/**
	 * Összes útvonal lekérdezése.
	 */
	@Test
	public void getAllRouteTest() {
		Route firstRoute = this.createNewRoute();
		Route secondRoute = this.createNewRoute();
		List<Route> foundRoutes = this.routeService.getAllRoute();
		Assert.notNull(foundRoutes);
		List<Long> routeIds = foundRoutes.stream().map(route -> route.getRouteId()).collect(Collectors.toList());
		Assert.isTrue(routeIds.contains(firstRoute.getRouteId()) && routeIds.contains(secondRoute.getRouteId()));

	}

	/**
	 * Város és a hozzá tartozó útvonalak lekérdezése.
	 */
	@Test
	public void getCityWithRoutesTest() {
		Route newRoute = this.createNewRoute();
		City startingCity = this.routeService.getCityWithRoutes(newRoute.getStartingCity().getCityId());
		Assert.notNull(startingCity);
		Assert.isTrue(startingCity.getRoutesFromCity().stream().map(route -> route.getRouteId())
				.collect(Collectors.toList()).contains(newRoute.getRouteId()));
		City destinationCity = this.routeService.getCityWithRoutes(newRoute.getDestinationCity().getCityId());
		Assert.notNull(destinationCity);
		Assert.isTrue(destinationCity.getRoutesToCity().stream().map(route -> route.getRouteId())
				.collect(Collectors.toList()).contains(newRoute.getRouteId()));
	}

	/**
	 * Nem létező városhoz tartozó útvonalak lekérdezése.
	 */
	@Test(expected = CityNotFoundException.class)
	public void getNotExistingCityWithRoutesTest() {
		this.routeService.getCityWithRoutes(Long.MAX_VALUE);
	}

	/**
	 * Nem létező útvonal lekérdezése.
	 */
	@Test(expected = RouteNotFoundException.class)
	public void getNotExistingRouteWithCitiesTest() {
		this.routeService.getRouteWithCities(Long.MAX_VALUE);
	}

	/**
	 * Útvonal lekérdezése a hozzá tartozó városokkal.
	 */
	@Test
	public void getRouteWithCitiesTest() {
		Route newRoute = this.createNewRoute();
		Route foundRoute = this.routeService.getRouteWithCities(newRoute.getRouteId());
		Assert.notNull(foundRoute);
		Assert.notNull(foundRoute.getStartingCity());
		Assert.notNull(foundRoute.getDestinationCity());
		Assert.isTrue(newRoute.getRouteId().equals(foundRoute.getRouteId()));
		Assert.isTrue(newRoute.getStartingCity().getCityId().equals(foundRoute.getStartingCity().getCityId()));
		Assert.isTrue(newRoute.getDestinationCity().getCityId().equals(foundRoute.getDestinationCity().getCityId()));
	}

	/**
	 * Nem létező útvonal módosítása.
	 */
	@Test(expected = RouteNotFoundException.class)
	public void modifyNotExistingRouteTest() {
		this.routeService.modifyRoute(Long.MAX_VALUE, this.createNewRoute());
	}

	/**
	 * Útvonal módosítása rossz adatokra.
	 */
	@Test(expected = RouteBadRequestException.class)
	public void modifyRouteBadRequestTest() {
		Route newRoute = this.createNewRoute();
		newRoute.setName(null);
		this.routeService.modifyRoute(newRoute.getRouteId(), newRoute);
	}

	/**
	 * Útvonal módosítása.
	 */
	@Test
	public void modifyRouteTest() {
		Route newRoute = this.createNewRoute();
		String name = "modifiedName";
		newRoute.setName(name);
		this.routeService.modifyRoute(newRoute.getRouteId(), newRoute);
		Route modifiedRoute = this.routeRepository.findOne(newRoute.getRouteId());
		Assert.notNull(modifiedRoute);
		Assert.isTrue(name.equals(modifiedRoute.getName()));
	}

	/**
	 * Új útvonal mentése rossz adatokkal.
	 */
	@Test(expected = RouteBadRequestException.class)
	public void saveRouteBadRequestTest() {
		Route newRoute = new Route();
		this.routeService.saveRoute(newRoute);
	}

	/**
	 * Új útvonal mentése.
	 */
	@Test
	public void saveRouteTest() {
		Route newRoute = new Route();
		newRoute.setDestinationCity(this.createNewCity());
		newRoute.setLength(100L);
		newRoute.setMaxHeight(100L);
		newRoute.setMaxLength(100L);
		newRoute.setMaxWeight(100L);
		newRoute.setMaxWidth(100L);
		newRoute.setName("TestName");
		newRoute.setStartingCity(this.createNewCity());
		Route persistedRoute = this.routeService.saveRoute(newRoute);
		Assert.notNull(persistedRoute);
		Assert.notNull(persistedRoute.getRouteId());
	}

	/**
	 * Útvonal keresése név alapján.
	 */
	@Test
	public void searchRouteTest() {
		Route newRoute = this.createNewRoute();
		String name = newRoute.getName();
		Route searchRoute = new Route();
		searchRoute.setName(name);
		List<Route> foundRoutes = this.routeService.searchRouteByParams(searchRoute);
		Assert.isTrue(foundRoutes.stream().map(route -> route.getRouteId()).collect(Collectors.toList())
				.contains(newRoute.getRouteId()));
	}

}

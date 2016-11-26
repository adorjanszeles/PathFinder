package pathfinder.service.test;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Route;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.PathFinderService;

/**
 * Útkeresésre szolgáló Service tesztelése
 * 
 * @author Kiss László
 */
public class PathFinderServiceTest extends PathfinderServiceTestBase {

	@Autowired
	PathFinderService pathfinderService;

	/**
	 * Legrövidebb út keresése, nem létező úttal.
	 * 
	 * @throws NoPathForThisParameters
	 */
	@Test(expected = NoPathForThisParameters.class)
	public void findNoPathTest() throws NoPathForThisParameters {
		// Jármű
		Vehicle vehicle = this.createNewVehicle();

		// Kiinduló város
		City startingCity = new City();
		startingCity.setName("A");
		this.cityRepository.save(startingCity);

		// Cél város
		City endCity = new City();
		endCity.setName("B");
		this.cityRepository.save(endCity);

		Route routeA = new Route();
		routeA.setDestinationCity(endCity);
		routeA.setStartingCity(startingCity);
		routeA.setLength(5L);
		routeA.setMaxHeight(50L);
		routeA.setMaxLength(100L);
		routeA.setMaxWeight(100L);
		routeA.setMaxWidth(100L);
		routeA.setName("Route A");
		this.routeRepository.save(routeA);

		this.pathfinderService.getPath(startingCity, endCity, vehicle);
	}

	/**
	 * Legrövidebb út keresése.
	 * 
	 * @throws NoPathForThisParameters
	 */
	@Test
	public void findPathTest() throws NoPathForThisParameters {
		// Jármű
		Vehicle vehicle = this.createNewVehicle();

		// Kiinduló város
		City startingCity = new City();
		startingCity.setName("A");
		this.cityRepository.save(startingCity);

		// Cél város
		City endCity = new City();
		endCity.setName("B");
		this.cityRepository.save(endCity);

		Route routeA = new Route();
		routeA.setDestinationCity(endCity);
		routeA.setStartingCity(startingCity);
		routeA.setLength(5L);
		routeA.setMaxHeight(50L);
		routeA.setMaxLength(100L);
		routeA.setMaxWeight(100L);
		routeA.setMaxWidth(100L);
		routeA.setName("Route A");
		this.routeRepository.save(routeA);

		City cityC = new City();
		cityC.setName("C");
		this.cityRepository.save(cityC);

		Route routeB = new Route();
		routeB.setDestinationCity(cityC);
		routeB.setStartingCity(startingCity);
		routeB.setLength(10L);
		routeB.setMaxHeight(100L);
		routeB.setMaxLength(100L);
		routeB.setMaxWeight(100L);
		routeB.setMaxWidth(100L);
		routeB.setName("Route B");
		this.routeRepository.save(routeB);

		Route routeC = new Route();
		routeC.setDestinationCity(endCity);
		routeC.setStartingCity(cityC);
		routeC.setLength(10L);
		routeC.setMaxHeight(100L);
		routeC.setMaxLength(100L);
		routeC.setMaxWeight(100L);
		routeC.setMaxWidth(100L);
		routeC.setName("Route C");
		this.routeRepository.save(routeC);

		City cityD = new City();
		cityD.setName("D");
		this.cityRepository.save(cityD);

		Route routeD = new Route();
		routeD.setDestinationCity(cityD);
		routeD.setStartingCity(startingCity);
		routeD.setLength(20L);
		routeD.setMaxHeight(100L);
		routeD.setMaxLength(100L);
		routeD.setMaxWeight(100L);
		routeD.setMaxWidth(100L);
		routeD.setName("Route D");
		this.routeRepository.save(routeD);

		Route routeE = new Route();
		routeE.setDestinationCity(endCity);
		routeE.setStartingCity(cityD);
		routeE.setLength(20L);
		routeE.setMaxHeight(100L);
		routeE.setMaxLength(100L);
		routeE.setMaxWeight(100L);
		routeE.setMaxWidth(100L);
		routeE.setName("Route E");
		this.routeRepository.save(routeE);

		Path path = this.pathfinderService.getPath(startingCity, endCity, vehicle);
		Assert.notNull(path);
		Assert.isTrue(vehicle.getVehicleId().equals(path.getVehicle().getVehicleId()));
		Assert.isTrue(startingCity.getCityId().equals(path.getStart().getCityId()));
		Assert.isTrue(endCity.getCityId().equals(path.getEnd().getCityId()));
		Assert.isTrue(new Long(20L).equals(path.getSumLength()));
		List<Long> routeIds = path.getRoutes().stream().map(route -> route.getRouteId()).collect(Collectors.toList());
		Assert.isTrue(routeIds.contains(routeB.getRouteId()) && routeIds.contains(routeC.getRouteId()));
	}

}

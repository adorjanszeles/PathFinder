package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pathfinder.exceptions.BadRequestException;
import pathfinder.exceptions.RouteNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.model.repositories.CityRepository;
import pathfinder.model.repositories.RouteRepository;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class RouteService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private RouteRepository routeRepository;

	public void deleteRoute(Long routeId) {
		Route persistedRoute = this.routeRepository.findOne(routeId);
		if (persistedRoute == null) {
			throw new RouteNotFoundException();
		}
		this.routeRepository.delete(persistedRoute);
	}

	public List<Route> getAllRoute() {
		List<Route> result = new ArrayList<Route>();
		Iterator<Route> iterator = this.routeRepository.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public Route getRouteWithCities(Long routeId) {
		Route route = this.routeRepository.findOne(routeId);
		if (route == null) {
			throw new RouteNotFoundException();
		}
		return route;
	}

	public Route modifyRoute(Long routeId, Route route) {
		Route persistedRoute = this.routeRepository.findOne(routeId);
		if (persistedRoute == null) {
			throw new RouteNotFoundException();
		}
		persistedRoute.setMaxHeight(route.getMaxHeight());
		persistedRoute.setMaxLength(route.getMaxLength());
		persistedRoute.setMaxWeight(route.getMaxWeight());
		persistedRoute.setMaxWidth(route.getMaxWidth());
		persistedRoute.setStartingCity(this.cityRepository.findOne(route.getStartingCity().getCityId()));
		persistedRoute.setDestinationCity(this.cityRepository.findOne(route.getDestinationCity().getCityId()));
		return this.routeRepository.save(persistedRoute);
	}

	public Route saveRoute(Route route) {
		Route persistedRoute = new Route();
		persistedRoute.setMaxHeight(route.getMaxHeight());
		persistedRoute.setMaxLength(route.getMaxLength());
		persistedRoute.setMaxWeight(route.getMaxWeight());
		persistedRoute.setMaxWidth(route.getMaxWidth());
		City startingCity = this.cityRepository.findOne(route.getStartingCity().getCityId());
		if (startingCity == null) {
			throw new BadRequestException();
		}
		persistedRoute.setStartingCity(startingCity);
		City destinationCity = this.cityRepository.findOne(route.getDestinationCity().getCityId());
		if (destinationCity == null) {
			// bad request
		}
		persistedRoute.setDestinationCity(destinationCity);
		return this.routeRepository.save(persistedRoute);
	}

}

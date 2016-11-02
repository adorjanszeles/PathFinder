package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.badrequest.RouteBadRequestException;
import pathfinder.exceptions.notfound.RouteNotFoundException;
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

	private Route doSaveRoute(Route persistedRoute, Route routeFromUI) {
		persistedRoute.setLength(routeFromUI.getLength());
		persistedRoute.setMaxHeight(routeFromUI.getMaxHeight());
		persistedRoute.setMaxLength(routeFromUI.getMaxLength());
		persistedRoute.setMaxWeight(routeFromUI.getMaxWeight());
		persistedRoute.setMaxWidth(routeFromUI.getMaxWidth());
		persistedRoute.setName(routeFromUI.getName());
		City startingCity = this.cityRepository.findOne(routeFromUI.getStartingCity().getCityId());
		if (startingCity == null) {
			throw new CityBadRequestException();
		}
		persistedRoute.setStartingCity(startingCity);
		City destinationCity = this.cityRepository.findOne(routeFromUI.getDestinationCity().getCityId());
		if (destinationCity == null) {
			throw new CityBadRequestException();
		}
		persistedRoute.setDestinationCity(destinationCity);
		return this.routeRepository.save(persistedRoute);
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
		this.validateRoute(route);
		Route persistedRoute = this.routeRepository.findOne(routeId);
		if (persistedRoute == null) {
			throw new RouteNotFoundException();
		}
		return this.doSaveRoute(persistedRoute, route);
	}

	public Route saveRoute(Route route) {
		this.validateRoute(route);
		Route persistedRoute = new Route();
		return this.doSaveRoute(persistedRoute, route);
	}

	private void validateRoute(Route route) {
		if (StringUtils.isEmpty(route.getName()) || route.getLength() == null || route.getLength() < 0
				|| route.getMaxHeight() < 0 || route.getMaxLength() < 0 || route.getMaxWeight() < 0
				|| route.getMaxWidth() < 0 || route.getDestinationCity() == null || route.getStartingCity() == null) {
			throw new RouteBadRequestException();
		}
	}

}

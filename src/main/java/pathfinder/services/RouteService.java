package pathfinder.services;

import java.util.List;

import pathfinder.model.nodes.Route;

/**
 * Útvonalak kezelésére szolgáló Service.
 * 
 * @author Kiss László
 *
 */
public interface RouteService {

	/**
	 * Útvonal törlése.
	 * 
	 * @param routeId
	 */
	void deleteRoute(Long routeId);

	/**
	 * Összes útvonal lekérdezése.
	 * 
	 * @return
	 */
	List<Route> getAllRoute();

	/**
	 * Útvonal lekérdezése a hozzá tartozó városokkal.
	 * 
	 * @param routeId
	 * @return
	 */
	Route getRouteWithCities(Long routeId);

	/**
	 * Útvonal módosítása.
	 * 
	 * @param routeId
	 * @param route
	 * @return
	 */
	Route modifyRoute(Long routeId, Route route);

	/**
	 * Útvonal mentése.
	 * 
	 * @param route
	 * @return
	 */
	Route saveRoute(Route route);

	/**
	 * Útvonal keresése név alapján.
	 * 
	 * @param searchRouteEntity
	 * @return
	 */
	List<Route> searchRouteByParams(Route searchRouteEntity);

}

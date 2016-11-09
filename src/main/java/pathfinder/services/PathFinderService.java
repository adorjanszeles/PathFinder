package pathfinder.services;

import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;

/**
 * Útkeresésre szolgáló Service.
 * 
 * @author Kiss László
 *
 */
public interface PathFinderService {

	/**
	 * Út keresése a jármű számára a megadott városok között.
	 * 
	 * @param from
	 * @param to
	 * @param vehicle
	 * @return
	 * @throws NoPathForThisParameters
	 */
	Path getPath(City from, City to, Vehicle vehicle) throws NoPathForThisParameters;

}

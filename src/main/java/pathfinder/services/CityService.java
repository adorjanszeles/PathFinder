package pathfinder.services;

import java.util.List;

import pathfinder.model.nodes.City;

/**
 * Városok kezelésére szolgáló Service.
 * 
 * @author Kiss László
 *
 */
public interface CityService {

	/**
	 * Város törlése.
	 * 
	 * @param cityId
	 */
	void deleteCity(Long cityId);

	/**
	 * Város keresése id alapján.
	 * 
	 * @param cityId
	 * @return
	 */
	City findById(Long cityId);

	/**
	 * Város keresése név alapján.
	 * 
	 * @param searchCityEntity
	 * @return
	 */
	List<City> findCitiesByParams(City searchCityEntity);

	/**
	 * Összes város lekérdezése.
	 * 
	 * @return
	 */
	List<City> getAllCities();

	/**
	 * Város módosítása.
	 * 
	 * @param cityId
	 * @param city
	 * @return
	 */
	City modifyCity(Long cityId, City city);

	/**
	 * Város mentése.
	 * 
	 * @param city
	 * @return
	 */
	City saveCity(City city);

}

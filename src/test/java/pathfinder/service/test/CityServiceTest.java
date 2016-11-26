package pathfinder.service.test;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.services.CityService;

/**
 * Városokat kezelő Service tesztelése.
 * 
 * @author Kiss László
 *
 */
public class CityServiceTest extends PathfinderServiceTestBase {

	@Autowired
	CityService cityService;

	/**
	 * Város törlése.
	 */
	@Test
	public void deleteCityTest() {
		City newCity = this.createNewCity();
		Long cityId = newCity.getCityId();
		this.cityService.deleteCity(cityId);
		Assert.isNull(this.cityRepository.findOne(cityId));
	}

	/**
	 * Nem létező város törlése.
	 */
	@Test(expected = CityNotFoundException.class)
	public void deleteNotExistingCity() {
		this.cityService.deleteCity(Long.MAX_VALUE);
	}

	/**
	 * Azonosító alapú keresés.
	 */
	@Test
	public void findByIdTest() {
		City newCity = this.createNewCity();
		City foundCity = this.cityService.findById(newCity.getCityId());
		Assert.notNull(foundCity);
		Assert.isTrue(newCity.getCityId().equals(foundCity.getCityId()));
	}

	/**
	 * Lekérdezés nem létező azonosítóval.
	 */
	@Test(expected = CityNotFoundException.class)
	public void findByNotExistingIdTest() {
		this.cityService.findById(Long.MAX_VALUE);
	}

	/**
	 * Összes város lekérdezése.
	 */
	@Test
	public void getAllCitiesTest() {
		City firstCity = this.createNewCity();
		City secondCity = this.createNewCity();
		List<City> foundCities = this.cityService.getAllCities();
		Assert.notNull(foundCities);
		List<Long> cityIds = foundCities.stream().map(city -> city.getCityId()).collect(Collectors.toList());
		Assert.isTrue(cityIds.contains(firstCity.getCityId()) && cityIds.contains(secondCity.getCityId()));
	}

	/**
	 * Város módosítása név nélkül.
	 */
	@Test(expected = CityBadRequestException.class)
	public void modifyCityBadRequestTest() {
		City newCity = this.createNewCity();
		newCity.setName(null);
		this.cityService.modifyCity(newCity.getCityId(), newCity);
	}

	/**
	 * Város módosítása.
	 */
	@Test
	public void modifyCityTest() {
		City newCity = this.createNewCity();
		String modifiedName = "modifiedCityName";
		newCity.setName(modifiedName);
		City modifiedCity = this.cityService.modifyCity(newCity.getCityId(), newCity);
		Assert.notNull(modifiedCity);
		Assert.isTrue(
				modifiedName.equals(modifiedCity.getName()) && modifiedCity.getCityId().equals(newCity.getCityId()));
	}

	/**
	 * Nem létező város módosítása.
	 */
	@Test(expected = CityNotFoundException.class)
	public void modifyNotExistingCityTest() {
		City newCity = this.createNewCity();
		this.cityService.modifyCity(Long.MAX_VALUE, newCity);
	}

	/**
	 * Város mentése név nélkül.
	 */
	@Test(expected = CityBadRequestException.class)
	public void saveCityBadRequestTest() {
		City city = new City();
		this.cityService.saveCity(city);
	}

	/**
	 * Új város mentése.
	 */
	@Test
	public void saveCityTest() {
		City city = new City();
		city.setName("TestCityName");
		City persistedCity = this.cityService.saveCity(city);
		Assert.notNull(persistedCity);
		Assert.notNull(persistedCity.getCityId());
	}

	/**
	 * Város keresése név alapján.
	 */
	public void searchCityTest() {
		City newCity = this.createNewCity();
		String name = newCity.getName();

		City searchEntity = new City();
		searchEntity.setName(name);

		List<City> foundCities = this.cityService.findCitiesByParams(searchEntity);
		Assert.notNull(foundCities);
		List<Long> cityIds = foundCities.stream().map(city -> city.getCityId()).collect(Collectors.toList());
		Assert.isTrue(cityIds.contains(newCity.getCityId()));
	}

}

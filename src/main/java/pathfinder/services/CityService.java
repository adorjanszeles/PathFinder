package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.model.repositories.CityRepository;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public void deleteCity(Long cityId) {
		City persistedCity = this.cityRepository.findOne(cityId);
		if (persistedCity == null) {
			throw new CityNotFoundException();
		}
		this.cityRepository.delete(persistedCity);
	}

	private City doSaveCity(City persistedCity, City cityFromUI) {
		persistedCity.setName(cityFromUI.getName());
		return this.cityRepository.save(persistedCity);
	}

	public City findById(Long cityId) {
		return this.cityRepository.findOne(cityId);
	}

	public List<City> findCitiesByParams(City searchCityEntity) {
		return this.cityRepository.findByName(searchCityEntity.getName());
	}

	public List<City> getAllCities() {
		List<City> result = new ArrayList<City>();
		Iterator<City> iterator = this.cityRepository.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public City getCityWithRoutes(Long cityId) {
		City persistedCity = this.cityRepository.findOne(cityId);
		if (persistedCity == null) {
			throw new CityNotFoundException();
		}
		persistedCity.setRoutesToCity(this.cityRepository.findRoutesToCity(cityId));
		persistedCity.setRoutesFromCity(this.cityRepository.findRoutesFromCity(cityId));
		return persistedCity;
	}

	public City modifyCity(Long cityId, City city) {
		this.validateCity(city);
		City persistedCity = this.cityRepository.findOne(cityId);
		if (persistedCity == null) {
			throw new CityNotFoundException();
		}
		return this.doSaveCity(persistedCity, city);
	}

	public City saveCity(City city) {
		this.validateCity(city);
		City persistedCity = new City();
		return this.doSaveCity(persistedCity, city);
	}

	private void validateCity(City city) {
		if (StringUtils.isEmpty(city.getName())) {
			throw new CityBadRequestException();
		}
	}
}

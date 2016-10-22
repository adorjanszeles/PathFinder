package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pathfinder.model.nodes.City;
import pathfinder.model.repositories.CityRepository;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public void saveCity(City city) {
		cityRepository.save(city);
	}

	public City findById(Long cityId) {
		return cityRepository.findOne(cityId);
	}
	 
	public List<City> getAllCities() {
		List<City> result = new ArrayList<City>();
		Iterator<City> iterator = cityRepository.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}
	
	public City modifyCiy(City city) {
		City persistedCity = this.cityRepository.findOne(city.getCityId());
		persistedCity.setName(city.getName());
		return cityRepository.save(persistedCity);
	}
	
	public void deleteCity(City city) {
		City persistedCity = cityRepository.findOne(city.getCityId());
		Assert.notNull(persistedCity);
		cityRepository.delete(persistedCity);
	}
} 

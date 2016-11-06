package pathfinder.ui.uilogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.services.CityService;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * A városok üzleti logikáját tartalmazó managed bean implementáció.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@ApplicationScoped
public class CityBeanImpl extends AbstractBean implements CityBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityBeanImpl.class);
    @Autowired
    private CityService cityService;

    @Override
    public void persistCity(City selectedCity) {
        LOGGER.info("persistCity(selectedCity = {})", selectedCity);
        try {
            if(selectedCity.getCityId() == null) {
                cityService.saveCity(selectedCity);
                showMessage(Messages.SAVE_CITY_SUCCESS, FacesMessage.SEVERITY_INFO);
            } else {
                cityService.modifyCity(selectedCity.getCityId(), selectedCity);
                showMessage(Messages.MODIFY_CITY_SUCCESS, FacesMessage.SEVERITY_INFO);
            }
        } catch (CityNotFoundException cityNotFound) {
            showMessage(Messages.CITY_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("persistCity() ... done");
    }

    @Override
    public List<City> searchCities(City searchCityEntity) {
        LOGGER.info("searchCities(searchCityEntity = {})", searchCityEntity);
        List<City> result = new ArrayList<>();
        try {
            if(searchCityEntity.getName() == null || "".equals(searchCityEntity.getName())) {
                result.addAll(cityService.getAllCities());
            } else {
                result.addAll(cityService.findCitiesByParams(searchCityEntity));
            }
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("searchCities() ... done result={}", result);
        return result;
    }

    @Override
    public void deleteCity(City selectedCity) {
        LOGGER.info("deleteCity(selectedCity = {})", selectedCity);
        try {
            cityService.deleteCity(selectedCity.getCityId());
            showMessage(Messages.DELETE_CITY_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch (CityNotFoundException cityNotFound) {
            showMessage(Messages.CITY_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("deleteCity() ... done");
    }

    @Override
    public City getCityById(Long cityId) {
        LOGGER.info("getCityById(cityId = {})", cityId);
        City result = null;
        try {
            result = cityService.findById(cityId);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getCityById(cityId = {}) ... done", cityId);
        return result;
    }

    @Override
    public List<City> getAllCity() {
        LOGGER.info("getAllCity()");
        List<City> result = new ArrayList<>();
        try {
            result.addAll(cityService.getAllCities());
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getAllCity() result = {} ... done", result);
        return result;
    }

    /**
     * Függőség setter metódus a tesztek miatt.
     *
     * @param cityService A mockolt city service
     */
    void setCityService(CityService cityService) {
        this.cityService = cityService;
    }
}

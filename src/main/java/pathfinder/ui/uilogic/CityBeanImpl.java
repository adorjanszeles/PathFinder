package pathfinder.ui.uilogic;

import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.services.CityService;
import pathfinder.services.RouteService;
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
    @Autowired
    private CityService cityService;
    @Autowired
    private RouteService routeService;

    @Override
    public List<Route> getRoutesToCity() {
        return getRoutes();
    }

    @Override
    public List<Route> getRoutesFromCity() {
        return getRoutes();
    }

    @Override
    public void persistCity(City selectedCity) {
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
    }

    @Override
    public List<City> searchCities(City searchCityEntity) {
        List<City> result = new ArrayList<>();
        try {
            if(searchCityEntity.getName() == null || "".equals(searchCityEntity.getName())) {
                result.addAll(cityService.getAllCities());
            } else {
                // TODO város keresés paraméter alapján
            }
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    @Override
    public void deleteCity(City selectedCity) {
        try {
            cityService.deleteCity(selectedCity.getCityId());
            showMessage(Messages.DELETE_CITY_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch (CityNotFoundException cityNotFound) {
            showMessage(Messages.CITY_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public City getCityById(Long cityId) {
        City result = null;
        try {
            result = cityService.findById(cityId);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    private List<Route> getRoutes() {
        List<Route> result = new ArrayList<>();
        try {
            result.addAll(routeService.getAllRoute());
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }
}

package pathfinder.ui.city;

import pathfinder.model.nodes.City;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.CityBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * A városok keresését, törlését megvalósító view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@SessionScoped
public class CitySearchBean {
    @ManagedProperty(value = "#{cityBeanImpl}")
    private CityBean cityBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private City searchCityEntity;
    private List<City> cityList;
    private City selectedCity;

    @PostConstruct
    public void postConstruct() {
        searchCityEntity = new City();
    }

    /**
     * Város keresése
     */
    public String searchCity() {
        cityList = cityBean.searchCities(searchCityEntity);
        return FacesCommon.stayOnPage();
    }

    /**
     * Város törlése
     */
    public String deleteCity() {
        cityBean.deleteCity(selectedCity);
        return navigationBean.gotCitySearchPage();
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public City getSearchCityEntity() {
        return searchCityEntity;
    }

    public void setSearchCityEntity(City searchCityEntity) {
        this.searchCityEntity = searchCityEntity;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}

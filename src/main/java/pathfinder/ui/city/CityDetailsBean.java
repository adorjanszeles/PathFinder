package pathfinder.ui.city;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.CityBean;
import pathfinder.ui.uilogic.RouteBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Az új város hozzáadása, illetve a város szerkesztése képernyők managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@SessionScoped
public class CityDetailsBean {
    @ManagedProperty(value = "#{cityBeanImpl}")
    private CityBean cityBean;
    @ManagedProperty(value = "#{routeBeanImpl}")
    private RouteBean routeBean;
    private City selectedCity;
    private boolean isNew;

    /**
     * Város példány inicializálása
     */
    public void initializeCity() {
        this.selectedCity = new City();
        this.selectedCity.setRoutesFromCity(new ArrayList<>());
        this.selectedCity.setRoutesToCity(new ArrayList<>());
    }

    /**
     * Kiválasztott város módosítása
     */
    public String persistCity() {
        cityBean.persistCity(selectedCity);
        return FacesCommon.stayOnPage();
    }

    public List<Route> getRoutesToCity() {
        return routeBean.getRoutesToCity();
    }

    public List<Route> getRoutesFromCity() {
        return routeBean.getRoutesFromCity();
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setRouteBean(RouteBean routeBean) {
        this.routeBean = routeBean;
    }
}

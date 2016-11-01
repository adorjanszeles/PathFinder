package pathfinder.ui.route;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.RouteBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Az út részletező képernyő managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@SessionScoped
public class RouteDetailsBean {
    @ManagedProperty(value = "#{routeBeanImpl}")
    private RouteBean routeBean;
    private Route selectedRoute;
    private boolean isNew;

    /**
     * Út inicializálása.
     */
    public void initializeRoute() {
        selectedRoute = new Route();
    }

    /**
     * Út perzisztálása.
     */
    public String persistRoute() {
        routeBean.persistRoute(selectedRoute);
        return FacesCommon.stayOnPage();
    }

    /**
     * Lekéri a városokat a szevertől.
     *
     * @return Az összes város egy listában
     */
    public List<City> getCityList() {
        return routeBean.getAllCity();
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
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

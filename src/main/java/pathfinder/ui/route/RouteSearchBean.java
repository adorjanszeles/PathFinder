package pathfinder.ui.route;

import pathfinder.model.nodes.Route;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.RouteBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Az utak keresését megvalósító view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 29.
 */
@ManagedBean
@SessionScoped
public class RouteSearchBean {
    @ManagedProperty(value = "#{routeBeanImpl}")
    private RouteBean routeBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private List<Route> routeList;
    private Route selectedRoute;
    private Route searchRouteEntity;

    @PostConstruct
    public void postConstruct() {
        searchRouteEntity = new Route();
    }

    /**
     * A kibálasztott út törlése.
     */
    public String deleteRoute() {
        routeBean.deleteRoute(selectedRoute);
        return navigationBean.goToRouteSearchPage();
    }

    /**
     * Utak keresése.
     */
    public String searchRoute() {
        routeList = routeBean.searchRoute(searchRouteEntity);
        return FacesCommon.stayOnPage();
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public void setRouteBean(RouteBean routeBean) {
        this.routeBean = routeBean;
    }

    public Route getSearchRouteEntity() {
        return searchRouteEntity;
    }

    public void setSearchRouteEntity(Route searchRouteEntity) {
        this.searchRouteEntity = searchRouteEntity;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}

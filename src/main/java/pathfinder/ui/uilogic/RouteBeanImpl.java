package pathfinder.ui.uilogic;

import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.RouteNotFoundException;
import pathfinder.model.nodes.Route;
import pathfinder.services.RouteService;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Az utakkal kapcsolatos műveletek üzleti logikájának implementációja.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 29.
 */
@ManagedBean
@ApplicationScoped
public class RouteBeanImpl extends AbstractBean implements RouteBean {
    @Autowired
    private RouteService routeService;

    @Override
    public void deleteRoute(Route route) {
        try {
            routeService.deleteRoute(route.getRouteId());
            showMessage(Messages.DELETE_ROUTE_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch(RouteNotFoundException routeNotFound) {
            showMessage(Messages.ROUTE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void persistRoute(Route route) {
        try {
            if(route.getRouteId() == null) {
                routeService.saveRoute(route);
                showMessage(Messages.SAVE_ROUTE_SUCCESS, FacesMessage.SEVERITY_INFO);
            } else {
                routeService.modifyRoute(route.getRouteId(), route);
                showMessage(Messages.MODIFY_ROUTE_SUCCESS, FacesMessage.SEVERITY_INFO);
            }
        } catch(RouteNotFoundException routeNotFound) {
            showMessage(Messages.ROUTE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public List<Route> searchRoute(Route searchRouteEntity) {
        List<Route> result = new ArrayList<>();
        try {
            if(searchRouteEntity.getName() == null) {
                result.addAll(routeService.getAllRoute());
            } else {
                // TODO útak keresése paraméter alapján
            }
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    @Override
    public Route getRouteById(Long routeId) {
        Route result = null;
        try {
            result = routeService.getRouteWithCities(routeId);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    @Override
    public List<Route> getRoutesToCity() {
        return getRoutes();
    }

    @Override
    public List<Route> getRoutesFromCity() {
        return getRoutes();
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

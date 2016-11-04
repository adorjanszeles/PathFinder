package pathfinder.ui.uilogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteBeanImpl.class);
    @Autowired
    private RouteService routeService;

    @Override
    public void deleteRoute(Route route) {
        LOGGER.info("deleteRoute(route = {})", route);
        try {
            routeService.deleteRoute(route.getRouteId());
            showMessage(Messages.DELETE_ROUTE_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch(RouteNotFoundException routeNotFound) {
            showMessage(Messages.ROUTE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("deleteRoute() ... done");
    }

    @Override
    public void persistRoute(Route route) {
        LOGGER.info("persistRoute(route = {})", route);
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
        LOGGER.info("persistRoute() ... done");
    }

    @Override
    public List<Route> searchRoute(Route searchRouteEntity) {
        LOGGER.info("searchRoute(searchRouteEntity = {})", searchRouteEntity);
        List<Route> result = new ArrayList<>();
        try {
            if(searchRouteEntity.getName() == null) {
                result.addAll(routeService.getAllRoute());
            } else {
                result.addAll(routeService.searchRouteByParams(searchRouteEntity));
            }
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("searchRoute() result = {} ... done", result);
        return result;
    }

    @Override
    public Route getRouteById(Long routeId) {
        LOGGER.info("getRouteById(routeId = {})", routeId);
        Route result = null;
        try {
            result = routeService.getRouteWithCities(routeId);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getRouteById() result = {} ... done", result);
        return result;
    }

    @Override
    public List<Route> getRoutesToCity() {
        LOGGER.info("getRoutesToCity()");
        List<Route> result = getRoutes();
        LOGGER.info("getRoutesToCity() result = {} ... done", result);
        return result;
    }

    @Override
    public List<Route> getRoutesFromCity() {
        LOGGER.info("getRoutesFromCity()");
        List<Route> result = getRoutes();
        LOGGER.info("getRoutesFromCity() result = {} ... done", result);
        return result;
    }

    private List<Route> getRoutes() {
        LOGGER.debug("getRoutes()");
        List<Route> result = new ArrayList<>();
        try {
            result.addAll(routeService.getAllRoute());
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.debug("getRoutes() result = {} ... done", result);
        return result;
    }

}

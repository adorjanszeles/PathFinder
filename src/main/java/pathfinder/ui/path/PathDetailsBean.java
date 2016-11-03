package pathfinder.ui.path;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Route;
import pathfinder.model.nodes.Vehicle;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.CityBean;
import pathfinder.ui.uilogic.PathBean;
import pathfinder.ui.uilogic.VehicleBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * @author Széles Adorján
 * Date: 2016. 11. 03.
 */
@ManagedBean
@SessionScoped
public class PathDetailsBean {
    @ManagedProperty(value = "#{pathBeanImpl}")
    private PathBean pathBean;
    @ManagedProperty(value = "#{cityBeanImpl}")
    private CityBean cityBean;
    @ManagedProperty(value = "#{vehicleBeanImpl}")
    private VehicleBean vehicleBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private Path selectedPath;
    private City fromCity;
    private City toCity;
    private Vehicle selectedVehicle;
    private boolean isNew;
    private List<City> allCities;
    private List<Vehicle> allVehicles;
    private String createPathString;

    /**
     * Inicializálja a view listáit.
     */
    public void initializeView() {
        allCities = cityBean.getAllCity();
        allVehicles = vehicleBean.getAllVehicle();
    }

    /**
     * Elmenti a generált útvonalat.
     */
    public String savePath() {
        pathBean.savePath(selectedPath);
        return FacesCommon.stayOnPage();
    }

    /**
     * Útvonalat generál a felhasználó által megadott paraméterek alapján.
     */
    public String generatePath() {
        selectedPath = pathBean.generatePath(fromCity, toCity, selectedVehicle);
        if(selectedPath == null) {
            return FacesCommon.stayOnPage();
        }
        isNew = false;
        return navigationBean.goToPathDetailsPage();
    }

    public String getCreatePathString() {
        createPathString = "";
        if(selectedPath != null) {
            StringBuilder pathBuilder = new StringBuilder();
            int destinationCounter = 1;
            for (Route routeElement : selectedPath.getRoutes()) {
                pathBuilder.append(Integer.toString(destinationCounter++));
                pathBuilder.append(".) ");
                pathBuilder.append(routeElement.getName());
                pathBuilder.append(": ");
                pathBuilder.append(routeElement.getStartingCity().getName());
                pathBuilder.append(" --> ");
                pathBuilder.append(routeElement.getDestinationCity().getName());
                pathBuilder.append("\n");
            }
            createPathString = pathBuilder.toString();
        }
        return createPathString;
    }

    public void setCreatePathString(String createPathString) {
        this.createPathString = createPathString;
    }

    public Path getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(Path selectedPath) {
        this.selectedPath = selectedPath;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public List<City> getAllCities() {
        return allCities;
    }

    public void setAllCities(List<City> allCities) {
        this.allCities = allCities;
    }

    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public void setAllVehicles(List<Vehicle> allVehicles) {
        this.allVehicles = allVehicles;
    }

    public void setPathBean(PathBean pathBean) {
        this.pathBean = pathBean;
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }

    public void setVehicleBean(VehicleBean vehicleBean) {
        this.vehicleBean = vehicleBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}

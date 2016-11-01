package pathfinder.ui.vehicle;

import pathfinder.model.nodes.Vehicle;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.VehicleBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Az utak keresésére szolgáló view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 29.
 */
@ManagedBean
@SessionScoped
public class VehicleSearchBean {
    @ManagedProperty(value = "#{vehicleBeanImpl}")
    private VehicleBean vehicleBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private List<Vehicle> vehicleList;
    private Vehicle selectedVehicle;
    private Vehicle searchVehicleEntity;

    @PostConstruct
    public void postConstruct() {
        searchVehicleEntity = new Vehicle();
        this.searchVehicle();
    }

    /**
     * Jármű törlése.
     */
    public String deleteVehicle() {
        vehicleBean.deleteVehicle(selectedVehicle);
        return navigationBean.goToVehicleSearchPage();
    }

    /**
     * Járművek keresése.
     */
    public String searchVehicle() {
        vehicleList = vehicleBean.searchVehicle(searchVehicleEntity);
        return FacesCommon.stayOnPage();
    }

    public void setVehicleBean(VehicleBean vehicleBean) {
        this.vehicleBean = vehicleBean;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public Vehicle getSearchVehicleEntity() {
        return searchVehicleEntity;
    }

    public void setSearchVehicleEntity(Vehicle searchVehicleEntity) {
        this.searchVehicleEntity = searchVehicleEntity;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}

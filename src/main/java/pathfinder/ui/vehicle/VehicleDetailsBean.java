package pathfinder.ui.vehicle;

import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.UserBean;
import pathfinder.ui.uilogic.VehicleBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * A járművek részletező view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@SessionScoped
public class VehicleDetailsBean {
    @ManagedProperty(value = "#{userBeanImpl}")
    private UserBean userBean;
    @ManagedProperty(value = "#{vehicleBeanImpl}")
    private VehicleBean vehicleBean;
    private Vehicle selectedVehicle;
    private boolean isNew;

    /**
     * Új jármű inicializálása.
     */
    public void initializeVehicle() {
        selectedVehicle = new Vehicle();
    }

    /**
     * Jármű perzisztálása.
     */
    public String persistVehicle() {
        vehicleBean.persistVehicle(selectedVehicle);
        return FacesCommon.stayOnPage();
    }

    /**
     * Lekéri a szervertől a lehetséges tulajdonosokat.
     * @return Egy user lista
     */
    public List<User> getOwnerList() {
        return userBean.getOwners();
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setVehicleBean(VehicleBean vehicleBean) {
        this.vehicleBean = vehicleBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}

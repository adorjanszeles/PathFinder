package pathfinder.ui.uilogic;

import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.UserService;
import pathfinder.services.VehicleService;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * A járművek kezeléséért felelős üzelti logika implementációja.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 23.
 */
@ManagedBean
@ApplicationScoped
public class VehicleBeanImpl extends AbstractBean implements VehicleBean {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;

    @Override
    public List<Vehicle> searchVehicle(Vehicle searchVehicleEntity) {
        List<Vehicle> result = new ArrayList<>();
        try {
            if(searchVehicleEntity.getPlateNumber() == null) {
                result.addAll(vehicleService.getAllVehicles());
            } else {
                // TODO útak keresése paraméter alapján
            }
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        try {
            vehicleService.deleteVehicle(vehicle.getVehicleId());
            showMessage(Messages.DELETE_VEHICLE_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch(VehicleNotFoundException vehicleNotFound) {
            showMessage(Messages.VEHICLE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void persistVehicle(Vehicle vehicle) {
        try {
            if(vehicle.getVehicleId() == null) {
                vehicleService.saveVehicle(vehicle);
                showMessage(Messages.SAVE_VEHICLE_SUCCESS, FacesMessage.SEVERITY_INFO);
            } else {
                vehicleService.modifyVehicle(vehicle.getVehicleId(), vehicle);
                showMessage(Messages.MODIFY_VEHICLE_SUCCESS, FacesMessage.SEVERITY_INFO);
            }
        } catch(VehicleNotFoundException vehicleNotFound) {
            showMessage(Messages.VEHICLE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public List<User> getOwners() {
        List<User> result = new ArrayList<>();
        try {
            result.addAll(userService.getAllUser());
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }
}

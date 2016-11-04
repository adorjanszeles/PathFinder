package pathfinder.ui.uilogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.model.nodes.Vehicle;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleBeanImpl.class);
    @Autowired
    private VehicleService vehicleService;

    @Override
    public List<Vehicle> searchVehicle(Vehicle searchVehicleEntity) {
        LOGGER.info("searchVehicle(searchVehicleEntity = {})", searchVehicleEntity);
        List<Vehicle> result = new ArrayList<>();
        try {
            if(searchVehicleEntity.getPlateNumber() == null) {
                result.addAll(vehicleService.getAllVehicles());
            } else {
                result.addAll(vehicleService.searchVehicleByParams(searchVehicleEntity));
            }
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("searchVehicle() result = {} ... done", result);
        return result;
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        LOGGER.info("deleteVehicle(vehicle = {})", vehicle);
        try {
            vehicleService.deleteVehicle(vehicle.getVehicleId());
            showMessage(Messages.DELETE_VEHICLE_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch(VehicleNotFoundException vehicleNotFound) {
            showMessage(Messages.VEHICLE_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("deleteVehicle() ... done");
    }

    @Override
    public void persistVehicle(Vehicle vehicle) {
        LOGGER.info("persistVehicle(vehicle = {})", vehicle);
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
        LOGGER.info("persistVehicle() ... done");
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        LOGGER.info("getAllVehicle()");
        List<Vehicle> result = new ArrayList<>();
        try {
            result.addAll(vehicleService.getAllVehicles());
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getAllVehicle() result = {} ... done", result);
        return result;
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId) {
        LOGGER.info("getVehicleById(vehicleId = {})", vehicleId);
        Vehicle result = null;
        try {
            result = vehicleService.findById(vehicleId);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getAllVehicle() result = {} ... done", result);
        return result;
    }

}

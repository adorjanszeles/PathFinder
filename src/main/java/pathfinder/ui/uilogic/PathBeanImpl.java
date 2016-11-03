package pathfinder.ui.uilogic;

import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.badrequest.VehicleBadRequestException;
import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.PathFinderService;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * A generált útvonalak felületi üzleti logikájának interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@ApplicationScoped
public class PathBeanImpl extends AbstractBean implements PathBean {
    @Autowired
    private PathFinderService pathService;

    @Override
    public Path generatePath(City from, City to, Vehicle vehicle) {
        Path result = null;
        try {
            result = pathService.getPath(from, to, vehicle);
            if(result == null) {
                result = new Path();
            }
        } catch(CityBadRequestException cityNull) {
            showMessage(Messages.FROM_OR_TO_CITY_NULL, FacesMessage.SEVERITY_ERROR);
        } catch(VehicleBadRequestException vehicleNull) {
            showMessage(Messages.VEHICLE_NULL, FacesMessage.SEVERITY_ERROR);
        } catch (NoPathForThisParameters noPathForThisParameters) {
            showMessage(Messages.NO_PATH_WITH_THIS_PARAMETER, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

}

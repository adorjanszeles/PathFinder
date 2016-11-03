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
import java.util.ArrayList;
import java.util.List;

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
    public List<Path> searchPath(Path searchParams) {
        List<Path> result = new ArrayList<>();
        try {
            // TODO a keresés implementációja
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        return result;
    }

    @Override
    public Path generatePath(City from, City to, Vehicle vehicle) {
        Path result = null;
        try {
            result = pathService.getPath(from, to, vehicle);
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

    @Override
    public void deletePath(Path path) {
        try {
            // TODO útvonal törlése
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void savePath(Path selectedPath) {
        try {
            // TODO útvonal mentése
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }
}

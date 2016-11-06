package pathfinder.ui.uilogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PathBeanImpl.class);
    @Autowired
    private PathFinderService pathService;

    @Override
    public Path generatePath(City from, City to, Vehicle vehicle) {
        LOGGER.info("generatePath(from = {}, to = {}, vehicle = {})", from, to, vehicle);
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
        LOGGER.info("generatePath(result = {}) ... done", result);
        return result;
    }

    /**
     * A tesztek miatti mockolt függőség injektálása.
     *
     * @param pathService A mockolt path service
     */
    void setPathService(PathFinderService pathService) {
        this.pathService = pathService;
    }
}

package pathfinder.ui.converter;

import pathfinder.model.nodes.Vehicle;
import pathfinder.ui.uilogic.VehicleBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * JSF converter a jármű entitások számára.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 03.
 */
@ManagedBean
public class VehicleConverter implements Converter {
    @ManagedProperty(value = "#{vehicleBeanImpl}")
    private VehicleBean vehicleBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return vehicleBean.getVehicleById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return Long.toString(((Vehicle)o).getVehicleId());
    }

    public void setVehicleBean(VehicleBean vehicleBean) {
        this.vehicleBean = vehicleBean;
    }
}

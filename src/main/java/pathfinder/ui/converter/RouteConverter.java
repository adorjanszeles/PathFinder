package pathfinder.ui.converter;

import pathfinder.model.nodes.Route;
import pathfinder.ui.uilogic.RouteBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * JSF converter osztály az út példányok listában való megjelenítéséért.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 02.
 */
@ManagedBean
public class RouteConverter implements Converter {
    @ManagedProperty(value = "#{routeBeanImpl}")
    private RouteBean routeBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return routeBean.getRouteById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return Long.toString(((Route)o).getRouteId());
    }

    public void setRouteBean(RouteBean routeBean) {
        this.routeBean = routeBean;
    }
}

package pathfinder.ui.converter;

import pathfinder.model.nodes.City;
import pathfinder.ui.uilogic.CityBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * JSF converter osztály a város példányok listában való megjelenítéséért.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 02.
 */
@ManagedBean
public class CityConverter implements Converter {
    @ManagedProperty(value = "#{cityBeanImpl}")
    private CityBean cityBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return cityBean.getCityById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return Long.toString(((City)o).getCityId());
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }
}

package pathfinder.ui.converter;

import pathfinder.model.nodes.User;
import pathfinder.ui.uilogic.UserBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * JSF converter a felhasználó entitások számára.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 02.
 */
@ManagedBean
public class UserConverter implements Converter {
    @ManagedProperty(value = "#{userBeanImpl}")
    private UserBean userBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return userBean.getUserById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return Long.toString(((User)o).getUserId());
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}

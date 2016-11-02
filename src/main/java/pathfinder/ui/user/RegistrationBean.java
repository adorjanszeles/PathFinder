package pathfinder.ui.user;

import pathfinder.common.RoleEnum;
import pathfinder.model.nodes.User;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.UserBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * A felhasználó létrehozásáért felelős view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 02.
 */
@ManagedBean
@SessionScoped
public class RegistrationBean {
    @ManagedProperty(value = "#{userBeanImpl}")
    private UserBean userBean;
    private User newUser;
    private String confirmPassword;

    @PostConstruct
    public void postConstruct() {
        newUser = new User();
    }

    /**
     * Elmenti a felahsználót.
     */
    public String saveUser() {
        userBean.saveUser(newUser, confirmPassword);
        return FacesCommon.stayOnPage();
    }

    public RoleEnum[] getRoles() {
        return RoleEnum.values();
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}

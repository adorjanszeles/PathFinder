package pathfinder.ui.user;

import pathfinder.model.nodes.User;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.UserBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * A felhasználói adatok módosításáért felelős view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@SessionScoped
public class UserDetailsBean {
    @ManagedProperty(value = "#{userBeanImpl}")
    private UserBean userBean;
    private User loggedInUser;
    private String confirmPassword;

    @PostConstruct
    public void postConstruct() {
        loggedInUser = userBean.getLoggedInUser();
    }

    /**
     * Felhasználói adatok módosítása.
     */
    public String modifyUser() {
        userBean.modifyUser(loggedInUser, confirmPassword);
        return FacesCommon.stayOnPage();
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}

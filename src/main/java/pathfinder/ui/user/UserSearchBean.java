package pathfinder.ui.user;

import pathfinder.common.RoleEnum;
import pathfinder.model.nodes.User;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.UserBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * A felhasználók keresését és törlését megvalósító view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 04.
 */
@ManagedBean
@SessionScoped
public class UserSearchBean {
    @ManagedProperty(value = "#{userBeanImpl}")
    private UserBean userBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private List<User> userList;
    private User selectedUser;
    private User searchUserEntity;

    @PostConstruct
    public void postConstruct() {
        searchUserEntity = new User();
    }

    /**
     * Jármű törlése.
     */
    public String deleteUser() {
        userBean.deleteUser(selectedUser);
        return navigationBean.goToUserSearchPage();
    }

    /**
     * Járművek keresése.
     */
    public String searchUser() {
        userList = userBean.searchUser(searchUserEntity);
        return FacesCommon.stayOnPage();
    }

    public RoleEnum[] getRoles() {
        return RoleEnum.values();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getSearchUserEntity() {
        return searchUserEntity;
    }

    public void setSearchUserEntity(User searchUserEntity) {
        this.searchUserEntity = searchUserEntity;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}

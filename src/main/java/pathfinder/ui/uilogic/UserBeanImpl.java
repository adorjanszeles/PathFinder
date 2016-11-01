package pathfinder.ui.uilogic;

import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;
import pathfinder.ui.common.Messages;
import pathfinder.ui.exceptions.PasswordsNotMatchException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * A felhasználó adatainak módosításáért felelős üzleti logika implementációja.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@ApplicationScoped
public class UserBeanImpl extends AbstractBean implements UserBean {
    @Autowired
    private UserService userService;

    @Override
    public User getLoggedInUser() {
        try {
            //TODO bejelentkezett felhasználó lekérése
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        // TODO ezt majd törölni kell
        User user = new User();
        user.setName("Blabla");
        user.setAge(28);
        user.setEmail("blabla@hu.hu");
        user.setPassword("LoL123456");
        return user;
    }

    @Override
    public void modifyUser(User loggedInUser, String confirmPassword) {
        try {
            if(!loggedInUser.getPassword().equals(confirmPassword)) {
                throw new PasswordsNotMatchException();
            }
            userService.modifyUser(loggedInUser.getUserId(), loggedInUser);
        } catch (UserNotFoundException userNotFound) {
            showMessage(Messages.USER_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (PasswordsNotMatchException passwordsNotMatch) {
            showMessage(Messages.PASSWORDS_NOT_MATCH, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
    }
}

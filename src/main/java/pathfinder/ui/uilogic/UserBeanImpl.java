package pathfinder.ui.uilogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;
import pathfinder.ui.common.Messages;
import pathfinder.ui.exceptions.LoggedInUserNotFoundException;
import pathfinder.ui.exceptions.PasswordsNotMatchException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * A felhasználó adatainak módosításáért felelős üzleti logika implementációja.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@ApplicationScoped
public class UserBeanImpl extends AbstractBean implements UserBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBeanImpl.class);
    @Autowired
    private UserService userService;

    @Override
    public User getLoggedInUser() {
        LOGGER.info("getLoggedInUser()");
        User result = null;
        try {
            // TODO nem biztos hogy jó...
            String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            result = userService.findByUserName(loggedInUserName);
            if(result == null) {
                throw new LoggedInUserNotFoundException();
            }
        } catch (LoggedInUserNotFoundException userNotFound) {
            showMessage(Messages.USER_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getLoggedInUser() result = {} ... done", result);
        return result;
    }

    @Override
    public void modifyUser(User loggedInUser, String confirmPassword) {
        LOGGER.info("modifyUser(loggedInUser = {})", loggedInUser);
        try {
            if(!loggedInUser.getPassword().equals(confirmPassword)) {
                throw new PasswordsNotMatchException();
            }
            userService.modifyUser(loggedInUser.getUserId(), loggedInUser);
            showMessage(Messages.USER_MODIFICATION_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch (UserNotFoundException userNotFound) {
            showMessage(Messages.USER_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (PasswordsNotMatchException passwordsNotMatch) {
            showMessage(Messages.PASSWORDS_NOT_MATCH, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("modifyUser() ... done");
    }

    @Override
    public void saveUser(User newUser, String confirmPassword) {
        LOGGER.info("saveUser(newUser = {})", newUser);
        try {
            if(!newUser.getPassword().equals(confirmPassword)) {
                throw new PasswordsNotMatchException();
            }
            userService.saveUser(newUser);
            showMessage(Messages.USER_SAVE_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch (UserNotFoundException userNotFound) {
            showMessage(Messages.USER_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch (PasswordsNotMatchException passwordsNotMatch) {
            showMessage(Messages.PASSWORDS_NOT_MATCH, FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("saveUser() ... done");
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.info("getUserById(userId = {})", userId);
        User result = null;
        try {
            result = userService.findById(userId);
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getUserById() result = {} ... done", result);
        return result;
    }

    @Override
    public List<User> getOwners() {
        LOGGER.info("getOwners()");
        List<User> result = new ArrayList<>();
        try {
            result.addAll(userService.getAllUser());
        } catch (Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getOwners() result = {} ... done", result);
        return result;
    }

    @Override
    public void deleteUser(User selectedUser) {
        LOGGER.info("deleteUser(selectedUser = {})", selectedUser);
        try {
            userService.deleteUser(selectedUser.getUserId());
            showMessage(Messages.DELETE_USER_SUCCESS, FacesMessage.SEVERITY_INFO);
        } catch(VehicleNotFoundException vehicleNotFound) {
            showMessage(Messages.USER_NOT_FOUND, FacesMessage.SEVERITY_ERROR);
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("getOwners() ... done");
    }

    @Override
    public List<User> searchUser(User searchUserEntity) {
        LOGGER.info("searchUser(searchUserEntity = {})", searchUserEntity);
        List<User> result = new ArrayList<>();
        try {
            if(searchUserEntity.getName() == null) {
                result.addAll(userService.getAllUser());
            } else {
                result.addAll(userService.searchUserByParams(searchUserEntity));
            }
        } catch(Exception e) {
            showMessage(Messages.INTERNAL_SERVER_ERROR, FacesMessage.SEVERITY_ERROR);
        }
        LOGGER.info("searchUser() result = {} ... done", result);
        return result;
    }
}

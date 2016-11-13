package pathfinder.ui.uilogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.model.nodes.User;
import pathfinder.services.UserService;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * A felhasználók kezelésének kliens oldali üzleti logikájának tesztjei.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class, SecurityContextHolder.class})
public class UserBeanTest {
    private static final String loggedInUserName = "Admin";
    private static final String loggedInUserPassword = "abcd1234";
    private UserBeanImpl userBean;
    private User user;
    @Mock
    private UserService userService;
    @Mock
    private FacesContext facesContext;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    private ArgumentCaptor<String> clientIdCaptor;
    private ArgumentCaptor<FacesMessage> facesMessageCaptor;

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        userBean = new UserBeanImpl();
        user = new User();
        user.setName(loggedInUserName);
        user.setPassword(loggedInUserPassword);
    }

    private void initializeMocks() {
        // A mockok létrehozása, illetve a válaszok beállítása.
        initMocks(this);
        mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(loggedInUserName);
        when(userService.saveUser(any(User.class))).then(returnsFirstArg());
        when(userService.findByUserName(any(String.class))).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return user;
            }
        });
        when(userService.getAllUser()).thenAnswer(new Answer<List<User>>() {
            @Override
            public List<User> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<User> result = new ArrayList<>();
                result.add(new User());
                result.add(new User());
                return result;
            }
        });
        when(userService.searchUserByParams(any(User.class))).thenAnswer(new Answer<List<User>>() {
            @Override
            public List<User> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<User> result = new ArrayList<>();
                result.add((User) invocationOnMock.getArguments()[0]);
                return result;
            }
        });

        // Captorok a FacesMessage objektumok elkapása miatt.
        clientIdCaptor = ArgumentCaptor.forClass(String.class);
        facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
    }

    @Test
    public void testGetLoggedInUserNotFoundException() throws Exception {
        userBean.setUserService(userService);
        when(userService.findByUserName(any(String.class))).thenReturn(null);
        userBean.getLoggedInUser();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testGetLoggedInUser() throws Exception {
        userBean.setUserService(userService);
        when(userService.findByUserName(any(String.class))).thenReturn(user);
        User result = userBean.getLoggedInUser();
        String resultName = result.getName();
        String expectedName = loggedInUserName;
        Assert.assertEquals(expectedName, resultName);
    }

    @Test
    public void testSearchUserByParams() throws Exception {
        userBean.setUserService(userService);
        List<User> resultList = userBean.searchUser(user);
        int resultElements = resultList.size();
        int expectedFoundElements = 1;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchUserGetAll() throws Exception {
        userBean.setUserService(userService);
        user.setName(null);
        List<User> resultList = userBean.searchUser(user);
        int resultElements = resultList.size();
        int expectedFoundElements = 2;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchUserException() throws Exception {
        userBean.setUserService(userService);
        when(userService.getAllUser()).thenThrow(UserNotFoundException.class);
        user.setName(null);
        userBean.searchUser(user);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSaveUserSuccess() throws Exception {
        userBean.setUserService(userService);
        when(userService.saveUser(any(User.class))).thenReturn(null);
        userBean.saveUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_SAVE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSaveUserPasswordNotMatchException() throws Exception {
        userBean.setUserService(userService);
        userBean.saveUser(user, "abcd");
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.PASSWORDS_NOT_MATCH);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSaveUserUserNotFoundException() throws Exception {
        userBean.setUserService(userService);
        when(userService.saveUser(any(User.class))).thenThrow(UserNotFoundException.class);
        userBean.saveUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSaveUserInternalServerErrorException() throws Exception {
        userBean.setUserService(userService);
        when(userService.saveUser(any(User.class))).thenThrow(Exception.class);
        userBean.saveUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testModifyUserSuccess() throws Exception {
        userBean.setUserService(userService);
        when(userService.modifyUser(any(Long.class), any(User.class))).thenReturn(null);
        userBean.modifyUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_MODIFICATION_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testModifyUserPasswordNotMatchException() throws Exception {
        userBean.setUserService(userService);
        userBean.modifyUser(user, "abcd");
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.PASSWORDS_NOT_MATCH);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testModifyUserUserNotFoundException() throws Exception {
        userBean.setUserService(userService);
        when(userService.modifyUser(any(Long.class), any(User.class))).thenThrow(UserNotFoundException.class);
        userBean.modifyUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testModifyInternalServerErrorException() throws Exception {
        userBean.setUserService(userService);
        when(userService.modifyUser(any(Long.class), any(User.class))).thenThrow(Exception.class);
        userBean.modifyUser(user, loggedInUserPassword);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        userBean.setUserService(userService);
        userService.deleteUser(any(Long.class));
        user.setUserId(1L);
        userBean.deleteUser(user);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.DELETE_USER_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void deleteUserNotFoundException() throws Exception {
        userBean.setUserService(userService);
        doThrow(new UserNotFoundException()).when(userService).deleteUser(any(Long.class));
        userBean.deleteUser(user);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.USER_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

}

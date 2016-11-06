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
        user.setPassword("abcd1234");
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

}

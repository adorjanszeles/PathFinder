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
import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.badrequest.VehicleBadRequestException;
import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.PathFinderService;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Az útvonal generálás kliens oldali üzleti logikájának tesztjei.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class })
public class PathBeanTest {
    private PathBeanImpl pathBean;
    @Mock
    private PathFinderService pathFinderService;
    @Mock
    private FacesContext facesContext;
    private ArgumentCaptor<String> clientIdCaptor;
    private ArgumentCaptor<FacesMessage> facesMessageCaptor;

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        pathBean = new PathBeanImpl();
    }

    private void initializeMocks() {
        // A mockok létrehozása, illetve a válaszok beállítása.
        initMocks(this);
        mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        // Captorok a FacesMessage objektumok elkapása miatt.
        clientIdCaptor = ArgumentCaptor.forClass(String.class);
        facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
    }

    @Test
    public void generatePathSuccess() throws Exception {
        pathBean.setPathService(pathFinderService);
        when(pathFinderService.getPath(any(City.class), any(City.class), any(Vehicle.class))).thenAnswer(new Answer<Path>() {
            @Override
            public Path answer(InvocationOnMock invocationOnMock) throws Throwable {
                Path path = new Path();
                path.setSumLength(1500L);
                return path;
            }
        });
        Path path = pathBean.generatePath(new City(), new City(), new Vehicle());
        Long expectedSumLength = 1500L;
        Long resultSumLength = path.getSumLength();
        Assert.assertEquals(expectedSumLength, resultSumLength);
    }

    @Test
    public void generatePathNoFromOrToCityException() throws Exception {
        pathBean.setPathService(pathFinderService);
        when(pathFinderService.getPath(any(City.class), any(City.class), any(Vehicle.class))).thenThrow(CityBadRequestException.class);
        pathBean.generatePath(new City(), new City(), new Vehicle());
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.FROM_OR_TO_CITY_NULL);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void generatePathNoVehicleException() throws Exception {
        pathBean.setPathService(pathFinderService);
        when(pathFinderService.getPath(any(City.class), any(City.class), any(Vehicle.class))).thenThrow(VehicleBadRequestException.class);
        pathBean.generatePath(new City(), new City(), new Vehicle());
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.VEHICLE_NULL);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void generatePathNoFoundPathException() throws Exception {
        pathBean.setPathService(pathFinderService);
        when(pathFinderService.getPath(any(City.class), any(City.class), any(Vehicle.class))).thenThrow(NoPathForThisParameters.class);
        pathBean.generatePath(new City(), new City(), new Vehicle());
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.NO_PATH_WITH_THIS_PARAMETER);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void generatePathInternalServerErrorException() throws Exception {
        pathBean.setPathService(pathFinderService);
        when(pathFinderService.getPath(any(City.class), any(City.class), any(Vehicle.class))).thenThrow(Exception.class);
        pathBean.generatePath(new City(), new City(), new Vehicle());
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }
}

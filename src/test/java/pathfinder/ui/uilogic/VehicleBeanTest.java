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
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.VehicleService;
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
 * A járművek kliens oldali üzleti logikájának tesztjei.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class})
public class VehicleBeanTest {
    private VehicleBeanImpl vehicleBean;
    private Vehicle vehicle;
    @Mock
    private VehicleService vehicleService;
    @Mock
    private FacesContext facesContext;
    private ArgumentCaptor<String> clientIdCaptor;
    private ArgumentCaptor<FacesMessage> facesMessageCaptor;

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        vehicleBean = new VehicleBeanImpl();
        vehicle = new Vehicle();
        vehicle.setPlateNumber("ABC-123");
        vehicle.setHeight(12L);
        vehicle.setLength(10L);
        vehicle.setWeight(1500L);
//        vehicle.setVehicleId(1L);
    }

    private void initializeMocks() {
        // A mockok létrehozása, illetve a válaszok beállítása.
        initMocks(this);
        mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(vehicleService.saveVehicle(any(Vehicle.class))).then(returnsFirstArg());
        when(vehicleService.searchVehicleByParams(any(Vehicle.class))).thenAnswer(new Answer<List<Vehicle>>() {
            @Override
            public List<Vehicle> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Vehicle> result = new ArrayList<>();
                result.add((Vehicle) invocationOnMock.getArguments()[0]);
                return result;
            }
        });
        when(vehicleService.getAllVehicles()).thenAnswer(new Answer<List<Vehicle>>() {
            @Override
            public List<Vehicle> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Vehicle> result = new ArrayList<>();
                result.add(new Vehicle());
                result.add(new Vehicle());
                return result;
            }
        });

        // Captorok a FacesMessage objektumok elkapása miatt.
        clientIdCaptor = ArgumentCaptor.forClass(String.class);
        facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
    }

    @Test
    public void testPersistRouteInternalServerError() throws Exception {
        vehicleBean.setVehicleService(null);
        vehicleBean.persistVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteSaveSuccess() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        vehicle.setVehicleId(null);
        vehicleBean.persistVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.SAVE_VEHICLE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteModifySuccess() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        vehicle.setVehicleId(1L);
        vehicleBean.persistVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.MODIFY_VEHICLE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteNotFound() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenThrow(VehicleNotFoundException.class);
        vehicle.setVehicleId(null);
        vehicleBean.persistVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.VEHICLE_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSearchRouteByParams() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        List<Vehicle> resultList = vehicleBean.searchVehicle(vehicle);
        int resultElements = resultList.size();
        int expectedFoundElements = 1;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchRouteGetAll() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        vehicle.setPlateNumber(null);
        List<Vehicle> resultList = vehicleBean.searchVehicle(vehicle);
        int resultElements = resultList.size();
        int expectedFoundElements = 2;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchRouteException() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        when(vehicleService.getAllVehicles()).thenThrow(VehicleNotFoundException.class);
        vehicle.setPlateNumber(null);
        vehicleBean.searchVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteRouteSuccess() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        vehicleService.deleteVehicle(any(Long.class));
        vehicleBean.deleteVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.DELETE_VEHICLE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteCityNotFound() throws Exception {
        vehicleBean.setVehicleService(vehicleService);
        doThrow(new VehicleNotFoundException()).when(vehicleService).deleteVehicle(any(Long.class));
        vehicleBean.deleteVehicle(vehicle);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.VEHICLE_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }
}

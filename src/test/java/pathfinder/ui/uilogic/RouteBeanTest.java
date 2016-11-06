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
import pathfinder.exceptions.notfound.RouteNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;
import pathfinder.services.RouteService;
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
 * Az út kezelésének kliens oldali üzleti logikájának tesztjei.
 * 
 * @author Széles Adorján
 * Date: 2016. 11. 06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class })
public class RouteBeanTest {
    private RouteBeanImpl routeBean;
    private Route route;
    @Mock
    private RouteService routeService;
    @Mock
    private FacesContext facesContext;
    private ArgumentCaptor<String> clientIdCaptor;
    private ArgumentCaptor<FacesMessage> facesMessageCaptor;

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        routeBean = new RouteBeanImpl();
        route = new Route();
        route.setName("Budapest út");
        route.setDestinationCity(new City());
        route.setStartingCity(new City());
        route.setLength(120L);
        route.setMaxHeight(15L);
    }

    private void initializeMocks() {
        // A mockok létrehozása, illetve a válaszok beállítása.
        initMocks(this);
        mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(routeService.saveRoute(any(Route.class))).then(returnsFirstArg());
        when(routeService.searchRouteByParams(any(Route.class))).thenAnswer(new Answer<List<Route>>() {
            @Override
            public List<Route> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Route> result = new ArrayList<>();
                result.add((Route) invocationOnMock.getArguments()[0]);
                return result;
            }
        });
        when(routeService.getAllRoute()).thenAnswer(new Answer<List<Route>>() {
            @Override
            public List<Route> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Route> result = new ArrayList<>();
                result.add(new Route());
                result.add(new Route());
                return result;
            }
        });

        // Captorok a FacesMessage objektumok elkapása miatt.
        clientIdCaptor = ArgumentCaptor.forClass(String.class);
        facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
    }

    @Test
    public void testPersistRouteInternalServerError() throws Exception {
        routeBean.setRouteService(null);
        routeBean.persistRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteSaveSuccess() throws Exception {
        routeBean.setRouteService(routeService);
        route.setRouteId(null);
        routeBean.persistRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.SAVE_ROUTE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteModifySuccess() throws Exception {
        routeBean.setRouteService(routeService);
        route.setRouteId(1L);
        routeBean.persistRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.MODIFY_ROUTE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistRouteNotFound() throws Exception {
        routeBean.setRouteService(routeService);
        when(routeService.saveRoute(any(Route.class))).thenThrow(RouteNotFoundException.class);
        route.setRouteId(null);
        routeBean.persistRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.ROUTE_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSearchRouteByParams() throws Exception {
        routeBean.setRouteService(routeService);
        List<Route> resultList = routeBean.searchRoute(route);
        int resultElements = resultList.size();
        int expectedFoundElements = 1;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchRouteGetAll() throws Exception {
        routeBean.setRouteService(routeService);
        route.setName(null);
        List<Route> resultList = routeBean.searchRoute(route);
        int resultElements = resultList.size();
        int expectedFoundElements = 2;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchRouteException() throws Exception {
        routeBean.setRouteService(routeService);
        when(routeService.getAllRoute()).thenThrow(RouteNotFoundException.class);
        route.setName(null);
        routeBean.searchRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteRouteSuccess() throws Exception {
        routeBean.setRouteService(routeService);
        routeService.deleteRoute(any(Long.class));
        routeBean.deleteRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.DELETE_ROUTE_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteCityNotFound() throws Exception {
        routeBean.setRouteService(routeService);
        doThrow(new RouteNotFoundException()).when(routeService).deleteRoute(any(Long.class));
        routeBean.deleteRoute(route);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.ROUTE_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

}

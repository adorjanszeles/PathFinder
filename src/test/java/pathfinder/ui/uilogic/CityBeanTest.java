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
import pathfinder.exceptions.notfound.CityNotFoundException;
import pathfinder.model.nodes.City;
import pathfinder.services.CityService;
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
 * A városokkal kapcsolatos üzleit logika tesztje.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class })
public class CityBeanTest {
    private CityBeanImpl cityBean;
    private City city;
    @Mock
    private CityService cityService;
    @Mock
    private FacesContext facesContext;
    private ArgumentCaptor<String> clientIdCaptor;
    private ArgumentCaptor<FacesMessage> facesMessageCaptor;

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        cityBean = new CityBeanImpl();
        city = new City();
        city.setName("Budapest");
    }

    private void initializeMocks() {
        // A mockok létrehozása, illetve a válaszok beállítása.
        initMocks(this);
        mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(cityService.saveCity(any(City.class))).then(returnsFirstArg());
        when(cityService.findCitiesByParams(any(City.class))).thenAnswer(new Answer<List<City>>() {
            @Override
            public List<City> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<City> result = new ArrayList<>();
                result.add((City) invocationOnMock.getArguments()[0]);
                return result;
            }
        });
        when(cityService.getAllCities()).thenAnswer(new Answer<List<City>>() {
            @Override
            public List<City> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<City> result = new ArrayList<>();
                result.add(new City());
                result.add(new City());
                return result;
            }
        });

        // Captorok a FacesMessage objektumok elkapása miatt.
        clientIdCaptor = ArgumentCaptor.forClass(String.class);
        facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
    }

    @Test
    public void testPersistCityInternalServerError() throws Exception {
        cityBean.setCityService(null);
        city.setCityId(1L);
        cityBean.persistCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistCitySaveSuccess() throws Exception {
        cityBean.setCityService(cityService);
        city.setCityId(null);
        cityBean.persistCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.SAVE_CITY_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistCityModifySuccess() throws Exception {
        cityBean.setCityService(cityService);
        city.setCityId(1L);
        cityBean.persistCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.MODIFY_CITY_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testPersistCityNotFound() throws Exception {
        cityBean.setCityService(cityService);
        when(cityService.saveCity(any(City.class))).thenThrow(CityNotFoundException.class);
        city.setCityId(null);
        cityBean.persistCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.CITY_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testSearchCityByParams() throws Exception {
        cityBean.setCityService(cityService);
        List<City> resultList = cityBean.searchCities(city);
        int resultElements = resultList.size();
        int expectedFoundElements = 1;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchCityGetAll() throws Exception {
        cityBean.setCityService(cityService);
        city.setName(null);
        List<City> resultList = cityBean.searchCities(city);
        int resultElements = resultList.size();
        int expectedFoundElements = 2;
        Assert.assertEquals(expectedFoundElements, resultElements);
    }

    @Test
    public void testSearchCityException() throws Exception {
        cityBean.setCityService(cityService);
        when(cityService.getAllCities()).thenThrow(CityNotFoundException.class);
        city.setName(null);
        cityBean.searchCities(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteCitySuccess() throws Exception {
        cityBean.setCityService(cityService);
        cityService.deleteCity(any(Long.class));
        cityBean.deleteCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.DELETE_CITY_SUCCESS);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void testDeleteCityNotFound() throws Exception {
        cityBean.setCityService(cityService);
        doThrow(new CityNotFoundException()).when(cityService).deleteCity(any(Long.class));
        cityBean.deleteCity(city);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        FacesMessage capturedFacesMessage = facesMessageCaptor.getValue();
        String resultMessage = capturedFacesMessage.getSummary();
        String expectedMessage = FacesCommon.getMessage(facesContext, Messages.CITY_NOT_FOUND);
        Assert.assertEquals(expectedMessage, resultMessage);
    }

}

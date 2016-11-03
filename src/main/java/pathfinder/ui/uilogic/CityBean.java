package pathfinder.ui.uilogic;

import pathfinder.model.nodes.City;

import java.util.List;

/**
 * A városok üzleti logikáját tartalmazó bean interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public interface CityBean {
    /**
     * Város perzisztálása, ha van city id, akkor a meglévőt módosítja, egyébként újat hoz létre.
     *
     * @param selectedCity A módosítandó város
     */
    void persistCity(City selectedCity);

    /**
     * Város keresése.
     *
     * @param searchCityEntity A keresési feltételek
     * @return A keresésnek megfelelő városok
     */
    List<City> searchCities(City searchCityEntity);

    /**
     * Város törlése.
     *
     * @param selectedCity A törlendő város
     */
    void deleteCity(City selectedCity);

    /**
     * Lekér egy várost az azonosítója alapján.
     *
     * @param cityId A város azonosítója
     * @return A város példány
     */
    City getCityById(Long cityId);

    /**
     * Lekéri a városokat a szervertől.
     *
     * @return Az összes város egy listában
     */
    List<City> getAllCity();

}

package pathfinder.ui.uilogic;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;

import java.util.List;

/**
 * A városok üzleti logikáját tartalmazó bean interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public interface CityBean {
    /**
     * A városokba vezető utak listáját kéri le a szervertől.
     *
     * @return Egy utakat tartalmazó lista
     */
    List<Route> getRoutesToCity();

    /**
     * A városból kijövő utak listáját kéri le a szervertől.
     *
     * @return Egy utakat tartalmazó lista
     */
    List<Route> getRoutesFromCity();

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
}

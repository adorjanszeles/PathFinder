package pathfinder.ui.uilogic;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Route;

import java.util.List;

/**
 * Az utakkal kapcsolatos műveletek üzleti logikájának interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 29.
 */
public interface RouteBean {
    /**
     * Út törlése.
     *
     * @param route A törlendő út
     */
    void deleteRoute(Route route);

    /**
     * Út perzisztálása, ha van id, akkor a meglévőt módosítja, egyébként újat hoz létre.
     *
     * @param route A módosítandó út
     */
    void persistRoute(Route route);

    /**
     * Utak keresése.
     *
     * @return Az utakat tartalmazó lista
     * @param searchRouteEntity A keresési feltételekt tartalmazó bean
     */
    List<Route> searchRoute(Route searchRouteEntity);

    /**
     * Lekéri a városokat a szervertől.
     *
     * @return Az összes város egy listában
     */
    List<City> getAllCity();
}

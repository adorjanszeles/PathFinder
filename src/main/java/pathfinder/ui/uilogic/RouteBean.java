package pathfinder.ui.uilogic;

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
     * Lekér egy várost az azonosítója alapján.
     *
     * @param routeId Az út azonosítója
     * @return Az út példány
     */
    Route getRouteById(Long routeId);

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
}

package pathfinder.ui.uilogic;

import pathfinder.model.nodes.Vehicle;

import java.util.List;

/**
 * A járművek kezeléséért felelős üzelti logika interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 23.
 */
public interface VehicleBean {
    /**
     * Járművek keresése.
     *
     * @return A keresési találatok listája
     * @param searchVehicleEntity
     */
    List<Vehicle> searchVehicle(Vehicle searchVehicleEntity);

    /**
     * Jármű törlése.
     *
     * @param selectedVehicle A törölni kívánt jármű
     */
    void deleteVehicle(Vehicle selectedVehicle);

    /**
     * Jármű perzisztálása, ha van id, akkor módosítja a meglévőt, egyébként újat hoz létre.
     *
     * @param selectedVehicle A perzisztálandó jármű
     */
    void persistVehicle(Vehicle selectedVehicle);

    /**
     * Lekéri az összes járművet az adatbázisból.
     *
     * @return A járművek egy listában
     */
    List<Vehicle> getAllVehicle();

    /**
     * Lekér egy jármű entitást az azonosítója alapján.
     *
     * @param vehicleId A jármű azonosítója
     * @return A jármű entitás
     */
    Vehicle getVehicleById(Long vehicleId);
}

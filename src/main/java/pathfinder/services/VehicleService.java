package pathfinder.services;

import java.util.List;

import pathfinder.model.nodes.Vehicle;

/**
 * Járművek kezelésére szolgáló Service.
 * 
 * @author Kiss László
 *
 */
public interface VehicleService {

	/**
	 * Jármű törlése.
	 * 
	 * @param vehicleId
	 */
	void deleteVehicle(Long vehicleId);

	/**
	 * Jármű keresése id alapján.
	 * 
	 * @param vehicleId
	 * @return
	 */
	Vehicle findById(Long vehicleId);

	/**
	 * Összes jármű lekérdezése.
	 * 
	 * @return
	 */
	List<Vehicle> getAllVehicles();

	/**
	 * 
	 * 
	 * @param vehicleId
	 * @return
	 */
	Vehicle getVehicleById(Long vehicleId);

	/**
	 * User járműveinek lekérdezése.
	 * 
	 * @param userId
	 * @return
	 */
	List<Vehicle> getVehiclesOfUser(Long userId);

	/**
	 * Jármű módosítása.
	 * 
	 * @param vehicleId
	 * @param vehicle
	 * @return
	 */
	Vehicle modifyVehicle(Long vehicleId, Vehicle vehicle);

	/**
	 * Jármű mentése.
	 * 
	 * @param vehicle
	 * @return
	 */
	Vehicle saveVehicle(Vehicle vehicle);

	/**
	 * Járművek keresése rendszám alapján.
	 * 
	 * @param searchVehicleEntity
	 * @return
	 */
	List<Vehicle> searchVehicleByParams(Vehicle searchVehicleEntity);

}

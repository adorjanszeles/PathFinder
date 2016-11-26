package pathfinder.service.test;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import pathfinder.exceptions.badrequest.VehicleBadRequestException;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.exceptions.unauthorized.UnauthorizedException;
import pathfinder.model.nodes.Vehicle;
import pathfinder.services.VehicleService;

public class VehicleServiceTest extends PathfinderServiceTestBase {

	@Autowired
	private VehicleService vehicleService;

	/**
	 * Nem létező jármű törlése.
	 */
	@Test(expected = VehicleNotFoundException.class)
	public void deleteNotExistingVehicle() {
		this.vehicleService.deleteVehicle(Long.MAX_VALUE);
	}

	/**
	 * Jármű törlése.
	 */
	@Test
	public void deleteTest() {
		Vehicle newVehicle = this.createNewVehicle();
		Long vehicleId = newVehicle.getVehicleId();
		this.vehicleService.deleteVehicle(vehicleId);
		Assert.isNull(this.vehicleRepository.findOne(vehicleId));
	}

	/**
	 * Összes jármű lekérdezése.
	 */
	@Test
	public void findAllVehicleTest() {
		Vehicle firstVehicle = this.createNewVehicle();
		Vehicle secondVehicle = this.createNewVehicle();
		List<Vehicle> foundVehicles = this.vehicleService.getAllVehicles();
		Assert.notNull(foundVehicles);
		Assert.notEmpty(foundVehicles);
		List<Long> foundVehicleIds = foundVehicles.stream().map(vehicle -> vehicle.getVehicleId())
				.collect(Collectors.toList());
		Assert.isTrue(foundVehicleIds.contains(firstVehicle.getVehicleId())
				&& foundVehicleIds.contains(secondVehicle.getVehicleId()));
	}

	/**
	 * Jármű keresése nem létező id-val.
	 */
	@Test(expected = VehicleNotFoundException.class)
	public void findByIdNotFoundTest() {
		this.vehicleService.getVehicleById(Long.MAX_VALUE);
	}

	/**
	 * Azonosító alapú keresés.
	 */
	@Test
	public void findByIdTest() {
		Vehicle newVehicle = this.createNewVehicle();
		Vehicle foundVehicle = this.vehicleService.getVehicleById(newVehicle.getVehicleId());
		Assert.notNull(foundVehicle);
		Assert.notNull(foundVehicle.getVehicleId());
		Assert.isTrue(foundVehicle.getVehicleId().equals(newVehicle.getVehicleId()));
	}

	/**
	 * Nem létező felasználó járműveinek lekérdezése.
	 */
	@Test(expected = UserNotFoundException.class)
	public void getVehiclesOfNotExistingUserTest() {
		this.vehicleService.getVehiclesOfUser(Long.MAX_VALUE);
	}

	/**
	 * Felhasználó járműveinek lekérdezése.
	 */
	@Test
	public void getVehiclesOfUserTest() {
		Vehicle newVehicle = this.createNewVehicle();
		List<Vehicle> foundVehicles = this.vehicleService.getVehiclesOfUser(newVehicle.getOwner().getUserId());
		Assert.notNull(foundVehicles);
		List<Long> vehicleIds = foundVehicles.stream().map(vehicle -> vehicle.getVehicleId())
				.collect(Collectors.toList());
		Assert.isTrue(vehicleIds.contains(newVehicle.getVehicleId()));
	}

	/**
	 * Nem létező jármű módosítása.
	 */
	@Test(expected = VehicleNotFoundException.class)
	public void modifyNotExistingVehicleTest() {
		Vehicle newVehicle = this.createNewVehicle();
		this.vehicleService.modifyVehicle(Long.MAX_VALUE, newVehicle);
	}

	/**
	 * Jármű módosítása hiányzó rendszámmal.
	 */
	@Test(expected = VehicleBadRequestException.class)
	public void modifyVehicleBadRequestTest() {
		Vehicle newVehicle = this.createNewVehicle();
		newVehicle.setPlateNumber(null);
		this.vehicleService.modifyVehicle(newVehicle.getVehicleId(), newVehicle);
	}

	/**
	 * Jármű módosítása.
	 */
	@Test
	public void modifyVehicleTest() {
		Vehicle newVehicle = this.createNewVehicle();
		String modifiedPlateNumber = "QWE456";
		newVehicle.setPlateNumber(modifiedPlateNumber);
		Vehicle modifiedVehicle = this.vehicleService.modifyVehicle(newVehicle.getVehicleId(), newVehicle);
		Assert.notNull(modifiedVehicle);
		Assert.isTrue(modifiedVehicle.getVehicleId().equals(newVehicle.getVehicleId())
				&& modifiedPlateNumber.equals(modifiedVehicle.getPlateNumber()));
	}

	/**
	 * Új jármű mentése.
	 */
	@Test
	public void saveVechileTest() {
		Vehicle vehicle = new Vehicle();
		vehicle.setHeight(100L);
		vehicle.setLength(100L);
		vehicle.setOwner(this.createNewUser());
		vehicle.setPlateNumber("UHK343");
		vehicle.setWeight(100L);
		vehicle.setWidth(100L);
		Vehicle newVehicle = this.vehicleService.saveVehicle(vehicle);
		Assert.notNull(newVehicle);
		Assert.notNull(newVehicle.getVehicleId());
	}

	/**
	 * Új jármű mentése rendszám nélkül.
	 */
	@Test(expected = VehicleBadRequestException.class)
	public void saveVehicleBadRequestTest() {
		Vehicle vehicle = new Vehicle();
		vehicle.setHeight(100L);
		vehicle.setLength(100L);
		vehicle.setOwner(this.createNewUser());
		vehicle.setWeight(100L);
		vehicle.setWidth(100L);
		this.vehicleService.saveVehicle(vehicle);
	}

	/**
	 * Jármű keresése rendszám alapján.
	 */
	@Test
	public void searchVehicleTest() {
		Vehicle newVehicle = this.createNewVehicle();
		String plateNumber = newVehicle.getPlateNumber();

		Vehicle searchEntity = new Vehicle();
		searchEntity.setPlateNumber(plateNumber);
		List<Vehicle> foundVehicles = this.vehicleService.searchVehicleByParams(searchEntity);
		Assert.notNull(foundVehicles);
		List<Long> vehicleIds = foundVehicles.stream().map(vehicle -> vehicle.getVehicleId())
				.collect(Collectors.toList());
		Assert.isTrue(vehicleIds.contains(newVehicle.getVehicleId()));
	}

	/**
	 * Felhasználó járműveinek lekérdezése megfelelő jogosultság nélkül.
	 */
	@Test(expected = UnauthorizedException.class)
	public void unauthorizedGetVehiclesOfUserTest() {
		this.authenticateAsUser();
		Vehicle newVehicle = this.createNewVehicle();
		this.vehicleService.getVehiclesOfUser(newVehicle.getOwner().getUserId());
	}

	/**
	 * Jármű módosítása jogosultság nélkül.
	 */
	@Test(expected = UnauthorizedException.class)
	public void unauthorizedModifyVehicleTest() {
		this.authenticateAsUser();
		Vehicle newVehicle = this.createNewVehicle();
		this.vehicleService.modifyVehicle(newVehicle.getVehicleId(), newVehicle);
	}
}

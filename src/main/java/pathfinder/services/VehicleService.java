package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import pathfinder.common.RoleEnum;
import pathfinder.exceptions.badrequest.UserBadRequestException;
import pathfinder.exceptions.badrequest.VehicleBadRequestException;
import pathfinder.exceptions.notfound.UserNotFoundException;
import pathfinder.exceptions.notfound.VehicleNotFoundException;
import pathfinder.exceptions.unauthorized.UnauthorizedException;
import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.model.repositories.UserRepository;
import pathfinder.model.repositories.VehicleRepository;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class VehicleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private VehicleRepository vehicleRepository;

	private boolean canUserModifyOrDeleteVehicle(User user, Vehicle vehicle) {
		return user.getRole() == RoleEnum.ROLE_ADMIN || user.getUserId()
				.equals(this.vehicleRepository.findOwnerOfVehicle(vehicle.getVehicleId()).getUserId());
	}

	public void deleteVehicle(Long vehicleId) {
		User loggedInUser = this.userService.getLoggedInUser();
		Assert.notNull(loggedInUser);
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicleId);
		if (persistedVehicle == null) {
			throw new VehicleNotFoundException();
		}
		if (this.canUserModifyOrDeleteVehicle(loggedInUser, persistedVehicle)) {
			this.vehicleRepository.delete(persistedVehicle);
		} else {
			throw new UnauthorizedException();
		}
	}

	private Vehicle doSaveVehicle(Vehicle persistedVehicle, Vehicle vehicleFromUI) {
		User owner = this.userRepository.findOne(vehicleFromUI.getOwner().getUserId());
		if (owner == null) {
			throw new UserBadRequestException();
		}
		persistedVehicle.setPlateNumber(vehicleFromUI.getPlateNumber());
		persistedVehicle.setHeight(vehicleFromUI.getHeight());
		persistedVehicle.setLength(vehicleFromUI.getLength());
		persistedVehicle.setWeight(vehicleFromUI.getWeight());
		persistedVehicle.setWidth(vehicleFromUI.getWidth());
		persistedVehicle.setOwner(owner);
		return this.vehicleRepository.save(persistedVehicle);
	}

	public Vehicle findById(Long vehicleId) {
		return this.vehicleRepository.findOne(vehicleId);
	}

	public List<Vehicle> getAllVehicles() {
		List<Vehicle> result = new ArrayList<Vehicle>();
		Iterator<Vehicle> iterator = this.vehicleRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Vehicle vehicle = iterator.next();
			result.add(vehicle);
		}
		return result;
	}

	public Vehicle getVehicleById(Long vehicleId) {
		Vehicle vehicle = this.vehicleRepository.findOne(vehicleId);
		if (vehicle == null) {
			throw new VehicleNotFoundException();
		}
		User loggedInUser = this.userService.getLoggedInUser();
		if (!this.canUserModifyOrDeleteVehicle(loggedInUser, vehicle)) {
			throw new UnauthorizedException();
		}
		return vehicle;
	}

	public List<Vehicle> getVehiclesOfUser(Long userId) {
		User user = this.userRepository.findOne(userId);
		if (user == null) {
			throw new UserNotFoundException();
		}
		User loggedInUser = this.userService.getLoggedInUser();
		if (loggedInUser.getRole() != RoleEnum.ROLE_ADMIN && !loggedInUser.getUserId().equals(userId)) {
			throw new UnauthorizedException();
		}
		return this.vehicleRepository.findVehiclesOfUser(userId);
	}

	public Vehicle modifyVehicle(Long vehicleId, Vehicle vehicle) {
		this.validateVehicle(vehicle);
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicleId);
		if (persistedVehicle == null) {
			throw new VehicleNotFoundException();
		}
		if (!this.canUserModifyOrDeleteVehicle(this.userService.getLoggedInUser(), persistedVehicle)) {
			throw new UnauthorizedException();
		}
		return this.doSaveVehicle(persistedVehicle, vehicle);
	}

	public Vehicle saveVehicle(Vehicle vehicle) {
		this.validateVehicle(vehicle);
		Vehicle persistedVehicle = new Vehicle();
		return this.doSaveVehicle(persistedVehicle, vehicle);
	}

	public List<Vehicle> searchVehicleByParams(Vehicle searchVehicleEntity) {
		return this.vehicleRepository.findByPlateNumber(searchVehicleEntity.getPlateNumber());
	}

	public void validateVehicle(Vehicle vehicle) {
		if (StringUtils.isEmpty(vehicle.getPlateNumber()) || vehicle.getHeight() == null
				|| vehicle.getHeight().compareTo(0L) <= 0 || vehicle.getLength() == null
				|| vehicle.getLength().compareTo(0L) <= 0 || vehicle.getOwner() == null || vehicle.getWeight() == null
				|| vehicle.getWeight().compareTo(0L) <= 0 || vehicle.getWidth() == null
				|| vehicle.getWidth().compareTo(0L) <= 0) {
			throw new VehicleBadRequestException();
		}
	}
}

package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pathfinder.exceptions.UserNotFoundException;
import pathfinder.exceptions.VehicleNotFoundException;
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
	private VehicleRepository vehicleRepository;

	public void deleteVehicle(Long vehicleId) {
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicleId);
		if (persistedVehicle == null) {
			throw new VehicleNotFoundException();
		}
		this.vehicleRepository.delete(persistedVehicle);
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
		return vehicle;
	}

	public List<Vehicle> getVehiclesOfUser(Long userId) {
		User user = this.userRepository.findOne(userId);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return this.vehicleRepository.findVehiclesOfUser(userId);
	}

	public Vehicle modifyVehicle(Long vehicleId, Vehicle vehicle) {
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicleId);
		if (persistedVehicle == null) {
			throw new VehicleNotFoundException();
		}
		persistedVehicle.setHeight(vehicle.getHeight());
		persistedVehicle.setLength(vehicle.getLength());
		persistedVehicle.setWeight(vehicle.getWeight());
		persistedVehicle.setWidth(vehicle.getWidth());
		User owner = this.userRepository.findOne(vehicle.getOwner().getUserId());
		if (owner == null) {
			// TODO bad message hiba
		}
		// TODO validáció
		persistedVehicle.setOwner(owner);
		this.vehicleRepository.save(persistedVehicle);
		return this.vehicleRepository.save(persistedVehicle);
	}

	public Vehicle saveVehicle(Vehicle vehicle) {
		User owner = this.userRepository.findOne(vehicle.getOwner().getUserId());
		if (owner == null) {
			// Bad message hiba
		}
		// TODO validáció
		Vehicle persistedVehicle = new Vehicle();
		persistedVehicle.setHeight(vehicle.getHeight());
		persistedVehicle.setLength(vehicle.getLength());
		persistedVehicle.setWeight(vehicle.getWeight());
		persistedVehicle.setWidth(vehicle.getWidth());
		persistedVehicle.setOwner(owner);
		return this.vehicleRepository.save(persistedVehicle);
	}

}

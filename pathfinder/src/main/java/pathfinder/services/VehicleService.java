package pathfinder.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;
import pathfinder.model.repositories.UserRepository;
import pathfinder.model.repositories.VehicleRepository;

@Service
@Transactional(readOnly = false,rollbackFor=Exception.class)
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveVehicle(Vehicle vehicle) {
		User owner = this.userRepository.findOne(vehicle.getOwner().getUserId());
		Vehicle persistedVehicle = new Vehicle();
		persistedVehicle.setHeight(vehicle.getHeight());
		persistedVehicle.setLength(vehicle.getLength());
		persistedVehicle.setWeight(vehicle.getWeight());
		persistedVehicle.setWidth(vehicle.getWidth());
		persistedVehicle.setOwner(owner);
		vehicleRepository.save(persistedVehicle);
	}
	
	public Vehicle findById(Long vehicleId) {
		return vehicleRepository.findOne(vehicleId);
	}

	public List<Vehicle> getAllVehicles() {
		List<Vehicle> result = new ArrayList<Vehicle>();
		Iterator<Vehicle> iterator = vehicleRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Vehicle vehicle = iterator.next();
//			User owner = this.vehicleRepository.findOwnerOfVehicle(vehicle.getVehicleId());
//			vehicle.setOwner(owner);
			result.add(vehicle);
		}
		return result;
	}
	
	public Vehicle modifyVehicle(Vehicle vehicle) {
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicle.getVehicleId());
		persistedVehicle.setHeight(vehicle.getHeight());
		persistedVehicle.setLength(vehicle.getLength());
		persistedVehicle.setWeight(vehicle.getWeight());
		persistedVehicle.setWidth(vehicle.getWidth());
		User owner = this.userRepository.findOne(vehicle.getOwner().getUserId());
		if (owner == null) {
			// TODO hiba
		}
		persistedVehicle.setOwner(owner);
		vehicleRepository.save(persistedVehicle);
		return vehicleRepository.save(persistedVehicle);
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		Vehicle persistedVehicle = vehicleRepository.findOne(vehicle.getVehicleId());
		Assert.notNull(persistedVehicle);
		vehicleRepository.delete(persistedVehicle);
	}
	
}

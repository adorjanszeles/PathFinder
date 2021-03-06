package pathfinder.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pathfinder.exceptions.badrequest.CityBadRequestException;
import pathfinder.exceptions.badrequest.VehicleBadRequestException;
import pathfinder.exceptions.notfound.NoPathForThisParameters;
import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;
import pathfinder.model.repositories.CityRepository;
import pathfinder.model.repositories.RouteRepository;
import pathfinder.model.repositories.VehicleRepository;
import pathfinder.services.PathFinderService;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class PathFinderServiceImpl implements PathFinderService {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Override
	public Path getPath(City from, City to, Vehicle vehicle) throws NoPathForThisParameters {
		City fromCity = this.cityRepository.findOne(from.getCityId());
		if (fromCity == null) {
			throw new CityBadRequestException();
		}
		City toCity = this.cityRepository.findOne(to.getCityId());
		if (toCity == null) {
			throw new CityBadRequestException();
		}
		Vehicle persistedVehicle = this.vehicleRepository.findOne(vehicle.getVehicleId());
		if (persistedVehicle == null) {
			throw new VehicleBadRequestException();
		}
		Path path = this.routeRepository.getPathForVehicle(from.getCityId(), to.getCityId(), vehicle.getVehicleId());
		if (path == null) {
			throw new NoPathForThisParameters();
		}
		return path;
	}

}

package pathfinder.model.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pathfinder.model.nodes.User;
import pathfinder.model.nodes.Vehicle;

@Repository
public interface VehicleRepository extends GraphRepository<Vehicle> {

	@Query("MATCH (v:Vehicle) WHERE v.plateNumber = {plateNumber} RETURN v")
	List<Vehicle> findByPlateNumber(@Param("plateNumber") String plateNumber);

	@Query("MATCH (v:Vehicle)-[r:OWNERSHIP]->(u:User) WHERE ID(v)={vehicleId} RETURN u")
	User findOwnerOfVehicle(@Param("vehicleId") Long vehicleId);

	@Query("MATCH (v:Vehicle)-[r:OWNERSHIP]->(u:User) WHERE ID(u)={userId} RETURN v")
	List<Vehicle> findVehiclesOfUser(@Param("userId") Long userId);

}

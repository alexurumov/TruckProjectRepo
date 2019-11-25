package truckmanagementproject.data.repositories.vehicles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.vehicles.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    boolean existsByRegNumber(String regNumber);

    Vehicle getByRegNumber(String regNumber);
}

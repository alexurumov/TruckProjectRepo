package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.vehicles.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle getByRegNumber(String regNumber);
}

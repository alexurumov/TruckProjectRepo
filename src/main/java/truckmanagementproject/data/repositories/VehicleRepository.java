package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}

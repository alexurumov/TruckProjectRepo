package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.users.Driver;

public interface DriverRepository extends JpaRepository<Driver, String> {
    Driver getByUsername(String username);
}

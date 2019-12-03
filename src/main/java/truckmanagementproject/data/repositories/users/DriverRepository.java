package truckmanagementproject.data.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.users.Driver;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    Driver getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByName(String name);

    boolean existsByUsernameAndPassword(String username, String password);

    Driver getByUsernameAndPassword(String username, String password);

    Driver getByName(String driverName);
}

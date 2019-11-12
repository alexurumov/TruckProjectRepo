package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.users.Manager;

public interface ManagerRepository extends JpaRepository<Manager, String> {
}

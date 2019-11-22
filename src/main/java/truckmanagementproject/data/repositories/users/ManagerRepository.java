package truckmanagementproject.data.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.users.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    boolean existsByUsername(String username);

    boolean existsByName(String name);
}

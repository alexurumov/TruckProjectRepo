package truckmanagementproject.data.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.users.Admin;
import truckmanagementproject.data.models.users.Manager;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByUsernameAndPassword(String username, String password);

    Admin getByUsernameAndPassword(String username, String password);
}

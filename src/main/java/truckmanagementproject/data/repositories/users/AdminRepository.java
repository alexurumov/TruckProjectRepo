package truckmanagementproject.data.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.users.Admin;
import truckmanagementproject.data.models.users.Manager;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}

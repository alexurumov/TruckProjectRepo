package truckmanagementproject.data.repositories.milestones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.milestones.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, String> {
    Milestone getById(String id);
}

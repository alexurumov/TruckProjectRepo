package truckmanagementproject.services.services.milestones;

import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;

public interface MilestoneService {
    void addMilestone(AddMilestoneServiceModel collectionModel) throws Exception;

    void updateMilestone(String id);

    MilestoneServiceModel getById(String id);
}

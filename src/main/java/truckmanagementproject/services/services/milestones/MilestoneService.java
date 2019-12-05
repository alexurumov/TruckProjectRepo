package truckmanagementproject.services.services.milestones;

import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;

public interface MilestoneService {
    void addCollection(AddMilestoneServiceModel collectionModel);

    void updateMilestone(String id);

    MilestoneServiceModel getById(String id);
}

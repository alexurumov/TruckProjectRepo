package truckmanagementproject.services.services.milestones;

import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;
import truckmanagementproject.web.models.milestones.AddMilestoneModel;

public interface MilestoneService {
    void addMilestone(AddMilestoneServiceModel collectionModel) throws Exception;

    void updateMilestone(String id) throws Exception;

    MilestoneServiceModel getById(String id);

    boolean isMilestoneValid(AddMilestoneModel addMilestoneModel);
}

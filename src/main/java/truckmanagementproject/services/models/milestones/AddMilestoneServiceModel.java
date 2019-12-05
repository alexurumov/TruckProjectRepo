package truckmanagementproject.services.models.milestones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMilestoneServiceModel {
    private String milestoneType;
    private String locationType;
    private String name;
    private String address;
    private String details;
    private String tripReference;
}

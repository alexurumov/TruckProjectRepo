package truckmanagementproject.web.models.milestones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMilestoneModel {
    private String locationType;
    private String name;
    private String address;
    private String details;
}

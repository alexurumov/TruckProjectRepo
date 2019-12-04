package truckmanagementproject.services.models.milestones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MilestoneServiceModel {
    private String milestoneType;
    private String locationType;
    private String name;
    private String address;
    private String details;
    private String tripReference;
}

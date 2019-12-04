package truckmanagementproject.services.models.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripServiceModel {
    private String vehicleRegNumber;
    private String date;
    private String direction;
    private String driverName;
    private String reference;
    private Boolean adr;
    private List<MilestoneServiceModel> milestones;
}

package truckmanagementproject.web.models.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.web.models.milestones.MilestoneViewModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripViewModel {
    private String vehicleRegNumber;
    private String date;
    private String direction;
    private String driverName;
    private String reference;
    private Boolean adr;
    private Integer emptyKm;
    private Integer tripKm;
    private BigDecimal expensesSum;
    private Integer emptyPallets;
    private Boolean isFinished;
    private BigDecimal price;
    private List<MilestoneViewModel> collections;
    private List<MilestoneViewModel> deliveries;
}

package truckmanagementproject.services.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class VehicleExpenseServiceModel {
    private String id;
    private String type;
    private String date;
    private String country;
    private String picture;
    private String vehicleRegNumber;
    private BigDecimal cost;
}

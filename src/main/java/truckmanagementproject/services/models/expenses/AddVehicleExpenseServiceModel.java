package truckmanagementproject.services.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddVehicleExpenseServiceModel {
    private String type;
    private LocalDate date;
    private String country;
    private String picture;
    private String vehicleRegNumber;
    private BigDecimal cost;
}

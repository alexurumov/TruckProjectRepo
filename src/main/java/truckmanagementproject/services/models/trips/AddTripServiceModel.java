package truckmanagementproject.services.models.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddTripServiceModel {

    private LocalDate date;
    private String direction;
    private String reference;
    private Boolean adr;
    private String driverName;
    private String vehicleRegNumber;
}

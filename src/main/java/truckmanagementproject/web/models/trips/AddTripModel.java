package truckmanagementproject.web.models.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AddTripModel {

    private String date;
    private String direction;
    private String reference;
    private Boolean adr;
    private String driverName;
    private String vehicleRegNumber;
}

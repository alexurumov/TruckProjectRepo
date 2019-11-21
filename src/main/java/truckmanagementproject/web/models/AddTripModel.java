package truckmanagementproject.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTripModel {

    private String tripDate;
    private String tripDirection;
    private String tripReference;
    private String tripIsAdr;
    private String tripDriver;
    private String tripVehicle;
}

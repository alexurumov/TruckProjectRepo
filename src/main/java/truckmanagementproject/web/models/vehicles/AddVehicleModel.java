package truckmanagementproject.web.models.vehicles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AddVehicleModel {

    @Pattern(regexp = "[A-Z]{1,2}[0-9]{4}[A-Z]{2} [\\/] {1}[A-Z]{1,2}[0-9]{4}[A-Z]{2}", message = "Input should be in pattern ##@@@@## / ##@@@@##")
    private String regNumber;
}

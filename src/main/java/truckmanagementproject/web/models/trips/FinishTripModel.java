package truckmanagementproject.web.models.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinishTripModel {
    private Integer emptyKm;
    private Integer tripKm;
    private Integer emptyPallets;
}

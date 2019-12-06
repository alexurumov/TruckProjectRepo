package truckmanagementproject.web.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TripExpenseViewModel {
    private String id;
    private String type;
    private String date;
    private String country;
    private String picture;
    private String tripReference;
    private BigDecimal cost;
}

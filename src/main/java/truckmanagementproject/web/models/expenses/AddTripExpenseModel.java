package truckmanagementproject.web.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AddTripExpenseModel {

    private String type;
    private String date;
    private String country;
    private MultipartFile picture;
    private String tripRef;
    private BigDecimal cost;

}

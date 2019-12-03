package truckmanagementproject.services.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddTripExpenseServiceModel {
    private String type;
    private LocalDate date;
    private String country;
    private String picture;
    private String tripRef;
    private BigDecimal cost;
}

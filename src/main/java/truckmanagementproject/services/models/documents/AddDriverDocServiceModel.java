package truckmanagementproject.services.models.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddDriverDocServiceModel {

    private String type;
    private String picture;
    private String driverName;
    private LocalDate expiryDate;

}

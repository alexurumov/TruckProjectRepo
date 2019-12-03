package truckmanagementproject.web.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddVehicleDocumentModel {

    private String type;
    private String picture;
    private LocalDate expiryDate;
    private String driverUsername;

}
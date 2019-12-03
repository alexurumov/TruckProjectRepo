package truckmanagementproject.services.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddCompanyDocServiceModel {

    private String type;
    private String picture;
    private LocalDate expiryDate;

}
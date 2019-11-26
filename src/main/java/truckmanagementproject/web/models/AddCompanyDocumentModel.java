package truckmanagementproject.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddCompanyDocumentModel {

    private String type;
    private String picture;
    private LocalDate expiryDate;

}

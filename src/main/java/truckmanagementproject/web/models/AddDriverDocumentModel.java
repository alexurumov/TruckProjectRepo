package truckmanagementproject.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddDriverDocumentModel {

    private String type;
    private String picture;

    @Future
    private LocalDate expiryDate;
    private String driverUsername;

}

package truckmanagementproject.services.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripDocumentServiceModel {

    private String id;
    private String type;
    private String picture;
    private String tripReference;

}

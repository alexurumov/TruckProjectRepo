package truckmanagementproject.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.documents.TripDocumentType;

@Getter
@Setter
@NoArgsConstructor
public class AddTripDocServiceModel {

    private String type;
    private String picture;
    private String tripRef;

}

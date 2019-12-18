package truckmanagementproject.services.models.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.documents.TripDocumentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTripDocServiceModel {

    private String type;
    private String picture;
    private String tripRef;

}

package truckmanagementproject.web.models.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTripDocumentModel {

    private String type;
    private MultipartFile picture;
    private String tripRef;

}

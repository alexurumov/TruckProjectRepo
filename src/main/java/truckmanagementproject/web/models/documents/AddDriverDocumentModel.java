package truckmanagementproject.web.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Future;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddDriverDocumentModel {

    private String type;
    private MultipartFile picture;
    private String expiryDate;
    private String driverName;

}

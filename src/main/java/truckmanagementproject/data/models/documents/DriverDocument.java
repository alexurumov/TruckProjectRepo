package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.users.Driver;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Driver")
@Getter
@Setter
@NoArgsConstructor
public class DriverDocument extends Document {

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @Column(name = "driver_document_type")
    @Enumerated(EnumType.STRING)
    private DriverDocumentType driverDocumentType;

    public DriverDocument(String type) {
        super(type);
    }
}

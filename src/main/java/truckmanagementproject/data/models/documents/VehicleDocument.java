package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.vehicles.Vehicle;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Vehicle")
@Getter
@Setter
@NoArgsConstructor
public class VehicleDocument extends Document {

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Column(name = "vehicle_document_type")
    @Enumerated(EnumType.STRING)
    private VehicleDocumentType vehicleDocumentType;

    public VehicleDocument(String type) {
        super(type);
    }
}

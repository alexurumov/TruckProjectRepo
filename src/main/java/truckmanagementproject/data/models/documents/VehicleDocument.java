package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.vehicles.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_documents")
@Getter
@Setter
@NoArgsConstructor
public class VehicleDocument extends Document {

    @Column(name = "vehicle_document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleDocumentType type;

    @Column(name = "expiry_date", nullable = false)
    @Future
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
}

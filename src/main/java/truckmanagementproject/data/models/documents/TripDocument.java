package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.vehicles.Vehicle;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trip_documents")
@Getter
@Setter
@NoArgsConstructor
public class TripDocument extends Document {

    @Column(name = "trip_document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TripDocumentType type;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;
}

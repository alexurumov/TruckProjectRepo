package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.users.Driver;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "driver_documents")
@Getter
@Setter
@NoArgsConstructor
public class DriverDocument extends Document {

    @Column(name = "driver_document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverDocumentType type;

    @Column(name = "expiry_date", nullable = false)
    @Future
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;
}

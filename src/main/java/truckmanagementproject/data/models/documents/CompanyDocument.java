package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "company_documents")
@Getter
@Setter
@NoArgsConstructor
public class CompanyDocument extends Document {

    @Column(name = "company_document_type")
    @Enumerated(EnumType.STRING)
    private CompanyDocumentType type;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
}

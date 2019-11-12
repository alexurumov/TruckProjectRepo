package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Company")
@Getter
@Setter
@NoArgsConstructor
public class CompanyDocument extends Document {

    @Column(name = "company_document_type")
    @Enumerated(EnumType.STRING)
    private CompanyDocumentType companyDocumentType;

    public CompanyDocument(String type) {
        super(type);
    }
}

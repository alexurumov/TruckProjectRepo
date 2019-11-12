package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.documents.CompanyDocument;

public interface CompanyDocumentRepository extends JpaRepository<CompanyDocument, String> {
}

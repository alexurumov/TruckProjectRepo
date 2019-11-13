package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.CompanyDocument;

@Repository
public interface CompanyDocumentRepository extends JpaRepository<CompanyDocument, String> {
}

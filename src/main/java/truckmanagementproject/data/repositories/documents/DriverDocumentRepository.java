package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.DriverDocument;

@Repository
public interface DriverDocumentRepository extends JpaRepository<DriverDocument, String> {
}

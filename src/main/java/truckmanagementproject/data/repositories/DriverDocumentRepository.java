package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.documents.DriverDocument;

public interface DriverDocumentRepository extends JpaRepository<DriverDocument, String> {
}

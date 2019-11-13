package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.VehicleDocument;

@Repository
public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, String> {
}

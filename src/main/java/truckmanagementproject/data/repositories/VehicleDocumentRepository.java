package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.documents.VehicleDocument;

public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, String> {
}

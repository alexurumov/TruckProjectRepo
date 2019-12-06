package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.VehicleDocument;
import truckmanagementproject.services.models.documents.VehicleDocumentServiceModel;

import java.util.List;

@Repository
public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, String> {
    List<VehicleDocument> getAllByVehicleId(String id);
}

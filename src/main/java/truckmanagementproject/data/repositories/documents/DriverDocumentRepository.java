package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.services.models.documents.DriverDocumentServiceModel;

import java.util.List;

@Repository
public interface DriverDocumentRepository extends JpaRepository<DriverDocument, String> {
    List<DriverDocument> getAllByDriverId(String id);
}

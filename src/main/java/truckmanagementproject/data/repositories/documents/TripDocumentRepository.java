package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.TripDocument;

@Repository
public interface TripDocumentRepository extends JpaRepository<TripDocument, String> {
}

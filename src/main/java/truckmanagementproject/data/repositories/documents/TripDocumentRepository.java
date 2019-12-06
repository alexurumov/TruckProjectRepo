package truckmanagementproject.data.repositories.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.documents.TripDocument;

import java.util.List;

@Repository
public interface TripDocumentRepository extends JpaRepository<TripDocument, String> {
    List<TripDocument> getAllByTripDriverUsername(String username);

    List<TripDocument> getAllByTripReference(String reference);
}

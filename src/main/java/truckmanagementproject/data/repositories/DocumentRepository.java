package truckmanagementproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import truckmanagementproject.data.models.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
}

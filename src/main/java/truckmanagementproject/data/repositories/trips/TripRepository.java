package truckmanagementproject.data.repositories.trips;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.trips.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, String> {
}

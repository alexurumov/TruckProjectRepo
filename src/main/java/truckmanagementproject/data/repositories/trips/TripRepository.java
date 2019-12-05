package truckmanagementproject.data.repositories.trips;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.trips.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, String> {
    Trip getByReference(String reference);
    List<Trip> getAllByDriverUsername(String driverUsername);

    void deleteByReference(String reference);
}

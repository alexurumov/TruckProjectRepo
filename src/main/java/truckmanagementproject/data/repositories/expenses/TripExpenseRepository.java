package truckmanagementproject.data.repositories.expenses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.expenses.TripExpense;

import java.util.List;

@Repository
public interface TripExpenseRepository extends JpaRepository<TripExpense, String> {
    List<TripExpense> getAllByTripDriverUsername(String username);

    List<TripExpense> getAllByTripReference(String reference);
}

package truckmanagementproject.data.repositories.expenses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.expenses.TripExpense;

@Repository
public interface TripExpenseRepository extends JpaRepository<TripExpense, String> {
}

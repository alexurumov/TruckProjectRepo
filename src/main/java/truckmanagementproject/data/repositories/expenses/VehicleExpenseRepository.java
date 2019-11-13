package truckmanagementproject.data.repositories.expenses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.expenses.VehicleExpense;

@Repository
public interface VehicleExpenseRepository extends JpaRepository<VehicleExpense, String> {
}

package truckmanagementproject.data.repositories.expenses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truckmanagementproject.data.models.expenses.VehicleExpense;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;

import java.util.BitSet;
import java.util.List;

@Repository
public interface VehicleExpenseRepository extends JpaRepository<VehicleExpense, String> {
    List<VehicleExpense> getAllByVehicleId(String id);
}

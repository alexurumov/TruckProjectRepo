package truckmanagementproject.services.services.expenses;

import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;

public interface ExpenseService {
    void addTripExpense(AddTripExpenseServiceModel tripExpense);

    void addVehicleExpense(AddVehicleExpenseServiceModel vehicleExpense);
}

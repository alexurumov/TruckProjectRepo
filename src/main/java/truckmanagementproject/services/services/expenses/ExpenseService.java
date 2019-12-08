package truckmanagementproject.services.services.expenses;

import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;
import truckmanagementproject.services.models.expenses.TripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;

import java.util.BitSet;
import java.util.List;

public interface ExpenseService {
    void addTripExpense(AddTripExpenseServiceModel tripExpense) throws Exception;

    void addVehicleExpense(AddVehicleExpenseServiceModel vehicleExpense) throws Exception;

    List<TripExpenseServiceModel> getAllTripExpensesByDriver(String username);

    List<TripExpenseServiceModel> getAllTripExpenses();

    void removeTripExpense(String id);

    List<TripExpenseServiceModel> getAllTripExpensesByTrip(String reference);

    List<VehicleExpenseServiceModel> getAllVehicleExpenses();

    void removeVehicleExpense(String id);

    List<VehicleExpenseServiceModel> getAllVehicleExpensesByVehicle(String id);
}

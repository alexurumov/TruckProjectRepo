package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.expenses.VehicleExpenseType;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;
import truckmanagementproject.services.models.summaries.SummaryServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.services.summaries.SummaryService;
import truckmanagementproject.services.services.trips.TripService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryServiceTest extends BaseServiceTest {

    List<VehicleExpenseServiceModel> vehicleExpenses;
    List<TripServiceModel> finishedTrips;

    @MockBean
    TripService tripService;

    @MockBean
    ExpenseService expenseService;

    @Autowired
    SummaryService service;

    @Override
    protected void beforeEach() {
        vehicleExpenses = new ArrayList<>();
        finishedTrips = new ArrayList<>();

        Mockito.when(expenseService.getAllVehicleExpenses())
                .thenReturn(vehicleExpenses);

        Mockito.when(tripService.getAllFinished())
                .thenReturn(finishedTrips);

    }

    @Test
    public void getSummary_whenExpensesAndTrips_shouldReturnCorrectExpenses() {
        finishedTrips.clear();
        TripServiceModel trip1 = new TripServiceModel();
        trip1.setExpensesSum(BigDecimal.valueOf(20));
        trip1.setEmptyKm(5);
        trip1.setTripKm(500);

        TripServiceModel trip2 = new TripServiceModel();
        trip2.setExpensesSum(BigDecimal.valueOf(50));
        trip2.setEmptyKm(10);
        trip2.setTripKm(250);
        finishedTrips.addAll(List.of(trip1, trip2));

        vehicleExpenses.clear();
        VehicleExpenseServiceModel expense1 = new VehicleExpenseServiceModel();
        expense1.setType(VehicleExpenseType.Fuel.toString());
        expense1.setCost(BigDecimal.valueOf(950));

        VehicleExpenseServiceModel expense2 = new VehicleExpenseServiceModel();
        expense2.setType(VehicleExpenseType.AdBlue.toString());
        expense2.setCost(BigDecimal.valueOf(35));

        VehicleExpenseServiceModel expense3 = new VehicleExpenseServiceModel();
        expense3.setType(VehicleExpenseType.Toll.toString());
        expense3.setCost(BigDecimal.valueOf(150));
        vehicleExpenses.addAll(List.of(expense1, expense2, expense3));

        SummaryServiceModel summary = service.getSummary();

        assertEquals(BigDecimal.valueOf(1135), summary.getTotalExpense());
    }

    @Test
    public void getSummary_whenNoExpensesAndTrips_shouldReturnCorrectExpenses() {
        finishedTrips.clear();
        TripServiceModel trip1 = new TripServiceModel();
        trip1.setExpensesSum(BigDecimal.valueOf(20));
        trip1.setEmptyKm(5);
        trip1.setTripKm(500);

        TripServiceModel trip2 = new TripServiceModel();
        trip2.setExpensesSum(BigDecimal.valueOf(50));
        trip2.setEmptyKm(10);
        trip2.setTripKm(250);
        finishedTrips.addAll(List.of(trip1, trip2));

        vehicleExpenses.clear();

        SummaryServiceModel summary = service.getSummary();

        assertEquals(BigDecimal.ZERO, summary.getTotalExpense());
    }

    @Test
    public void getSummary_whenExpensesAndNoTrips_shouldReturnCorrectIncome() {
        finishedTrips.clear();

        vehicleExpenses.clear();
        VehicleExpenseServiceModel expense1 = new VehicleExpenseServiceModel();
        expense1.setType(VehicleExpenseType.Fuel.toString());
        expense1.setCost(BigDecimal.valueOf(950));

        VehicleExpenseServiceModel expense2 = new VehicleExpenseServiceModel();
        expense2.setType(VehicleExpenseType.AdBlue.toString());
        expense2.setCost(BigDecimal.valueOf(35));

        VehicleExpenseServiceModel expense3 = new VehicleExpenseServiceModel();
        expense3.setType(VehicleExpenseType.Toll.toString());
        expense3.setCost(BigDecimal.valueOf(150));
        vehicleExpenses.addAll(List.of(expense1, expense2, expense3));

        SummaryServiceModel summary = service.getSummary();

        assertEquals(BigDecimal.valueOf(0.0), summary.getTotalIncome());
    }

    @Test
    public void getSummary_whenExpensesAndTrips_shouldReturnCorrectIncome() {
        finishedTrips.clear();
        TripServiceModel trip1 = new TripServiceModel();
        trip1.setExpensesSum(BigDecimal.valueOf(20));
        trip1.setEmptyKm(5);
        trip1.setTripKm(500);

        TripServiceModel trip2 = new TripServiceModel();
        trip2.setExpensesSum(BigDecimal.valueOf(50));
        trip2.setEmptyKm(10);
        trip2.setTripKm(250);
        finishedTrips.addAll(List.of(trip1, trip2));

        vehicleExpenses.clear();
        VehicleExpenseServiceModel expense1 = new VehicleExpenseServiceModel();
        expense1.setType(VehicleExpenseType.Fuel.toString());
        expense1.setCost(BigDecimal.valueOf(950));

        VehicleExpenseServiceModel expense2 = new VehicleExpenseServiceModel();
        expense2.setType(VehicleExpenseType.AdBlue.toString());
        expense2.setCost(BigDecimal.valueOf(35));

        VehicleExpenseServiceModel expense3 = new VehicleExpenseServiceModel();
        expense3.setType(VehicleExpenseType.Toll.toString());
        expense3.setCost(BigDecimal.valueOf(150));
        vehicleExpenses.addAll(List.of(expense1, expense2, expense3));

        SummaryServiceModel summary = service.getSummary();

        assertEquals(BigDecimal.valueOf(903.85), summary.getTotalIncome());
    }
}
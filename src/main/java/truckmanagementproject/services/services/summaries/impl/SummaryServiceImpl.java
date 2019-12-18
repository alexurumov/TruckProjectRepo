package truckmanagementproject.services.services.summaries.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;
import truckmanagementproject.services.models.summaries.SummaryServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.services.summaries.SummaryService;
import truckmanagementproject.services.services.trips.TripService;

import java.math.BigDecimal;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final TripService tripService;
    private final ExpenseService expenseService;

    @Autowired
    public SummaryServiceImpl(TripService tripService, ExpenseService expenseService) {
        this.tripService = tripService;
        this.expenseService = expenseService;
    }

    @Override
    public SummaryServiceModel getSummary() {
        Integer emptyKm = calculateEmptyKm();
        Integer tripKm = calculateTripKm();
        BigDecimal expenses = calculateExpenses();
        BigDecimal fuel = calculateFuel();
        BigDecimal adBlue = calculateAdBlue();
        BigDecimal toll = calculateToll();
        BigDecimal totalIncome = expenses.add(BigDecimal.valueOf((emptyKm + tripKm) * 1.09));
        BigDecimal totalExpense = fuel.add(adBlue).add(toll);

        SummaryServiceModel summary = new SummaryServiceModel();
        summary.setEmptyKm(emptyKm);
        summary.setTripKm(tripKm);
        summary.setExpenses(expenses);
        summary.setFuel(fuel);
        summary.setAdBlue(adBlue);
        summary.setToll(toll);
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        return summary;
    }

    private BigDecimal calculateToll() {
        return expenseService.getAllVehicleExpenses()
                .stream()
                .filter(exp -> exp.getType().equals("Toll"))
                .map(VehicleExpenseServiceModel::getCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateAdBlue() {
        return expenseService.getAllVehicleExpenses()
                .stream()
                .filter(exp -> exp.getType().equals("AdBlue"))
                .map(VehicleExpenseServiceModel::getCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateFuel() {
        return expenseService.getAllVehicleExpenses()
                .stream()
                .filter(exp -> exp.getType().equals("Fuel"))
                .map(VehicleExpenseServiceModel::getCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateExpenses() {
        return tripService.getAllFinished()
                .stream()
                .map(TripServiceModel::getExpensesSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private Integer calculateTripKm() {
        return tripService.getAllFinished()
                .stream()
                .map(TripServiceModel::getTripKm)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Integer calculateEmptyKm() {
        return tripService.getAllFinished()
                .stream()
                .map(TripServiceModel::getEmptyKm)
                .reduce(Integer::sum)
                .orElse(0);
    }
}

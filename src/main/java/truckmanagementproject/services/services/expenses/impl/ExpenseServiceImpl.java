package truckmanagementproject.services.services.expenses.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.expenses.TripExpense;
import truckmanagementproject.data.models.expenses.VehicleExpense;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.expenses.TripExpenseRepository;
import truckmanagementproject.data.repositories.expenses.VehicleExpenseRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.models.documents.TripDocumentServiceModel;
import truckmanagementproject.services.models.expenses.TripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final TripExpenseRepository tripExpenseRepository;
    private final VehicleExpenseRepository vehicleExpenseRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;

    @Autowired
    public ExpenseServiceImpl(TripExpenseRepository tripExpenseRepository, VehicleExpenseRepository vehicleExpenseRepository, TripRepository tripRepository, VehicleRepository vehicleRepository, ModelMapper mapper) {
        this.tripExpenseRepository = tripExpenseRepository;
        this.vehicleExpenseRepository = vehicleExpenseRepository;
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
    }

    @Override
    public void addTripExpense(AddTripExpenseServiceModel tripExpense) {
        TripExpense expense = mapper.map(tripExpense, TripExpense.class);
        Trip trip = tripRepository.getByReference(tripExpense.getTripRef());
        expense.setTrip(trip);
        tripExpenseRepository.saveAndFlush(expense);
    }

    @Override
    public void addVehicleExpense(AddVehicleExpenseServiceModel vehicleExpense) {
        VehicleExpense expense = mapper.map(vehicleExpense, VehicleExpense.class);
        Vehicle vehicle = vehicleRepository.getByRegNumber(vehicleExpense.getVehicleRegNumber());
        expense.setVehicle(vehicle);
        vehicleExpenseRepository.saveAndFlush(expense);
    }

    @Override
    @Transactional
    public List<TripExpenseServiceModel> getAllTripExpensesByDriver(String username) {
        return tripExpenseRepository.getAllByTripDriverUsername(username)
                .stream()
                .map(exp -> mapper.map(exp, TripExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TripExpenseServiceModel> getAllTripExpenses() {
        return tripExpenseRepository.findAll()
                .stream()
                .map(exp -> mapper.map(exp, TripExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeTripExpense(String id) {
        tripExpenseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<TripExpenseServiceModel> getAllTripExpensesByTrip(String reference) {
        return tripExpenseRepository.getAllByTripReference(reference)
                .stream()
                .map(exp -> mapper.map(exp, TripExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleExpenseServiceModel> getAllVehicleExpenses() {
        return vehicleExpenseRepository.findAll()
                .stream()
                .map(exp -> mapper.map(exp, VehicleExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeVehicleExpense(String id) {
        vehicleExpenseRepository.deleteById(id);
    }

    @Override
    public List<VehicleExpenseServiceModel> getAllVehicleExpensesByVehicle(String id) {
        return vehicleExpenseRepository.getAllByVehicleId(id)
                .stream()
                .map(exp -> mapper.map(exp, VehicleExpenseServiceModel.class))
                .collect(Collectors.toList());
    }
}

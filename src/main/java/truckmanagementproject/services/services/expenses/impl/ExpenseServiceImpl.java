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
import truckmanagementproject.services.models.expenses.TripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final TripExpenseRepository tripExpenseRepository;
    private final VehicleExpenseRepository vehicleExpenseRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ExpenseServiceImpl(TripExpenseRepository tripExpenseRepository, VehicleExpenseRepository vehicleExpenseRepository, TripRepository tripRepository, VehicleRepository vehicleRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.tripExpenseRepository = tripExpenseRepository;
        this.vehicleExpenseRepository = vehicleExpenseRepository;
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addTripExpense(AddTripExpenseServiceModel tripExpense) throws Exception {
        TripExpense expense = mapper.map(tripExpense, TripExpense.class);
        Trip trip = tripRepository.getByReference(tripExpense.getTripRef());
        if (trip == null) {
            throw new Exception("Invalid Trip");
        }
        expense.setTrip(trip);

        if (validationUtil.isValid(expense)) {
            tripExpenseRepository.saveAndFlush(expense);
        } else {
            throw new Exception("Invalid Trip Expense");
        }
    }

    @Override
    public void addVehicleExpense(AddVehicleExpenseServiceModel vehicleExpense) throws Exception {
        VehicleExpense expense = mapper.map(vehicleExpense, VehicleExpense.class);
        Vehicle vehicle = vehicleRepository.getByRegNumber(vehicleExpense.getVehicleRegNumber());
        if (vehicle == null) {
            throw new Exception("Invalid vehicle");
        }
        expense.setVehicle(vehicle);
        if (validationUtil.isValid(expense)) {
            vehicleExpenseRepository.saveAndFlush(expense);
        } else {
            throw new Exception("Invalid Vehicle Expense");
        }

    }

    @Override
    public List<TripExpenseServiceModel> getAllTripExpenses() {
        return tripExpenseRepository.findAll()
                .stream()
                .map(exp -> mapper.map(exp, TripExpenseServiceModel.class))
                .collect(Collectors.toList());
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
    public List<TripExpenseServiceModel> getAllTripExpensesByTrip(String reference) {
        return tripExpenseRepository.getAllByTripReference(reference)
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
    public List<VehicleExpenseServiceModel> getAllVehicleExpenses() {
        return vehicleExpenseRepository.findAll()
                .stream()
                .map(exp -> mapper.map(exp, VehicleExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleExpenseServiceModel> getAllVehicleExpensesByVehicle(String id) {
        return vehicleExpenseRepository.getAllByVehicleId(id)
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
    public boolean isTripExpenseValid(AddTripExpenseModel addTripExpenseModel) {
        if (addTripExpenseModel.getDate().trim().isEmpty()) {
            return false;
        }
        return !addTripExpenseModel.getPicture().getOriginalFilename().isEmpty() &&
                !addTripExpenseModel.getTripRef().equals("0") &&
                addTripExpenseModel.getCost().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean isVehicleExpenseValid(AddVehicleExpenseModel addVehicleExpenseModel) {
        if (addVehicleExpenseModel.getDate().trim().isEmpty()) {
            return false;
        }
        return !addVehicleExpenseModel.getPicture().getOriginalFilename().isEmpty() &&
                !addVehicleExpenseModel.getVehicleRegNumber().equals("0") &&
                addVehicleExpenseModel.getCost().compareTo(BigDecimal.ZERO) > 0;
    }
}

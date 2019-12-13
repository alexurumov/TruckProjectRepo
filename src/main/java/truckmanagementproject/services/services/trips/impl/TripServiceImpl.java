package truckmanagementproject.services.services.trips.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.expenses.Expense;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.models.trips.FinishTripServiceModel;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;
import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, DriverRepository driverRepository, VehicleRepository vehicleRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public List<TripServiceModel> getAllTripsByDriver(String driverUsername) {
        return tripRepository.getAllByDriverUsername(driverUsername)
                .stream()
                .map(trip -> mapper.map(trip, TripServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isReferenceTaken(String reference) {
        Trip trip = tripRepository.getByReference(reference);
        return trip != null;
    }

    @Override
    public void addTrip(AddTripServiceModel tripServiceModel) throws Exception {
        Trip trip = mapper.map(tripServiceModel, Trip.class);
        Driver driver = driverRepository.getByName(tripServiceModel.getDriverName());
        trip.setDriver(driver);
        Vehicle vehicle = vehicleRepository.getByRegNumber(tripServiceModel.getVehicleRegNumber());
        trip.setVehicle(vehicle);
        if (validationUtil.isValid(trip)) {
            tripRepository.saveAndFlush(trip);
        } else throw new Exception("Invalid Trip!");
    }

    @Override
    public List<TripServiceModel> getAllCurrent() {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> !trip.getIsFinished())
                .map(trip -> {
                    TripServiceModel model = mapper.map(trip, TripServiceModel.class);
                    List<MilestoneServiceModel> milestones = trip.getMilestones()
                            .stream()
                            .map(milestone -> mapper.map(milestone, MilestoneServiceModel.class))
                            .collect(Collectors.toList());

                    model.setMilestones(milestones);
                    BigDecimal expenses = trip.getExpenses().stream().map(Expense::getCost).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    model.setExpensesSum(expenses);
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TripServiceModel getTripByReference(String reference) {
        Trip trip = tripRepository.getByReference(reference);
        TripServiceModel tripModel = mapper.map(trip, TripServiceModel.class);

        List<MilestoneServiceModel> milestones = trip.getMilestones()
                .stream()
                .map(milestone -> mapper.map(milestone, MilestoneServiceModel.class))
                .collect(Collectors.toList());

        tripModel.setMilestones(milestones);

        BigDecimal expenses = trip.getExpenses().stream().map(Expense::getCost).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        tripModel.setExpensesSum(expenses);

        return tripModel;
    }

    @Override
    public void finishTrip(FinishTripServiceModel tripServiceModel, String reference) throws Exception {
        Trip trip = tripRepository.getByReference(reference);
        trip.setEmptyKm(tripServiceModel.getEmptyKm());
        trip.setTripKm(tripServiceModel.getTripKm());
        trip.setEmptyPallets(tripServiceModel.getEmptyPallets());
        trip.setIsFinished(true);
        if (validationUtil.isValid(trip)) {
            tripRepository.saveAndFlush(trip);
        } else throw new Exception("Invalid Trip!");
    }

    @Override
    public List<TripServiceModel> getAllFinished() {
        return tripRepository.findAll()
                .stream()
                .filter(Trip::getIsFinished)
                .map(trip -> {
                    TripServiceModel model = mapper.map(trip, TripServiceModel.class);
                    List<MilestoneServiceModel> milestones = trip.getMilestones()
                            .stream()
                            .map(milestone -> mapper.map(milestone, MilestoneServiceModel.class))
                            .collect(Collectors.toList());

                    model.setMilestones(milestones);
                    BigDecimal expenses = trip.getExpenses().stream().map(Expense::getCost).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    model.setExpensesSum(expenses);

                    BigDecimal price = BigDecimal.valueOf((trip.getEmptyKm() + trip.getTripKm()) * 1.09);
                    price = price.add(expenses);
                    if (trip.getAdr()) {
                        price = price.add(BigDecimal.valueOf(50.00));
                    }
                    model.setPrice(price);
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(String reference) {
        tripRepository.deleteByReference(reference);
    }

    @Override
    public List<TripServiceModel> getAllTripsByVehicle(String id) {
        return tripRepository.getAllByVehicleId(id)
                .stream()
                .map(trip -> mapper.map(trip, TripServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripServiceModel> getAllTripsByDriverId(String id) {
        return tripRepository.getAllByDriverId(id)
                .stream()
                .map(trip -> mapper.map(trip, TripServiceModel.class))
                .collect(Collectors.toList());
    }
}

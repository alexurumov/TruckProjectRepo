package truckmanagementproject.services.services.trips.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;
import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, DriverRepository driverRepository, VehicleRepository vehicleRepository, ModelMapper mapper) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
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
    public void addTrip(AddTripServiceModel tripServiceModel) {
        Trip trip = mapper.map(tripServiceModel, Trip.class);
        Driver driver = driverRepository.getByName(tripServiceModel.getDriverName());
        trip.setDriver(driver);
        Vehicle vehicle = vehicleRepository.getByRegNumber(tripServiceModel.getVehicleRegNumber());
        trip.setVehicle(vehicle);
        tripRepository.saveAndFlush(trip);
    }

    @Override
    public List<TripServiceModel> getAllCurrent() {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> !trip.getIsFinished())
                .map(tr -> mapper.map(tr, TripServiceModel.class))
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
        return tripModel;
    }
}
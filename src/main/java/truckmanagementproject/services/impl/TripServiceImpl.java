package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.TripService;
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
}

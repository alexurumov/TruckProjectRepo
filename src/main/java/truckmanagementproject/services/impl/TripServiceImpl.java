package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.services.TripService;
import truckmanagementproject.services.models.trips.TripServiceModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final ModelMapper mapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, ModelMapper mapper) {
        this.tripRepository = tripRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TripServiceModel> getAllTripsByDriver(String driverUsername) {
        return tripRepository.getAllByDriverUsername(driverUsername)
                .stream()
                .map(trip -> mapper.map(trip, TripServiceModel.class))
                .collect(Collectors.toList());
    }


}

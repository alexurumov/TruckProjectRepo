package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.VehicleService;
import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final ModelMapper mapper;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(ModelMapper mapper, VehicleRepository vehicleRepository) {
        this.mapper = mapper;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void registerVehicle(AddVehicleServiceModel model) throws Exception {
        if (vehicleRepository.existsByRegNumber(model.getRegNumber())) {
            throw new Exception("Incorrect input");
        }

        Vehicle vehicle = mapper.map(model, Vehicle.class);

        //TODO implement Validator utils and VALIDATE Manager fields before adding to DB

        vehicleRepository.saveAndFlush(vehicle);

    }
}

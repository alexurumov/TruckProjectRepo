package truckmanagementproject.services.services.vehicles.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;
import truckmanagementproject.services.models.vehicles.VehicleServiceModel;
import truckmanagementproject.util.ValidationUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final ModelMapper mapper;
    private final VehicleRepository vehicleRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public VehicleServiceImpl(ModelMapper mapper, VehicleRepository vehicleRepository, ValidationUtil validationUtil) {
        this.mapper = mapper;
        this.vehicleRepository = vehicleRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerVehicle(AddVehicleServiceModel model) throws Exception {
        if (vehicleRepository.existsByRegNumber(model.getRegNumber())) {
            throw new Exception("Invalid vehicle");
        }

        Vehicle vehicle = mapper.map(model, Vehicle.class);

        if (validationUtil.isValid(vehicle)) {
            vehicleRepository.saveAndFlush(vehicle);
        } else throw new Exception("Invalid vehicle!");

    }

    @Override
    public List<VehicleServiceModel> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicle -> mapper.map(vehicle, VehicleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeVehicle(String id) {
        vehicleRepository.deleteById(id);
    }
}

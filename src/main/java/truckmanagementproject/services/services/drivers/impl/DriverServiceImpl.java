package truckmanagementproject.services.services.drivers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.services.validations.AddDriverValidationService;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.services.hashing.HashingService;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.services.models.drivers.DriverServiceModel;
import truckmanagementproject.util.ValidationUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final ModelMapper mapper;
    private final AddDriverValidationService addDriverValidationService;
    private final HashingService hashingService;
    private final ValidationUtil validationUtil;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper mapper, AddDriverValidationService addValidationService, AddDriverValidationService addDriverValidationService, HashingService hashingService, ValidationUtil validationUtil) {
        this.driverRepository = driverRepository;
        this.mapper = mapper;
        this.addDriverValidationService = addDriverValidationService;
        this.hashingService = hashingService;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerDriver(AddDriverServiceModel model) throws Exception {
        if (!addDriverValidationService.isValid(model)) {
            throw new Exception("Incorrect input");
        }

        Driver driver = mapper.map(model, Driver.class);
        driver.setPassword(hashingService.hash(model.getPassword()));

        if (validationUtil.isValid(driver)) {
            driverRepository.saveAndFlush(driver);
        } else {
            throw new Exception("Invalid Driver");
        }
    }

    @Override
    public List<DriverServiceModel> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driver -> mapper.map(driver, DriverServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeDriver(String id) {
        driverRepository.deleteById(id);
    }
}

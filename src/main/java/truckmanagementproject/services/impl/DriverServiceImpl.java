package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.AddDriverValidationService;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.HashingService;
import truckmanagementproject.services.models.AddDriverServiceModel;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final ModelMapper mapper;
    private final AddDriverValidationService addValidationService;
    private final HashingService hashingService;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper mapper, AddDriverValidationService addValidationService, HashingService hashingService) {
        this.driverRepository = driverRepository;
        this.mapper = mapper;
        this.addValidationService = addValidationService;
        this.hashingService = hashingService;
    }

    @Override
    public void registerDriver(AddDriverServiceModel model) throws Exception {
        if (!addValidationService.isValid(model)) {
            throw new Exception("Incorrect input");
        }

        Driver driver = mapper.map(model, Driver.class);
        driver.setPassword(hashingService.hash(model.getPassword()));

        //TODO implement Validator utils and VALIDATE Manager fields before adding to DB

        driverRepository.saveAndFlush(driver);
    }
}

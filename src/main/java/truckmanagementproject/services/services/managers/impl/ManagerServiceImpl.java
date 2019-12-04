package truckmanagementproject.services.services.managers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.services.validations.AddManagerValidationService;
import truckmanagementproject.services.services.hashing.HashingService;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ModelMapper mapper;
    private final AddManagerValidationService addValidationService;
    private final HashingService hashingService;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, ModelMapper mapper, AddManagerValidationService addValidationService, HashingService hashingService) {
        this.managerRepository = managerRepository;
        this.mapper = mapper;
        this.addValidationService = addValidationService;
        this.hashingService = hashingService;
    }

    @Override
    public void registerManager(AddManagerServiceModel model) throws Exception {
        if (!addValidationService.isValid(model)) {
            throw new Exception("Incorrect input");
        }

        Manager manager = mapper.map(model, Manager.class);
        manager.setPassword(hashingService.hash(model.getPassword()));

        //TODO implement Validator utils and VALIDATE Manager fields before adding to DB

        managerRepository.saveAndFlush(manager);
    }
}

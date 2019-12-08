package truckmanagementproject.services.services.managers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.models.managers.ManagerServiceModel;
import truckmanagementproject.services.services.validations.AddManagerValidationService;
import truckmanagementproject.services.services.hashing.HashingService;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.util.ValidationUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ModelMapper mapper;
    private final AddManagerValidationService addValidationService;
    private final HashingService hashingService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, ModelMapper mapper, AddManagerValidationService addValidationService, HashingService hashingService, ValidationUtil validationUtil) {
        this.managerRepository = managerRepository;
        this.mapper = mapper;
        this.addValidationService = addValidationService;
        this.hashingService = hashingService;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerManager(AddManagerServiceModel model) throws Exception {
        if (!addValidationService.isValid(model)) {
            throw new Exception("Incorrect input");
        }

        Manager manager = mapper.map(model, Manager.class);
        manager.setPassword(hashingService.hash(model.getPassword()));

        if (validationUtil.isValid(manager)) {
            managerRepository.saveAndFlush(manager);
        } else throw new Exception("Invalid Manager");

    }

    @Override
    public List<ManagerServiceModel> getAllManagers() {
        return managerRepository.findAll()
                .stream()
                .map(manager -> mapper.map(manager, ManagerServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeManager(String id) {
        managerRepository.deleteById(id);
    }
}

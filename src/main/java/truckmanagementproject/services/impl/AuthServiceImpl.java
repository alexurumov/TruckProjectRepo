package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.Admin;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.AdminRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.AuthService;
import truckmanagementproject.services.HashingService;
import truckmanagementproject.services.models.auth.LoginUserServiceModel;

@Service
public class AuthServiceImpl implements AuthService {

    private final HashingService hashingService;
    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;
    private final ModelMapper mapper;

    @Autowired
    public AuthServiceImpl(HashingService hashingService, AdminRepository adminRepository, ManagerRepository managerRepository, DriverRepository driverRepository, ModelMapper mapper) {
        this.hashingService = hashingService;
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
        this.mapper = mapper;
    }

    @Override
    public LoginUserServiceModel login(LoginUserServiceModel model) {
        model.setPassword(hashingService.hash(model.getPassword()));
        if (adminRepository.existsByUsernameAndPassword(model.getUsername(), model.getPassword())) {
            Admin admin = adminRepository.getByUsernameAndPassword(model.getUsername(), model.getPassword());
            return mapper.map(admin, LoginUserServiceModel.class);
        }

        if (managerRepository.existsByUsernameAndPassword(model.getUsername(), model.getPassword())) {
            Manager manager = managerRepository.getByUsernameAndPassword(model.getUsername(), model.getPassword());
            return mapper.map(manager, LoginUserServiceModel.class);
        }

        if (driverRepository.existsByUsernameAndPassword(model.getUsername(), model.getPassword())) {
            Driver driver = driverRepository.getByUsernameAndPassword(model.getUsername(), model.getPassword());
            return mapper.map(driver, LoginUserServiceModel.class);
        }

        return null;
    }
}

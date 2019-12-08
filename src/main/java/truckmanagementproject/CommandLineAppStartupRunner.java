package truckmanagementproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import truckmanagementproject.data.models.users.Admin;
import truckmanagementproject.data.repositories.users.AdminRepository;
import truckmanagementproject.services.services.hashing.HashingService;


@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final HashingService hashingService;

    @Autowired
    public CommandLineAppStartupRunner(AdminRepository adminRepository, HashingService hashingService) {
        this.adminRepository = adminRepository;
        this.hashingService = hashingService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findAll().size() != 0) {
            return;
        }

        Admin admin = new Admin();

        admin.setName("Administrator");
        admin.setUsername("admin");
        admin.setPassword(hashingService.hash("123"));

        adminRepository.saveAndFlush(admin);

    }

}

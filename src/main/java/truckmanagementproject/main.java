package truckmanagementproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import truckmanagementproject.data.repositories.documents.CompanyDocumentRepository;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.VehicleDocumentRepository;
import truckmanagementproject.data.repositories.milestones.MilestoneRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;

@Component
public class main implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final CompanyDocumentRepository companyDocumentRepository;
    private final MilestoneRepository milestoneRepository;


    @Autowired
    public main(VehicleRepository vehicleRepository, DriverRepository driverRepository, ManagerRepository managerRepository, VehicleDocumentRepository vehicleDocumentRepository, DriverDocumentRepository driverDocumentRepository, CompanyDocumentRepository companyDocumentRepository, MilestoneRepository milestoneRepository) {
        this.vehicleRepository = vehicleRepository;

        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.companyDocumentRepository = companyDocumentRepository;
        this.milestoneRepository = milestoneRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //TODO make trip cost logic (Transient or property)

        //TODO implement boolean isCompleted to each Milestone

    }
}

package truckmanagementproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import truckmanagementproject.data.models.documents.*;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.models.vehicles.Trailer;
import truckmanagementproject.data.models.vehicles.Truck;
import truckmanagementproject.data.repositories.*;

import java.sql.Date;

@Component
public class main implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final CompanyDocumentRepository companyDocumentRepository;


    @Autowired
    public main(VehicleRepository vehicleRepository, DriverRepository driverRepository, ManagerRepository managerRepository, VehicleDocumentRepository vehicleDocumentRepository, DriverDocumentRepository driverDocumentRepository, CompanyDocumentRepository companyDocumentRepository) {
        this.vehicleRepository = vehicleRepository;

        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.companyDocumentRepository = companyDocumentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Manager manager = new Manager();
        manager.setName("Petar Petrov");
        manager.setUsername("pesho");
        manager.setPassword("123");

        managerRepository.saveAndFlush(manager);

        Driver driver = new Driver();
        driver.setName("Krasimir Simeonov");
        driver.setUsername("ksimeonov");
        driver.setPassword("123");

        driverRepository.saveAndFlush(driver);

        Driver driver2 = new Driver();
        driver2.setName("Ivan Karamihalev");
        driver2.setUsername("ikaramihalev");
        driver2.setPassword("123");

        driverRepository.saveAndFlush(driver2);

        Truck truck = new Truck();
        truck.setRegNumber("E 0607 KX");

        vehicleRepository.saveAndFlush(truck);

        Trailer trailer = new Trailer();
        trailer.setRegNumber("E 1966 EE");

        vehicleRepository.saveAndFlush(trailer);

        CompanyDocument companyDocument1 = new CompanyDocument();
        companyDocument1.setCompanyDocumentType(CompanyDocumentType.TransportLicense);
        companyDocument1.setExpiryDate(Date.valueOf("2020-05-28"));
        companyDocument1.setPicture("url..");

        companyDocumentRepository.saveAndFlush(companyDocument1);

        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.setVehicleDocumentType(VehicleDocumentType.GO);
        vehicleDocument.setExpiryDate(Date.valueOf("2020-04-12"));
        vehicleDocument.setPicture("url..");
        vehicleDocument.setVehicle(vehicleRepository.getByRegNumber("E 0607 KX"));

        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        DriverDocument driverDocument = new DriverDocument();
        driverDocument.setDriverDocumentType(DriverDocumentType.ID);
        driverDocument.setExpiryDate(Date.valueOf("2030-12-12"));
        driverDocument.setPicture("url...");
        driverDocument.setDriver(driverRepository.getByUsername("ksimeonov"));

        driverDocumentRepository.saveAndFlush(driverDocument);

    }
}

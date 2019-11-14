package truckmanagementproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.documents.DriverDocumentType;
import truckmanagementproject.data.models.documents.VehicleDocument;
import truckmanagementproject.data.models.documents.VehicleDocumentType;
import truckmanagementproject.data.models.milestones.LocationType;
import truckmanagementproject.data.models.milestones.Milestone;
import truckmanagementproject.data.models.milestones.MilestoneType;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.documents.CompanyDocumentRepository;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.VehicleDocumentRepository;
import truckmanagementproject.data.repositories.milestones.MilestoneRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class main implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final CompanyDocumentRepository companyDocumentRepository;
    private final MilestoneRepository milestoneRepository;
    private final TripRepository tripRepository;


    @Autowired
    public main(VehicleRepository vehicleRepository, DriverRepository driverRepository, ManagerRepository managerRepository, VehicleDocumentRepository vehicleDocumentRepository, DriverDocumentRepository driverDocumentRepository, CompanyDocumentRepository companyDocumentRepository, MilestoneRepository milestoneRepository, TripRepository tripRepository) {
        this.vehicleRepository = vehicleRepository;

        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.companyDocumentRepository = companyDocumentRepository;
        this.milestoneRepository = milestoneRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //Create a driver and save it to DB
        Driver driver = new Driver();
        driver.setName("Ivan Karamihalev");
        driver.setUsername("IKaramihalev");
        driver.setPassword("1234");
        driver.setDrivingHours(LocalTime.of(8, 50));
        driver.setWorkingHours(LocalTime.of(20, 30));
        driverRepository.saveAndFlush(driver);

        //Create a vehicle and save it to DB
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNumber("E7210MH / E2186EE");
        vehicleRepository.saveAndFlush(vehicle);

        //Create a driver document, set it's driver and save it to DB
        DriverDocument driverDocument = new DriverDocument();
        driverDocument.setType(DriverDocumentType.ID);
        driverDocument.setPicture("driverDocumentUrl...");
        driverDocument.setExpiryDate(LocalDate.of(2020, 5, 28));
        driverDocument.setDriver(driver);
        driverDocumentRepository.saveAndFlush(driverDocument);

        //Create a vehicle document, set it's vehicle and save it to DB
        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.setType(VehicleDocumentType.CmrInsurance);
        vehicleDocument.setPicture("vehicleDocumentUrl...");
        vehicleDocument.setExpiryDate(LocalDate.of(2020, 6, 12));
        vehicleDocument.setVehicle(vehicle);
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        //Create a trip with driver and vehicle and save it to DB
        Trip trip = new Trip();
        trip.setDate(LocalDate.of(2019, 11, 14));
        trip.setDirection("BE -> DE");
        trip.setReference("LGQY21204");
        trip.setAdr(false);
        trip.setDriver(driver);
        trip.setVehicle(vehicle);
        tripRepository.saveAndFlush(trip);

        //Create a collection, set it's trip and save it to DB
        Milestone collection = new Milestone();
        collection.setMilestoneType(MilestoneType.Collection);
        collection.setLocationType(LocationType.Depot);
        collection.setName("Ital Heywood");
        collection.setAddress("Ital Heywood Ltd" +
                "Berchway Business Park" +
                "Heywood OL10 2SX" +
                "UK");
        collection.setDetails("Loading 12 EP for export for Greece with ref: Ital123123");
        collection.setDeadline(LocalDateTime.of(2019, 11, 15, 8, 0));
        collection.setTrip(trip);
        milestoneRepository.saveAndFlush(collection);

        ////Create a delivery and save it to DB
        Milestone delivery = new Milestone();
        delivery.setMilestoneType(MilestoneType.Delivery);
        delivery.setLocationType(LocationType.Standard);
        delivery.setName("Syngenta Innofita (GR18004)");
        delivery.setAddress("Syngenta Innofita GmbH" +
                "Innofita Business Park" +
                "Innofita 18004" +
                "Greece");
        delivery.setDetails("Delivery of 12 EP from Ital Heywood, UK with ref: SY1023123");
        delivery.setDeadline(LocalDateTime.of(2019, 11, 20, 14, 30));
        delivery.setTrip(trip);
        milestoneRepository.saveAndFlush(delivery);

        //TODO make trip cost logic (Transient or property)

        //TODO implement boolean isCompleted to each Milestone

    }
}

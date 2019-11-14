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
import truckmanagementproject.data.models.milestones.Status;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

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
    @Transactional
    public void run(String... args) throws Exception {

        //Create a driver and save it to DB
        Driver driver = this.createNewDriver(
                "Ivan Karamihalev",
                "IKaramihalev",
                "1234",
                LocalTime.of(8, 50),
                LocalTime.of(20, 30));

        //Create a driver document
        DriverDocument driverDocument = this.createNewDriverDocument(
                DriverDocumentType.ID,
                "driverDocumentUrl...",
                LocalDate.of(2020, 5, 28));

        //Assign document to driver and save to DB
        driver.getDriverDocuments().add(driverDocument);
        driverRepository.saveAndFlush(driver);
        driverDocumentRepository.saveAndFlush(driverDocument);

//        //Create a vehicle and save it to DB
//        this.createNewVehicle("E7210MH / E2186EE");

//        //Create a vehicle document, set it's vehicle and save it to DB
//        VehicleDocument vehicleDocument = this.createNewVehicleDocument(
//                VehicleDocumentType.CmrInsurance,
//                "vehicleDocumentUrl...",
//                LocalDate.of(2020, 6, 12));
//
////        vehicle.getDocuments().add(vehicleDocument);
////        vehicleRepository.saveAndFlush(vehicle);
////
////        //Create a trip with driver and vehicle and save it to DB
////        Trip trip = this.createNewTrip(
////                LocalDate.of(2019, 11, 14),
////                "BE -> DE",
////                "LGQY21204",
////                false,
////                driver,
////                vehicle);
//
//        //Create a collection, set it's trip and save it to DB
//
//        Milestone ital_heywood = this.createMilestone(
//                MilestoneType.Collection,
//                LocationType.Depot,
//                "Ital Heywood",
//                "Ital Heywood Ltd" +
//                        "Berchway Business Park" +
//                        "Heywood OL10 2SX" +
//                        "UK",
//                "Loading 12 EP for export for Greece with ref: Ital123123",
//                LocalDateTime.of(2019, 11, 15, 8, 0));
//
//        ////Create a delivery and save it to DB
//
//        Milestone syngenta = this.createMilestone(
//                MilestoneType.Delivery,
//                LocationType.Standard,
//                "Syngenta Innofita (GR18004)",
//                "Syngenta Innofita GmbH" +
//                        "Innofita Business Park" +
//                        "Innofita 18004" +
//                        "Greece",
//                "Delivery of 12 EP from Ital Heywood, UK with ref: SY1023123",
//                LocalDateTime.of(2019, 11, 20, 14, 30));

//        trip.getMilestones().add(ital_heywood);
//        trip.getMilestones().add(syngenta);
//        milestoneRepository.saveAndFlush(ital_heywood);
//        milestoneRepository.saveAndFlush(syngenta);
//        tripRepository.saveAndFlush(trip);

        //TODO make trip cost logic (Transient or property)

//        Milestone milestone1 = trip.getMilestones()
//                .stream()
//                .filter(milestone -> !milestone.getStatus().equals(Status.Finished))
//                .findFirst().orElse(null);
//
//        while (milestone1 != null) {
//            milestone1.setStatus(Status.Arrived);
//            milestone1.setStatus(Status.Unloading);
//            milestone1.setStatus(Status.Finished);
//            milestone1 = trip.getMilestones()
//                    .stream()
//                    .filter(milestone -> !milestone.getStatus().equals(Status.Finished))
//                    .findFirst().orElse(null);
//        }

    }

    private Milestone createMilestone(MilestoneType type, LocationType locationType, String name, String address, String details, LocalDateTime deadline) {
        Milestone milestone = new Milestone();
        milestone.setMilestoneType(type);
        milestone.setLocationType(locationType);
        milestone.setName(name);
        milestone.setAddress(address);
        milestone.setDetails(details);
        milestone.setDeadline(deadline);
        return milestone;
    }

    private Trip createNewTrip(LocalDate date, String direction, String reference, boolean hasAdr, Driver driver, Vehicle vehicle) {
        Trip trip = new Trip();
        trip.setDate(date);
        trip.setDirection(direction);
        trip.setReference(reference);
        trip.setAdr(hasAdr);
        trip.setDriver(driver);
        trip.setVehicle(vehicle);
        return trip;
    }

    private VehicleDocument createNewVehicleDocument(VehicleDocumentType type, String pictureUrl, LocalDate expiryDate) {
        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.setType(type);
        vehicleDocument.setPicture(pictureUrl);
        vehicleDocument.setExpiryDate(expiryDate);
        return vehicleDocument;
    }

    private DriverDocument createNewDriverDocument(DriverDocumentType type, String pictureUrl, LocalDate expiryDate) {
        DriverDocument driverDocument = new DriverDocument();
        driverDocument.setType(type);
        driverDocument.setPicture(pictureUrl);
        driverDocument.setExpiryDate(expiryDate);
        return driverDocument;
    }

    private void createNewVehicle(String regNumber) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNumber(regNumber);
    }

    private Driver createNewDriver(String name, String username, String password, LocalTime drivingHours, LocalTime workingHours) {
        Driver driver = new Driver();
        driver.setName(name);
        driver.setUsername(username);
        driver.setPassword(password);
        driver.setDrivingHours(drivingHours);
        driver.setWorkingHours(workingHours);
        return driver;
    }
}

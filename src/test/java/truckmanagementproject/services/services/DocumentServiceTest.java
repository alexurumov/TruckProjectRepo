package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.documents.*;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.documents.AddCompanyDocServiceModel;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;
import truckmanagementproject.services.models.documents.AddVehicleDocServiceModel;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.util.ValidationUtil;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceTest extends BaseServiceTest {

    @MockBean
    TripRepository tripRepository;

    @MockBean
    TripDocumentRepository tripDocumentRepository;

    @MockBean
    DriverRepository driverRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    ValidationUtil validationUtil;

    @Autowired
    DocumentService service;

    @Test
    public void addTripDocument_whenIncorrectModel_shouldThrowException() {
        String tripRef = "tripRef";
        AddTripDocServiceModel documentModel = new AddTripDocServiceModel();
        documentModel.setTripRef(tripRef);

        Trip trip = new Trip();
        Mockito.when(tripRepository.getByReference(documentModel.getTripRef()))
                .thenReturn(trip);

        TripDocument document = new TripDocument();

        Mockito.when(validationUtil.isValid(document))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addTripDocument(documentModel));
    }

    @Test
    public void addDriverDocument_whenIncorrectModel_shouldThrowException() {
        String driverName = "driverName";
        AddDriverDocServiceModel documentModel = new AddDriverDocServiceModel();
        documentModel.setDriverName(driverName);

        Driver driver = new Driver();

        Mockito.when(driverRepository.getByName(documentModel.getDriverName()))
                .thenReturn(driver);

        DriverDocument document = new DriverDocument();

        Mockito.when(validationUtil.isValid(document))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addDriverDocument(documentModel));
    }

    @Test
    public void addVehicleDocument_whenIncorrectModel_shouldThrowException() {
        String regNumber = "regNumber";

        AddVehicleDocServiceModel documentModel = new AddVehicleDocServiceModel();
        documentModel.setRegNumber(regNumber);

        Vehicle vehicle = new Vehicle();

        Mockito.when(vehicleRepository.getByRegNumber(documentModel.getRegNumber()))
                .thenReturn(vehicle);

        VehicleDocument document = new VehicleDocument();

        Mockito.when(validationUtil.isValid(document))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addVehicleDocument(documentModel));
    }

    @Test
    public void addCompanyDocument_whenIncorrectModel_shouldThrowException() {
        AddCompanyDocServiceModel documentModel = new AddCompanyDocServiceModel();
        CompanyDocument document = new CompanyDocument();

        Mockito.when(validationUtil.isValid(document))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addCompanyDocument(documentModel));
    }

}
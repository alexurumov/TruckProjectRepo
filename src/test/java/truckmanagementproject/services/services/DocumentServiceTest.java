package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import truckmanagementproject.data.models.documents.*;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.documents.CompanyDocumentRepository;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.documents.VehicleDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.documents.*;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.documents.AddCompanyDocumentModel;
import truckmanagementproject.web.models.documents.AddDriverDocumentModel;
import truckmanagementproject.web.models.documents.AddTripDocumentModel;
import truckmanagementproject.web.models.documents.AddVehicleDocumentModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceTest extends BaseServiceTest {

    @MockBean
    TripRepository tripRepository;

    @MockBean
    TripDocumentRepository tripDocumentRepository;

    @MockBean
    VehicleDocumentRepository vehicleDocumentRepository;

    @MockBean
    DriverDocumentRepository driverDocumentRepository;

    @MockBean
    CompanyDocumentRepository companyDocumentRepository;

    @MockBean
    DriverRepository driverRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    ValidationUtil validationUtil;

    List<TripDocument> tripDocs;
    List<VehicleDocument> vehicleDocs;
    List<DriverDocument> driverDocs;
    List<CompanyDocument> companyDocs;

    @Autowired
    DocumentService service;

    @Override
    protected void beforeEach() {
        tripDocs = new ArrayList<>();
        vehicleDocs = new ArrayList<>();
        driverDocs = new ArrayList<>();
        companyDocs = new ArrayList<>();

        Mockito.when(tripDocumentRepository.findAll())
                .thenReturn(tripDocs);

        Mockito.when(vehicleDocumentRepository.findAll())
                .thenReturn(vehicleDocs);

        Mockito.when(driverDocumentRepository.findAll())
                .thenReturn(driverDocs);

        Mockito.when(companyDocumentRepository.findAll())
                .thenReturn(companyDocs);
    }

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

    @Test
    public void getAllTripDocs_whenDocs_shouldReturnCorrectList() {
        tripDocs.clear();
        TripDocument tripDocument1 = new TripDocument();
        tripDocument1.setId("id1");
        tripDocument1.setType(TripDocumentType.CMR);
        tripDocument1.setPicture("picture1");
        tripDocument1.setTrip(new Trip());
        TripDocument tripDocument2 = new TripDocument();
        tripDocument2.setId("id1");
        tripDocument2.setType(TripDocumentType.CMR);
        tripDocument2.setPicture("picture1");
        tripDocument2.setTrip(new Trip());
        tripDocs.add(tripDocument1);
        tripDocs.add(tripDocument2);

        List<TripDocumentServiceModel> allTripDocs = service.getAllTripDocs();

        assertEquals(2, allTripDocs.size());
    }

    @Test
    public void getAllTripDocs_whenNoDocs_shouldReturnEmptyList() {
        tripDocs.clear();
        List<TripDocumentServiceModel> allTripDocs = service.getAllTripDocs();

        assertEquals(0, allTripDocs.size());
    }

    @Test
    public void getAllTripDocsByDriver_whenNoDocs_shouldReturnEmptyList() {
        tripDocs.clear();

        String username = "username";

        List<TripDocumentServiceModel> allTripDocsByDriver = service.getAllTripDocsByDriver(username);

        assertEquals(0, allTripDocsByDriver.size());
    }

    @Test
    public void getAllTripDocsByTrip_whenNoDocs_shouldReturnEmptyList() {
        tripDocs.clear();

        String ref = "ref";

        List<TripDocumentServiceModel> allTripDocsByDriver = service.getAllTripDocsByTrip(ref);

        assertEquals(0, allTripDocsByDriver.size());
    }

    @Test
    public void getAllVehicleDocs_whenDocs_shouldReturnCorrectList() {
        vehicleDocs.clear();
        VehicleDocument document = new VehicleDocument();
        document.setId("id1");
        document.setType(VehicleDocumentType.GO);
        document.setPicture("picture1");
        document.setExpiryDate(LocalDate.of(2019, 12, 21));
        document.setVehicle(new Vehicle());
        VehicleDocument document2 = new VehicleDocument();
        document2.setId("id1");
        document2.setType(VehicleDocumentType.GO);
        document2.setPicture("picture1");
        document2.setExpiryDate(LocalDate.of(2019, 12, 21));
        document2.setVehicle(new Vehicle());
        vehicleDocs.add(document);
        vehicleDocs.add(document2);

        List<VehicleDocumentServiceModel> allVehicleDocs = service.getAllVehicleDocs();

        assertEquals(2, allVehicleDocs.size());
    }

    @Test
    public void getAllVehicleDocs_whenNoDocs_shouldReturnEmptyList() {
        vehicleDocs.clear();
        List<VehicleDocumentServiceModel> allVehicleDocs = service.getAllVehicleDocs();

        assertEquals(0, allVehicleDocs.size());
    }

    @Test
    public void getAllDriverDocs_whenDocs_shouldReturnCorrectList() {
        driverDocs.clear();
        DriverDocument document = new DriverDocument();
        document.setId("id1");
        document.setType(DriverDocumentType.AdrLicense);
        document.setPicture("picture1");
        document.setExpiryDate(LocalDate.of(2019, 12, 21));
        document.setDriver(new Driver());
        DriverDocument document2 = new DriverDocument();
        document2.setId("id1");
        document2.setType(DriverDocumentType.DrivingLicense);
        document2.setPicture("picture1");
        document2.setExpiryDate(LocalDate.of(2019, 12, 21));
        document2.setDriver(new Driver());
        driverDocs.add(document);
        driverDocs.add(document2);

        List<DriverDocumentServiceModel> allDriverDocs = service.getAllDriverDocs();

        assertEquals(2, allDriverDocs.size());
    }

    @Test
    public void getAllDriverDocs_whenNoDocs_shouldReturnEmptyList() {
        driverDocs.clear();
        List<DriverDocumentServiceModel> allDriverDocs = service.getAllDriverDocs();

        assertEquals(0, allDriverDocs.size());
    }

    @Test
    public void getAllCompanyDocs_whenDocs_shouldReturnCorrectList() {
        companyDocs.clear();
        CompanyDocument document = new CompanyDocument();
        document.setId("id1");
        document.setType(CompanyDocumentType.AdrLicense);
        document.setPicture("picture1");
        document.setExpiryDate(LocalDate.of(2019, 12, 21));
        CompanyDocument document2 = new CompanyDocument();
        document2.setId("id1");
        document2.setType(CompanyDocumentType.AdministratorLicense);
        document2.setPicture("picture1");
        document2.setExpiryDate(LocalDate.of(2019, 12, 21));
        companyDocs.add(document);
        companyDocs.add(document2);

        List<CompanyDocumentServiceModel> allCompanyDocs = service.getAllCompanyDocs();

        assertEquals(2, allCompanyDocs.size());
    }

    @Test
    public void getAllCompanyDocs_whenNoDocs_shouldReturnEmptyList() {
        companyDocs.clear();
        List<CompanyDocumentServiceModel> allCompanyDocs = service.getAllCompanyDocs();

        assertEquals(0, allCompanyDocs.size());
    }

    @Test
    public void getAllVehicleDocumentsByVehicle_whenNoDocs_shouldReturnEmptyList() {
        vehicleDocs.clear();

        String regNumber = "regNumber";

        List<VehicleDocumentServiceModel> allVehicleDocumentsByVehicle = service.getAllVehicleDocumentsByVehicle(regNumber);

        assertEquals(0, allVehicleDocumentsByVehicle.size());
    }

    @Test
    public void getAllDriverDocsByDriver_whenNoDocs_shouldReturnEmptyList() {
        driverDocs.clear();

        String username = "username";

        List<DriverDocumentServiceModel> allDriverDocsByDriver = service.getAllDriverDocsByDriver(username);

        assertEquals(0, allDriverDocsByDriver.size());
    }

    @Test
    public void isTripDocValid_whenNoPicture_shouldReturnFalse() {
        AddTripDocumentModel model = new AddTripDocumentModel();
        MultipartFile picture = new MockMultipartFile(" ",  "", " ", " ".getBytes());
        model.setPicture(picture);

        assertFalse(service.isTripDocValid(model));
    }

    @Test
    public void isTripDocValid_whenNoTripRef_shouldReturnFalse() {
        AddTripDocumentModel model = new AddTripDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setTripRef("0");

        assertFalse(service.isTripDocValid(model));
    }

    @Test
    public void isTripDocValid_whenAllValid_shouldReturnTrue() {
        AddTripDocumentModel model = new AddTripDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setTripRef("TripRef");

        assertTrue(service.isTripDocValid(model));
    }

    @Test
    public void isVehicleDocValid_whenDateIsEmpty_shouldReturnFalse() {
        AddVehicleDocumentModel model = new AddVehicleDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("");

        assertFalse(service.isVehicleDocValid(model));
    }

    @Test
    public void isVehicleDocValid_whenDateIsPast_shouldReturnFalse() {
        AddVehicleDocumentModel model = new AddVehicleDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("12/12/2019");

        assertFalse(service.isVehicleDocValid(model));
    }

    @Test
    public void isVehicleDocValid_whenNoPicture_shouldReturnFalse() {
        AddVehicleDocumentModel model = new AddVehicleDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");

        assertFalse(service.isVehicleDocValid(model));
    }

    @Test
    public void isVehicleDocValid_whenNoRegNumber_shouldReturnFalse() {
        AddVehicleDocumentModel model = new AddVehicleDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");
        model.setRegNumber("0");

        assertFalse(service.isVehicleDocValid(model));
    }

    @Test
    public void isVehicleDocValid_whenAllValid_shouldReturnTrue() {
        AddVehicleDocumentModel model = new AddVehicleDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");
        model.setRegNumber("RegNum");

        assertTrue(service.isVehicleDocValid(model));
    }

    @Test
    public void isDriverDocValid_whenDateIsEmpty_shouldReturnFalse() {
        AddDriverDocumentModel model = new AddDriverDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("");

        assertFalse(service.isDriverDocValid(model));
    }

    @Test
    public void isDriverDocValid_whenDateIsPast_shouldReturnFalse() {
        AddDriverDocumentModel model = new AddDriverDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("12/12/2019");

        assertFalse(service.isDriverDocValid(model));
    }

    @Test
    public void isDriverDocValid_whenNoPicture_shouldReturnFalse() {
        AddDriverDocumentModel model = new AddDriverDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");

        assertFalse(service.isDriverDocValid(model));
    }

    @Test
    public void isDriverDocValid_whenNoDriverName_shouldReturnFalse() {
        AddDriverDocumentModel model = new AddDriverDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");
        model.setDriverName("0");

        assertFalse(service.isDriverDocValid(model));
    }

    @Test
    public void isDriverDocValid_whenAllValid_shouldReturnTrue() {
        AddDriverDocumentModel model = new AddDriverDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");
        model.setDriverName("Driver");

        assertTrue(service.isDriverDocValid(model));
    }

    @Test
    public void isCompanyDocValid_whenDateIsEmpty_shouldReturnFalse() {
        AddCompanyDocumentModel model = new AddCompanyDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("");

        assertFalse(service.isCompanyDocValid(model));
    }

    @Test
    public void isCompanyDocValid_whenDateIsPast_shouldReturnFalse() {
        AddCompanyDocumentModel model = new AddCompanyDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("12/12/2019");

        assertFalse(service.isCompanyDocValid(model));
    }

    @Test
    public void isCompanyDocValid_whenNoPicture_shouldReturnFalse() {
        AddCompanyDocumentModel model = new AddCompanyDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");

        assertFalse(service.isCompanyDocValid(model));
    }

    @Test
    public void isCompanyDocValid_whenAllValid_shouldReturnTrue() {
        AddCompanyDocumentModel model = new AddCompanyDocumentModel();
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setExpiryDate("30/12/2019");

        assertTrue(service.isCompanyDocValid(model));
    }
}
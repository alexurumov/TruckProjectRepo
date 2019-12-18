package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import truckmanagementproject.data.models.expenses.TripExpense;
import truckmanagementproject.data.models.expenses.VehicleExpense;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.expenses.TripExpenseRepository;
import truckmanagementproject.data.repositories.expenses.VehicleExpenseRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;
import truckmanagementproject.services.models.expenses.TripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.VehicleExpenseServiceModel;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest extends BaseServiceTest {

    List<TripExpense> tripExpenses;
    List<VehicleExpense> vehicleExpenses;

    @MockBean
    TripExpenseRepository tripExpenseRepository;

    @MockBean
    VehicleExpenseRepository vehicleExpenseRepository;

    @MockBean
    TripRepository tripRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    ValidationUtil validationUtil;

    @Autowired
    ExpenseService service;

    @Override
    protected void beforeEach() {
        tripExpenses = new ArrayList<>();
        vehicleExpenses = new ArrayList<>();

        Mockito.when(tripExpenseRepository.findAll())
                .thenReturn(tripExpenses);

        Mockito.when(vehicleExpenseRepository.findAll())
                .thenReturn(vehicleExpenses);
    }

    @Test
    public void addTripExpense_withInvalidTrip_shouldThrowException() {
        AddTripExpenseServiceModel model = new AddTripExpenseServiceModel();

        String tripRef = "ref";
        Trip trip = null;

        Mockito.when(tripRepository.getByReference(tripRef))
                .thenReturn(trip);

        assertThrows(Exception.class,
                () -> service.addTripExpense(model));
    }

    @Test
    public void addTripExpense_withInvalidExpense_shouldThrowException() {
        AddTripExpenseServiceModel model = new AddTripExpenseServiceModel();

        String tripRef = "ref";
        Trip trip = new Trip();

        Mockito.when(tripRepository.getByReference(tripRef))
                .thenReturn(trip);

        TripExpense expense = new TripExpense();

        Mockito.when(validationUtil.isValid(expense))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addTripExpense(model));
    }

    @Test
    public void addVehicleExpense_withInvalidVehicle_shouldThrowException() {
        AddVehicleExpenseServiceModel model = new AddVehicleExpenseServiceModel();

        String regNumber = "regNumber";
        Vehicle vehicle = null;

        Mockito.when(vehicleRepository.getByRegNumber(regNumber))
                .thenReturn(vehicle);

        assertThrows(Exception.class,
                () -> service.addVehicleExpense(model));
    }

    @Test
    public void addVehicleExpense_withInvalidExpense_shouldThrowException() {
        AddVehicleExpenseServiceModel model = new AddVehicleExpenseServiceModel();

        String regNumber = "regNumber";
        Vehicle vehicle = new Vehicle();

        Mockito.when(vehicleRepository.getByRegNumber(regNumber))
                .thenReturn(vehicle);

        VehicleExpense expense = new VehicleExpense();

        Mockito.when(validationUtil.isValid(expense))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addVehicleExpense(model));
    }

    @Test
    public void getAllTripExpenses_whenTripExpenses_shouldReturnCorrectList() {
        tripExpenses.clear();
        TripExpense expense1 = new TripExpense();
        TripExpense expense2 = new TripExpense();
        tripExpenses.addAll(List.of(expense1, expense2));

        List<TripExpenseServiceModel> allTripExpenses = service.getAllTripExpenses();

        assertEquals(2, allTripExpenses.size());
    }

    @Test
    public void getAllTripExpenses_whenNoTripExpenses_shouldReturnEmptyList() {
        tripExpenses.clear();

        List<TripExpenseServiceModel> allTripExpenses = service.getAllTripExpenses();

        assertEquals(0, allTripExpenses.size());
    }

    @Test
    public void getAllTripExpensesByDriver_whenNoTripExpenses_shouldReturnEmptyList() {
        tripExpenses.clear();

        String username = "username";

        List<TripExpenseServiceModel> allTripExpensesByDriver = service.getAllTripExpensesByDriver(username);

        assertEquals(0, allTripExpensesByDriver.size());
    }

    @Test
    public void getAllTripExpensesByTrip_whenNoTripExpenses_shouldReturnEmptyList() {
        tripExpenses.clear();

        String tripRef = "tripRef";

        List<TripExpenseServiceModel> allTripExpensesByTrip = service.getAllTripExpensesByTrip(tripRef);

        assertEquals(0, allTripExpensesByTrip.size());
    }

    @Test
    public void getAllVehicleExpenses_whenVehicleExpenses_shouldReturnCorrectList() {
        vehicleExpenses.clear();
        VehicleExpense expense1 = new VehicleExpense();
        VehicleExpense expense2 = new VehicleExpense();
        vehicleExpenses.addAll(List.of(expense1, expense2));

        List<VehicleExpenseServiceModel> allVehicleExpenses = service.getAllVehicleExpenses();

        assertEquals(2, allVehicleExpenses.size());
    }

    @Test
    public void getAllVehicleExpenses_whenNoVehicleExpenses_shouldReturnEmptyList() {
        vehicleExpenses.clear();

        List<VehicleExpenseServiceModel> allVehicleExpenses = service.getAllVehicleExpenses();

        assertEquals(0, allVehicleExpenses.size());
    }

    @Test
    public void getAllVehicleExpensesByVehicle_whenNoVehicleExpenses_shouldReturnEmptyList() {
        vehicleExpenses.clear();

        String regNumber = "regNumber";

        List<VehicleExpenseServiceModel> allVehicleExpensesByVehicle = service.getAllVehicleExpensesByVehicle(regNumber);

        assertEquals(0, allVehicleExpensesByVehicle.size());
    }

    @Test
    public void isTripExpenseValid_whenNoDate_shouldReturnFalse() {
        AddTripExpenseModel model = new AddTripExpenseModel();
        model.setDate("");

        assertFalse(service.isTripExpenseValid(model));
    }

    @Test
    public void isTripExpenseValid_whenNoPicture_shouldReturnFalse() {
        AddTripExpenseModel model = new AddTripExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "", "picture", "picture".getBytes());
        model.setPicture(picture);

        assertFalse(service.isTripExpenseValid(model));
    }

    @Test
    public void isTripExpenseValid_whenNoTripRef_shouldReturnFalse() {
        AddTripExpenseModel model = new AddTripExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setTripRef("0");

        assertFalse(service.isTripExpenseValid(model));
    }

    @Test
    public void isTripExpenseValid_whenNoNegativeCost_shouldReturnFalse() {
        AddTripExpenseModel model = new AddTripExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setTripRef("someRef");
        model.setCost(BigDecimal.valueOf(-1));

        assertFalse(service.isTripExpenseValid(model));
    }

    @Test
    public void isTripExpenseValid_whenAllValid_shouldReturnTrue() {
        AddTripExpenseModel model = new AddTripExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setTripRef("someRef");
        model.setCost(BigDecimal.valueOf(1));

        assertTrue(service.isTripExpenseValid(model));
    }

    @Test
    public void isVehicleExpenseValid_whenNoDate_shouldReturnFalse() {
        AddVehicleExpenseModel model = new AddVehicleExpenseModel();
        model.setDate("");

        assertFalse(service.isVehicleExpenseValid(model));
    }

    @Test
    public void isVehicleExpenseValid_whenNoPicture_shouldReturnFalse() {
        AddVehicleExpenseModel model = new AddVehicleExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "", "picture", "picture".getBytes());
        model.setPicture(picture);

        assertFalse(service.isVehicleExpenseValid(model));
    }

    @Test
    public void isVehicleExpenseValid_whenNoVehicleRef_shouldReturnFalse() {
        AddVehicleExpenseModel model = new AddVehicleExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setVehicleRegNumber("0");

        assertFalse(service.isVehicleExpenseValid(model));
    }

    @Test
    public void isVehicleExpenseValid_whenNoNegativeCost_shouldReturnFalse() {
        AddVehicleExpenseModel model = new AddVehicleExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setVehicleRegNumber("someNumber");
        model.setCost(BigDecimal.valueOf(-1));

        assertFalse(service.isVehicleExpenseValid(model));
    }

    @Test
    public void isVehicleExpenseValid_whenAllValid_shouldReturnTrue() {
        AddVehicleExpenseModel model = new AddVehicleExpenseModel();
        model.setDate("someDate");
        MultipartFile picture = new MockMultipartFile("picture",  "picture", "picture", "picture".getBytes());
        model.setPicture(picture);
        model.setVehicleRegNumber("someNumber");
        model.setCost(BigDecimal.valueOf(1));

        assertTrue(service.isVehicleExpenseValid(model));
    }


}
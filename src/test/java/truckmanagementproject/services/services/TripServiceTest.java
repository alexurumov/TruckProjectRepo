package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.expenses.TripExpense;
import truckmanagementproject.data.models.milestones.Milestone;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;
import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.FinishTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.trips.AddTripModel;
import truckmanagementproject.web.models.trips.FinishTripModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest extends BaseServiceTest {

    List<Trip> trips;

    @MockBean
    TripRepository tripRepository;

    @MockBean
    DriverRepository driverRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    ValidationUtil validationUtil;

    @Autowired
    TripService service;

    @Override
    protected void beforeEach() {
        trips = new ArrayList<>();
        Mockito.when(tripRepository.findAll())
                .thenReturn(trips);
    }

    @Test
    public void addTrip_whenInvalidDriver_shouldThrowException() {
        AddTripServiceModel model = new AddTripServiceModel();

        String driverName = "driverName";
        Driver driver = null;
        Mockito.when(driverRepository.getByName(driverName))
                .thenReturn(driver);

        assertThrows(Exception.class,
                () -> service.addTrip(model));
    }

    @Test
    public void addTrip_whenInvalidVehicle_shouldThrowException() {
        AddTripServiceModel model = new AddTripServiceModel();

        String driverName = "driverName";
        Driver driver = new Driver();
        Mockito.when(driverRepository.getByName(driverName))
                .thenReturn(driver);

        String regNumber = "regNumber";
        Vehicle vehicle = null;
        Mockito.when(vehicleRepository.getByRegNumber(regNumber))
                .thenReturn(vehicle);

        assertThrows(Exception.class,
                () -> service.addTrip(model));
    }

    @Test
    public void addTrip_whenInvalidTrip_shouldThrowException() {
        AddTripServiceModel model = new AddTripServiceModel();

        String driverName = "driverName";
        Driver driver = new Driver();
        Mockito.when(driverRepository.getByName(driverName))
                .thenReturn(driver);

        String regNumber = "regNumber";
        Vehicle vehicle = new Vehicle();
        Mockito.when(vehicleRepository.getByRegNumber(regNumber))
                .thenReturn(vehicle);

        Trip trip = new Trip();
        Mockito.when(validationUtil.isValid(trip))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addTrip(model));
    }

    @Test
    public void getTripByReference_whenInvalidReference_shouldThrowException() {
        String reference = "someRef";

        Trip trip = null;
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        assertThrows(Exception.class,
                () -> service.getTripByReference(reference));
    }

    @Test
    public void getTripByReference_whenValid_shouldReturnCorrectModel() throws Exception {
        String reference = "someRef";

        Trip trip = new Trip();

        Milestone collection = new Milestone();
        Milestone delivery = new Milestone();
        trip.setMilestones(List.of(collection, delivery));

        TripExpense expense1 = new TripExpense();
        expense1.setCost(BigDecimal.valueOf(50.00));
        TripExpense expense2 = new TripExpense();
        expense2.setCost(BigDecimal.valueOf(25.00));
        trip.setExpenses(List.of(expense1, expense2));

        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        TripServiceModel model = service.getTripByReference(reference);

        assertEquals(2, model.getMilestones().size());
        assertEquals(BigDecimal.valueOf(75.00), model.getExpensesSum());
    }

    @Test
    public void finishTrip_whenInvalidReference_shouldThrowException() {
        FinishTripServiceModel model = new FinishTripServiceModel();
        String reference = "someRef";
        Trip trip = null;
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        assertThrows(Exception.class,
                () -> service.finishTrip(model, reference));
    }

    @Test
    public void finishTrip_whenInvalidTrip_shouldThrowException() {
        FinishTripServiceModel model = new FinishTripServiceModel();
        String reference = "someRef";
        Trip trip = new Trip();
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        Mockito.when(validationUtil.isValid(trip))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.finishTrip(model, reference));
    }

    @Test
    public void finishTrip_whenValid_shouldSetIsFinishedToTrue() throws Exception {
        FinishTripServiceModel model = new FinishTripServiceModel();
        String reference = "someRef";
        Trip trip = new Trip();
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        Mockito.when(validationUtil.isValid(trip))
                .thenReturn(true);

        service.finishTrip(model, reference);

        assertTrue(trip.getIsFinished());
    }

    @Test
    public void getAllCurrent_whenNoShouldReturnEmptyList() {
        trips.clear();

        List<TripServiceModel> allCurrent = service.getAllCurrent();

        assertEquals(0, allCurrent.size());
    }

    @Test
    public void getAllCurrent_whenTripsShouldReturnCorrectNumber() {
        trips.clear();
        Trip trip1 = new Trip();
        trip1.setIsFinished(false);
        Trip trip2 = new Trip();
        trip2.setIsFinished(false);
        Trip trip3 = new Trip();
        trip3.setIsFinished(true);
        trips.addAll(List.of(trip1, trip2, trip3));

        List<TripServiceModel> allCurrent = service.getAllCurrent();
        assertEquals(2, allCurrent.size());
    }

    @Test
    public void getAllFinished_whenNoShouldReturnEmptyList() {
        trips.clear();

        List<TripServiceModel> allFinished = service.getAllFinished();

        assertEquals(0, allFinished.size());
    }

    @Test
    public void getAllFinished_whenTripsShouldReturnCorrectNumber() {
        trips.clear();
        Trip trip1 = new Trip();
        trip1.setIsFinished(false);
        Trip trip2 = new Trip();
        trip2.setIsFinished(false);
        Trip trip3 = new Trip();
        trip3.setIsFinished(true);
        trips.addAll(List.of(trip1, trip2, trip3));

        List<TripServiceModel> allFinished = service.getAllFinished();
        assertEquals(1, allFinished.size());
    }

    @Test
    public void getAllTripsByDriverId_whenNoTrips_shouldReturnEmptyList() {
        String id = "someId";
        List<TripServiceModel> allTripsByDriverId = service.getAllTripsByDriverId(id);

        assertEquals(0, allTripsByDriverId.size());
    }

    @Test
    public void getAllTripsByDriver_whenNoTrips_shouldReturnEmptyList() {
        String username = "someUsername";
        List<TripServiceModel> allTripsByDriver = service.getAllTripsByDriver(username);

        assertEquals(0, allTripsByDriver.size());
    }

    @Test
    public void getAllTripsByVehicle_whenNoTrips_shouldReturnEmptyList() {
        String vehicleId = "someId";
        List<TripServiceModel> allTripsByVehicle = service.getAllTripsByVehicle(vehicleId);

        assertEquals(0, allTripsByVehicle.size());
    }

    @Test
    public void isReferenceTaken_whenTaken_shouldReturnTrue() {
        String reference = "someRef";
        Trip trip = new Trip();
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        assertTrue(service.isReferenceTaken(reference));
    }

    @Test
    public void isReferenceTaken_whenFre_shouldReturnFalse() {
        String reference = "someRef";
        Trip trip = null;
        Mockito.when(tripRepository.getByReference(reference))
                .thenReturn(trip);

        assertFalse(service.isReferenceTaken(reference));
    }

    @Test
    public void isAddTripModelValid_whenInvalidRef_shoudReturnFalse() {
        AddTripModel model = new AddTripModel();
        model.setReference("");
        model.setDate("someDate");
        model.setDirection("someDirection");
        model.setAdr(true);
        model.setDriverName("someName");
        model.setVehicleRegNumber("someNumber");

        assertFalse(service.isAddTripModelValid(model));
    }

    @Test
    public void isAddTripModelValid_whenInvalidDriverName_shoudReturnFalse() {
        AddTripModel model = new AddTripModel();
        model.setReference("someRef");
        model.setDate("someDate");
        model.setDirection("someDirection");
        model.setAdr(true);
        model.setDriverName("0");
        model.setVehicleRegNumber("someNumber");

        assertFalse(service.isAddTripModelValid(model));
    }

    @Test
    public void isAddTripModelValid_whenDateEmpty_shoudReturnFalse() {
        AddTripModel model = new AddTripModel();
        model.setReference("someRef");
        model.setDate("");
        model.setDirection("someDirection");
        model.setAdr(true);
        model.setDriverName("someName");
        model.setVehicleRegNumber("someNumber");

        assertFalse(service.isAddTripModelValid(model));
    }

    @Test
    public void isAddTripModelValid_whenVehicleRegNumberInvalid_shoudReturnFalse() {
        AddTripModel model = new AddTripModel();
        model.setReference("someRef");
        model.setDate("someDate");
        model.setDirection("someDirection");
        model.setAdr(true);
        model.setDriverName("someName");
        model.setVehicleRegNumber("0");

        assertFalse(service.isAddTripModelValid(model));
    }

    @Test
    public void areAllMilestonesFinished_whenFinished_shouldReturnTrue() {
        TripServiceModel model = new TripServiceModel();
        MilestoneServiceModel milestone1 = new MilestoneServiceModel();
        milestone1.setIsFinished(true);
        MilestoneServiceModel milestone2 = new MilestoneServiceModel();
        milestone2.setIsFinished(true);
        model.setMilestones(List.of(milestone1, milestone2));

        assertTrue(service.areAllMilestonesFinished(model));
    }

    @Test
    public void areAllMilestonesFinished_whenNotFinished_shouldReturnFalse() {
        TripServiceModel model = new TripServiceModel();
        MilestoneServiceModel milestone1 = new MilestoneServiceModel();
        milestone1.setIsFinished(true);
        MilestoneServiceModel milestone2 = new MilestoneServiceModel();
        milestone2.setIsFinished(false);
        model.setMilestones(List.of(milestone1, milestone2));

        assertFalse(service.areAllMilestonesFinished(model));
    }

    @Test
    public void isFinishTripValid_whenInvalidEmptyKm_shouldReturnFalse() {
        FinishTripModel model = new FinishTripModel();
        model.setEmptyKm(null);

        assertFalse(service.isFinishTripValid(model));
    }

    @Test
    public void isFinishTripValid_whenInvalidTripKm_shouldReturnFalse() {
        FinishTripModel model = new FinishTripModel();
        model.setTripKm(null);

        assertFalse(service.isFinishTripValid(model));
    }

    @Test
    public void isFinishTripValid_whenInvalidEmptyPallets_shouldReturnFalse() {
        FinishTripModel model = new FinishTripModel();
        model.setEmptyPallets(null);

        assertFalse(service.isFinishTripValid(model));
    }

    @Test
    public void isFinishTripValid_whenValid_shouldReturnTrue() {
        FinishTripModel model = new FinishTripModel();
        model.setEmptyKm(1);
        model.setTripKm(1);
        model.setEmptyPallets(1);

        assertTrue(service.isFinishTripValid(model));
    }

}
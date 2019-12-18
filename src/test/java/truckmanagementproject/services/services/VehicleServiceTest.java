package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.services.models.managers.ManagerServiceModel;
import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;
import truckmanagementproject.services.models.vehicles.VehicleServiceModel;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.services.services.validations.AddManagerValidationService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest extends BaseServiceTest {

    List<Vehicle> vehicles;

    @MockBean
    ValidationUtil validationUtil;

    @MockBean
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleService service;

    @Override
    protected void beforeEach() {
        vehicles = new ArrayList<>();

        Mockito.when(vehicleRepository.findAll())
                .thenReturn(vehicles);
    }

    @Test
    public void registerVehicle_withRegNumberTaken_shouldThrowException() {
        AddVehicleServiceModel model = new AddVehicleServiceModel();
        String regNumber = "someRegNumber";
        Mockito.when(vehicleRepository.existsByRegNumber(regNumber))
                .thenReturn(true);

        assertThrows(Exception.class,
                () -> service.registerVehicle(model));
    }

    @Test
    public void registerVehicle_withInvalidVehicle_shouldThrowException() {
        AddVehicleServiceModel model = new AddVehicleServiceModel();

        Vehicle vehicle = new Vehicle();
        Mockito.when(validationUtil.isValid(vehicle))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.registerVehicle(model));
    }

    @Test
    public void getAllVehicles_whenVehicles_shouldReturnCorrectList() {
        vehicles.clear();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        vehicles.addAll(List.of(vehicle1, vehicle2));

        List<VehicleServiceModel> allVehicles = service.getAllVehicles();

        assertEquals(2, allVehicles.size());
    }

    @Test
    public void getAllVehicles_whenNoVehicles_shouldReturnEmptyList() {
        vehicles.clear();

        List<VehicleServiceModel> allVehicles = service.getAllVehicles();

        assertEquals(0, allVehicles.size());
    }

}
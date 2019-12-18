package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.services.models.drivers.DriverServiceModel;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.services.validations.AddDriverValidationService;
import truckmanagementproject.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceTest extends BaseServiceTest {

    List<Driver> drivers;

    @MockBean
    AddDriverValidationService addDriverValidationService;

    @MockBean
    ValidationUtil validationUtil;

    @MockBean
    DriverRepository driverRepository;

    @Autowired
    DriverService service;

    @Override
    protected void beforeEach() {
        drivers = new ArrayList<>();

        Mockito.when(driverRepository.findAll())
                .thenReturn(drivers);
    }

    @Test
    public void registerDriver_withInvalidModel_shouldThrowException() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        Mockito.when(addDriverValidationService.isValid(model))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.registerDriver(model));
    }

    @Test
    public void registerDriver_withInvalidDriver_shouldThrowException() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        Mockito.when(addDriverValidationService.isValid(model))
                .thenReturn(true);

        Driver driver = new Driver();
        Mockito.when(validationUtil.isValid(driver))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.registerDriver(model));
    }

    @Test
    public void getAllDrivers_whenDrivers_shouldReturnCorrectList() {
        drivers.clear();
        Driver driver1 = new Driver();
        Driver driver2 = new Driver();
        drivers.addAll(List.of(driver1, driver2));

        List<DriverServiceModel> allDrivers = service.getAllDrivers();

        assertEquals(2, allDrivers.size());
    }

    @Test
    public void getAllDrivers_whenNoDrivers_shouldReturnEmptyList() {
        drivers.clear();

        List<DriverServiceModel> allDrivers = service.getAllDrivers();

        assertEquals(0, allDrivers.size());
    }

}
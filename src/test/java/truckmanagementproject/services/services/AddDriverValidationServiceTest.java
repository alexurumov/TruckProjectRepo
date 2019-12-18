package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.services.services.validations.AddDriverValidationService;

import static org.junit.jupiter.api.Assertions.*;

class AddDriverValidationServiceTest extends BaseServiceTest {

    @MockBean
    DriverRepository driverRepository;

    @Autowired
    AddDriverValidationService service;

    @Test
    public void isValid_whenPasswordsNOTValid_shouldReturnFalse() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        model.setPassword("pass1");
        model.setConfirmPassword("invalidPass");

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenUsernameIsNOTFree_shouldReturnFalse() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        String username = "someUsername";
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");
        model.setName("correctName");
        model.setUsername(username);

        Mockito.when(driverRepository.existsByUsername(username))
                .thenReturn(true);

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenNameIsNOTFree_shouldReturnFalse() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        String name = "someName";
        model.setName(name);
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");
        model.setUsername("someUsername");

        Mockito.when(driverRepository.existsByName(name))
                .thenReturn(true);

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenAllIsValid_shouldReturnTrue() {
        AddDriverServiceModel model = new AddDriverServiceModel();
        String name = "someName";
        String username = "someUsername";
        model.setName(name);
        model.setUsername(username);
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");

        Mockito.when(driverRepository.existsByUsername(username))
                .thenReturn(false);

        Mockito.when(driverRepository.existsByName(name))
                .thenReturn(false);

        assertTrue(service.isValid(model));
    }

}
package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.services.services.validations.AddManagerValidationService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddManagerValidationServiceTest extends BaseServiceTest {

    @MockBean
    ManagerRepository managerRepository;

    @Autowired
    AddManagerValidationService service;

    @Test
    public void isValid_whenPasswordsNOTValid_shouldReturnFalse() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        model.setPassword("pass1");
        model.setConfirmPassword("invalidPass");

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenUsernameIsNOTFree_shouldReturnFalse() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        String username = "someUsername";
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");
        model.setName("correctName");
        model.setUsername(username);

        Mockito.when(managerRepository.existsByUsername(username))
                .thenReturn(true);

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenNameIsNOTFree_shouldReturnFalse() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        String name = "someName";
        model.setName(name);
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");
        model.setUsername("someUsername");

        Mockito.when(managerRepository.existsByName(name))
                .thenReturn(true);

        assertFalse(service.isValid(model));
    }

    @Test
    public void isValid_whenAllIsValid_shouldReturnTrue() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        String name = "someName";
        String username = "someUsername";
        model.setName(name);
        model.setUsername(username);
        model.setPassword("correctPass");
        model.setConfirmPassword("correctPass");

        Mockito.when(managerRepository.existsByUsername(username))
                .thenReturn(false);

        Mockito.when(managerRepository.existsByName(name))
                .thenReturn(false);

        assertTrue(service.isValid(model));
    }

}
package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.services.models.managers.ManagerServiceModel;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.services.services.validations.AddManagerValidationService;
import truckmanagementproject.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManagerServiceTest extends BaseServiceTest {

    List<Manager> managers;

    @MockBean
    AddManagerValidationService addManagerValidationService;

    @MockBean
    ValidationUtil validationUtil;

    @MockBean
    ManagerRepository managerRepository;

    @Autowired
    ManagerService service;

    @Override
    protected void beforeEach() {
        managers = new ArrayList<>();

        Mockito.when(managerRepository.findAll())
                .thenReturn(managers);
    }

    @Test
    public void registerManager_withInvalidModel_shouldThrowException() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        Mockito.when(addManagerValidationService.isValid(model))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.registerManager(model));
    }

    @Test
    public void registerManager_withInvalidManager_shouldThrowException() {
        AddManagerServiceModel model = new AddManagerServiceModel();
        Mockito.when(addManagerValidationService.isValid(model))
                .thenReturn(true);

        Manager manager = new Manager();
        Mockito.when(validationUtil.isValid(manager))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.registerManager(model));
    }

    @Test
    public void getAllManagers_whenManagers_shouldReturnCorrectList() {
        managers.clear();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        managers.addAll(List.of(manager1, manager2));

        List<ManagerServiceModel> allManagers = service.getAllManagers();

        assertEquals(2, allManagers.size());
    }

    @Test
    public void getAllManagers_whenNoManagers_shouldReturnEmptyList() {
        managers.clear();

        List<ManagerServiceModel> allManagers = service.getAllManagers();

        assertEquals(0, allManagers.size());
    }

}
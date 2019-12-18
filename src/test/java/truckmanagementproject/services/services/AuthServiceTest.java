package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import truckmanagementproject.data.models.users.Admin;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.users.Manager;
import truckmanagementproject.data.repositories.users.AdminRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.auth.LoginUserServiceModel;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.hashing.HashingService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest extends BaseServiceTest {

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    ManagerRepository managerRepository;

    @MockBean
    DriverRepository driverRepository;

    @MockBean
    HashingService hashingService;

    @Autowired
    AuthService service;

    @Test
    public void login_whenCorrectAdmin_shouldReturnAdmin() {
        String name = "admin";
        String username = "admin";
        String password = "123";
        String role = "Admin";

        Mockito.when(hashingService.hash(password))
                .thenReturn(password);

        LoginUserServiceModel adminUser = new LoginUserServiceModel(username, password, null);
        Mockito.when(adminRepository.existsByUsernameAndPassword(adminUser.getUsername(), adminUser.getPassword()))
                .thenReturn(true);

        Admin admin = new Admin();
        admin.setName(name);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRole(role);

        Mockito.when(adminRepository.getByUsernameAndPassword(adminUser.getUsername(), adminUser.getPassword()))
                .thenReturn(admin);

        LoginUserServiceModel actual = service.login(adminUser);

        assertEquals(role, actual.getRole());
        assertEquals(username, actual.getUsername());
        assertEquals(password, actual.getPassword());
    }

    @Test
    public void login_whenWrongAdmin_shouldReturnNull() {
        String username = "admin";
        String password = "123";

        Mockito.when(hashingService.hash(password))
                .thenReturn(password);

        LoginUserServiceModel adminUser = new LoginUserServiceModel(username, password, null);
        Mockito.when(adminRepository.existsByUsernameAndPassword(adminUser.getUsername(), adminUser.getPassword()))
                .thenReturn(false);

        LoginUserServiceModel actual = service.login(adminUser);

        assertNull(actual);
    }

    @Test
    public void login_whenCorrectManager_shouldReturnManager() {
        String name = "manager";
        String username = "manager";
        String password = "123";
        String role = "Manager";

        Mockito.when(hashingService.hash(password))
                .thenReturn(password);

        LoginUserServiceModel managerUser = new LoginUserServiceModel(username, password, null);
        Mockito.when(managerRepository.existsByUsernameAndPassword(managerUser.getUsername(), managerUser.getPassword()))
                .thenReturn(true);

        Manager manager = new Manager();
        manager.setName(name);
        manager.setUsername(username);
        manager.setPassword(password);
        manager.setRole(role);

        Mockito.when(managerRepository.getByUsernameAndPassword(managerUser.getUsername(), managerUser.getPassword()))
                .thenReturn(manager);

        LoginUserServiceModel actual = service.login(managerUser);

        assertEquals(role, actual.getRole());
        assertEquals(username, actual.getUsername());
        assertEquals(password, actual.getPassword());
    }

    @Test
    public void login_whenCorrectDiver_shouldReturnDiver() {
        String name = "driver";
        String username = "driver";
        String password = "123";
        String role = "Manager";

        Mockito.when(hashingService.hash(password))
                .thenReturn(password);

        LoginUserServiceModel driverUser = new LoginUserServiceModel(username, password, null);
        Mockito.when(driverRepository.existsByUsernameAndPassword(driverUser.getUsername(), driverUser.getPassword()))
                .thenReturn(true);

        Driver driver = new Driver();
        driver.setName(name);
        driver.setUsername(username);
        driver.setPassword(password);
        driver.setRole(role);

        Mockito.when(driverRepository.getByUsernameAndPassword(driverUser.getUsername(), driverUser.getPassword()))
                .thenReturn(driver);

        LoginUserServiceModel actual = service.login(driverUser);

        assertEquals(role, actual.getRole());
        assertEquals(username, actual.getUsername());
        assertEquals(password, actual.getPassword());
    }

    @Test
    public void login_whenNoSuchUser_shouldReturnNull() {
        String username = "invalid";
        String password = "invalid";

        Mockito.when(hashingService.hash(password))
                .thenReturn(password);

        LoginUserServiceModel user = new LoginUserServiceModel(username, password, null);

        Mockito.when(adminRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(false);
        Mockito.when(driverRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(false);
        Mockito.when(driverRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(false);

        LoginUserServiceModel actual = service.login(user);

        assertNull(actual);
    }

    @Test
    public void isUserDriver_whenCorrect_shouldReturnTrue() {
        String role = "Driver";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertTrue(service.isUserDriver(model));
    }

    @Test
    public void isUserDriver_whenNull_shouldReturnFalse() {
        LoginUserViewModel model = null;
        assertFalse(service.isUserDriver(model));
    }

    @Test
    public void isUserDriver_whenNotDriver_shouldReturnFalse() {
        String role = "another";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertFalse(service.isUserDriver(model));
    }

    @Test
    public void isUserManager_whenCorrect_shouldReturnTrue() {
        String role = "Manager";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertTrue(service.isUserManager(model));
    }

    @Test
    public void isUserManager_whenNull_shouldReturnFalse() {
        LoginUserViewModel model = null;
        assertFalse(service.isUserManager(model));
    }

    @Test
    public void isUserManager_whenNotManager_shouldReturnFalse() {
        String role = "another";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertFalse(service.isUserManager(model));
    }

    @Test
    public void isUserAdmin_whenCorrect_shouldReturnTrue() {
        String role = "Admin";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertTrue(service.isUserAdmin(model));
    }

    @Test
    public void isUserAdmin_whenNull_shouldReturnFalse() {
        LoginUserViewModel model = null;
        assertFalse(service.isUserAdmin(model));
    }

    @Test
    public void isUserAdmin_whenNotAdmin_shouldReturnFalse() {
        String role = "another";
        LoginUserViewModel model = new LoginUserViewModel();
        model.setRole(role);

        assertFalse(service.isUserAdmin(model));
    }
}
package truckmanagementproject.web.controllers.base;

import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;

public abstract class BaseController {

    private final AuthService authService;

    protected BaseController(AuthService authService) {
        this.authService = authService;
    }

    protected void authorizeDriver(LoginUserViewModel user) throws Exception {
        if (!authService.isUserDriver(user)) {
            throw new Exception("Unauthorized user");
        }
    }

    protected void authorizeManager(LoginUserViewModel user) throws Exception {
        if (!authService.isUserManager(user)) {
            throw new Exception("Unauthorized user");
        }
    }

    protected void authorizeAdmin(LoginUserViewModel user) throws Exception {
        if (!authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }
    }

    protected void authorizeAdminAndManager(LoginUserViewModel user) throws Exception {
        if (!authService.isUserAdmin(user) && !authService.isUserManager(user)) {
            throw new Exception("Unauthorized user");
        }
    }

}

package truckmanagementproject.services.services.auth;

import org.springframework.stereotype.Service;
import truckmanagementproject.services.models.auth.LoginUserServiceModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;

public interface AuthService {
    LoginUserServiceModel login(LoginUserServiceModel model);

    boolean isUserDriver(LoginUserViewModel user);

    boolean isUserManager(LoginUserViewModel user);

    boolean isUserAdmin(LoginUserViewModel user);

}

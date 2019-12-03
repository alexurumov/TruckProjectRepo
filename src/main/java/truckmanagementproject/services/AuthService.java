package truckmanagementproject.services;

import org.springframework.stereotype.Service;
import truckmanagementproject.services.models.auth.LoginUserServiceModel;

@Service
public interface AuthService {
    LoginUserServiceModel login(LoginUserServiceModel model);
}

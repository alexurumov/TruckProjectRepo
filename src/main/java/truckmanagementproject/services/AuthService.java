package truckmanagementproject.services;

import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.users.User;
import truckmanagementproject.services.models.LoginUserServiceModel;

@Service
public interface AuthService {
    LoginUserServiceModel login(LoginUserServiceModel model);
}

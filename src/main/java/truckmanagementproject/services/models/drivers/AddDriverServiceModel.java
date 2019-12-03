package truckmanagementproject.services.models.drivers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddDriverServiceModel {

    private String name;
    private String username;
    private String password;
    private String confirmPassword;
}

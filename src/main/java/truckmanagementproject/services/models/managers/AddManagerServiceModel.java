package truckmanagementproject.services.models.managers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddManagerServiceModel {

    private String name;
    private String username;
    private String password;
    private String confirmPassword;
}

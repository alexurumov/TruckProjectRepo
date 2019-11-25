package truckmanagementproject.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddDriverModel {

    private String name;
    private String username;
    private String password;
    private String confirmPassword;
}
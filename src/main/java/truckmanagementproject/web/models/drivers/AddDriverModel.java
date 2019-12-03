package truckmanagementproject.web.models.drivers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AddDriverModel {

    @Pattern(regexp = "^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$")
    private String name;

    @Pattern(regexp = "[A-Z]{2}.+")
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}

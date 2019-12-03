package truckmanagementproject.web.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserViewModel {
    private String username;
    private String role;
    private String shortUsername;
}

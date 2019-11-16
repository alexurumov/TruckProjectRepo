package truckmanagementproject.web.models;

import javassist.bytecode.ByteArray;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateModel {
    private String username;
    private String password;
    private String email;
    private String picture;
}

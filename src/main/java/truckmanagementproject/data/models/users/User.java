package truckmanagementproject.data.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Getter
@Setter
@NoArgsConstructor
public abstract class User extends BaseEntity {

    @Column(updatable = false, insertable = false)
    private String role;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$")
    private String name;

    @Column(name = "username", nullable = false)
    @Pattern(regexp = "[A-Z]{2}.+")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

}

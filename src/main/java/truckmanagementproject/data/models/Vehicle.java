package truckmanagementproject.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
@NoArgsConstructor
public abstract class Vehicle extends BaseEntity {

    @Column(updatable = false, insertable = false)
    private String type;

    @Column(name = "reg_number", nullable = false, unique = true, updatable = false)
    private String regNumber;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VehicleDocument> documents;

}

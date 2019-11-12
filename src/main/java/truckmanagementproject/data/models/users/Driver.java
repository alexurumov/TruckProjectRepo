package truckmanagementproject.data.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Driver")
@Getter
@Setter
@NoArgsConstructor
public class Driver extends User {

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DriverDocument> driverDocuments;
}

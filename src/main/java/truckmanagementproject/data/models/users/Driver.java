package truckmanagementproject.data.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Driver")
@Getter
@Setter
@NoArgsConstructor
public class Driver extends User {

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<DriverDocument> driverDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Trip> trips = new ArrayList<>();


}

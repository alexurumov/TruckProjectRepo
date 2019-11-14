package truckmanagementproject.data.models.vehicles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;
import truckmanagementproject.data.models.documents.VehicleDocument;
import truckmanagementproject.data.models.expenses.VehicleExpense;
import truckmanagementproject.data.models.trips.Trip;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle extends BaseEntity {

    @Column(name = "reg_number", nullable = false, unique = true, updatable = false)
    private String regNumber;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleExpense> expenses = new ArrayList<>();

}

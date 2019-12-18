package truckmanagementproject.data.models.trips;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.expenses.Expense;
import truckmanagementproject.data.models.expenses.TripExpense;
import truckmanagementproject.data.models.milestones.Milestone;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "direction", nullable = false)
    private String direction;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "empty_km")
    private Integer emptyKm = 0;

    @Column(name = "trip_km")
    private Integer tripKm = 0;

    @Column(name = "adr", nullable = false)
    private Boolean adr = false;

    @Column(name = "empty_pallets")
    private Integer emptyPallets = 0;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Milestone> milestones = new ArrayList<>();

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TripExpense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<TripDocument> documents = new ArrayList<>();

}

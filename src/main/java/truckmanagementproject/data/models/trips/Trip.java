package truckmanagementproject.data.models.trips;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trips")
@Getter
@Setter
//@NoArgsConstructor
public class Trip extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "direction", nullable = false)
    private String direction;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "empty_km")
    private Integer emptyKm;

    @Column(name = "trip_km")
    private Integer tripKm;

    @Column(name = "adr", nullable = false)
    private Boolean adr = false;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "empty_pallets")
    private Integer emptyPallets;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Milestone> milestones;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TripExpense> expenses;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TripDocument> documents;

    public Trip() {
//        BigDecimal sum = BigDecimal.valueOf((this.emptyKm + this.tripKm) * 1.09);
//        BigDecimal expenses = this.getExpenses().stream().map(Expense::getCost).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
//        sum = sum.add(expenses);
//        if (this.getAdr()) {
//            sum = sum.add(BigDecimal.valueOf(50.00));
//        }
//        this.setPrice(sum);
    }
}

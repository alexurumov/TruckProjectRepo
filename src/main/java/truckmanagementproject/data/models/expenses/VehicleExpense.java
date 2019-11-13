package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.vehicles.Vehicle;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_expenses")
@Getter
@Setter
@NoArgsConstructor
public class VehicleExpense extends Expense {

    @Column(name = "truck_expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleExpenseType truckExpenseType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "picture")
    private String picture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

}

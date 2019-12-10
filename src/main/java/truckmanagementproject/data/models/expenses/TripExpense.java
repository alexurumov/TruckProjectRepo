package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "trip_expenses")
@Getter
@Setter
@NoArgsConstructor
public class TripExpense extends Expense {

    @Column(name = "trip_expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TripExpenseType type;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "picture", nullable = false)
    private String picture;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;
}

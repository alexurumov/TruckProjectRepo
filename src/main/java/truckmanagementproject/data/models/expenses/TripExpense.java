package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Trip")
@Getter
@Setter
@NoArgsConstructor
public class TripExpense extends Expense {
    public TripExpense(String type) {
        super(type);
    }

    @Column(name = "trip_expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TripExpenseType tripExpenseType;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "picture")
    private String picture;
}

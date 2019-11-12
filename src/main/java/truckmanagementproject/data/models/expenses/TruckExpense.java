package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Truck")
@Getter
@Setter
@NoArgsConstructor
public class TruckExpense extends Expense {
    public TruckExpense(String type) {
        super(type);
    }

    @Column(name = "truck_expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TruckExpenseType truckExpenseType;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "picture")
    private String picture;
}

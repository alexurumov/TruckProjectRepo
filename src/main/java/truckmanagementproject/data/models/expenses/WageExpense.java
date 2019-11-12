package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.expenses.Expense;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "DriverWage")
@Getter
@Setter
@NoArgsConstructor
public class WageExpense extends Expense {
    public WageExpense(String type) {
        super(type);
    }
}

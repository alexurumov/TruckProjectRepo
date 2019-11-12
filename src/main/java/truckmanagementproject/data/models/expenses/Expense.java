package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expenses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
@NoArgsConstructor
public abstract class Expense extends BaseEntity {

    @Column(updatable = false, insertable = false)
    private String type;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    protected Expense(String type) {
        this.type = type;
    }
}

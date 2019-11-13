package truckmanagementproject.data.models.expenses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Expense extends BaseEntity {

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;
}

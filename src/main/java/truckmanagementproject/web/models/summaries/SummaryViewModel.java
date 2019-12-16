package truckmanagementproject.web.models.summaries;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SummaryViewModel {
    private Integer emptyKm;
    private Integer tripKm;
    private BigDecimal expenses;
    private BigDecimal fuel;
    private BigDecimal adBlue;
    private BigDecimal toll;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
}

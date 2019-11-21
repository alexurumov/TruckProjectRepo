package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.web.models.AddTripModel;

@Controller
public class SummaryController {

    @GetMapping("/summary/overall")
    public String getOverallSummary() {
        return "overall-summary";
    }

    @GetMapping("/summary/by-truck")
    public String getSummaryByTruck() {
        return "summary-by-truck";
    }

    @GetMapping("/summary/by-driver")
    public String getSummaryByDriver() {
        return "summary-by-driver";
    }
}

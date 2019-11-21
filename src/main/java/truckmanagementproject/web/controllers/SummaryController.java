package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SummaryController {

    @GetMapping("/summary/overall")
    public String getOverallSummary() {
        return "summary/overall-summary";
    }

    @GetMapping("/summary/by-truck")
    public String getSummaryByTruck() {
        return "summary/summary-by-truck";
    }

    @GetMapping("/summary/by-driver")
    public String getSummaryByDriver() {
        return "summary/summary-by-driver";
    }
}

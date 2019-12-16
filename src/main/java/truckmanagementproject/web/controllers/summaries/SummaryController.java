package truckmanagementproject.web.controllers.summaries;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.summaries.SummaryService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.summaries.SummaryViewModel;

import javax.servlet.http.HttpSession;

@Controller
public class SummaryController {

    private final SummaryService summaryService;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Autowired
    public SummaryController(SummaryService summaryService, AuthService authService, ModelMapper mapper) {
        this.summaryService = summaryService;
        this.authService = authService;
        this.mapper = mapper;
    }

    @GetMapping("/summary/overall")
    public ModelAndView getOverallSummary(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserManager(user) && !authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }

        SummaryViewModel summary = mapper.map(summaryService.getSummary(), SummaryViewModel.class);

        modelAndView.addObject("summary", summary);
        modelAndView.setViewName("summary/overall-summary");
        return modelAndView;
    }
}

package truckmanagementproject.web.controllers.summaries;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.summaries.SummaryService;
import truckmanagementproject.web.controllers.base.BaseController;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.summaries.SummaryViewModel;

import javax.servlet.http.HttpSession;

@Controller
public class SummaryController extends BaseController {

    private final SummaryService summaryService;
    private final ModelMapper mapper;

    @Autowired
    public SummaryController(SummaryService summaryService, AuthService authService, ModelMapper mapper) {
        super(authService);
        this.summaryService = summaryService;
        this.mapper = mapper;
    }

    @GetMapping("/summary/overall")
    public ModelAndView getOverallSummary(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        SummaryViewModel summary = mapper.map(summaryService.getSummary(), SummaryViewModel.class);

        modelAndView.addObject("summary", summary);
        modelAndView.setViewName("summary/overall-summary");
        return modelAndView;
    }
}

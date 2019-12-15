package truckmanagementproject.web.interceptors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.expenses.TripExpenseViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllTripExpensesInterceptor extends HandlerInterceptorAdapter {

    private final ExpenseService expenseService;
    private final ModelMapper mapper;

    @Autowired
    public AllTripExpensesInterceptor(ExpenseService expenseService, ModelMapper mapper) {
        this.expenseService = expenseService;
        this.mapper = mapper;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");

        if (user.getRole().equals("Driver")) {
            List<TripExpenseViewModel> expenses = expenseService.getAllTripExpensesByDriver(user.getUsername())
                    .stream()
                    .map(expense -> mapper.map(expense, TripExpenseViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("expenses", expenses);
            modelAndView.setViewName("expenses/trip/all");
        } else {
            List<TripExpenseViewModel> expenses = expenseService.getAllTripExpenses()
                    .stream()
                    .map(expense -> mapper.map(expense, TripExpenseViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("expenses", expenses);
            modelAndView.setViewName("expenses/trip/all");
        }
    }
}

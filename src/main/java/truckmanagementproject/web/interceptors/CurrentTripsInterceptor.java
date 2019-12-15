package truckmanagementproject.web.interceptors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.trips.TripViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrentTripsInterceptor extends HandlerInterceptorAdapter {

    private final TripService tripService;
    private final ModelMapper mapper;

    @Autowired
    public CurrentTripsInterceptor(TripService tripService, ModelMapper mapper) {
        this.tripService = tripService;
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
            String driverUsername = user.getUsername();
            List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                    .stream()
                    .filter(trip -> !trip.getIsFinished())
                    .map(trip -> mapper.map(trip, TripViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("trips", trips);
        } else {
            List<TripViewModel> trips = tripService.getAllCurrent()
                    .stream()
                    .map(trip -> mapper.map(trip, TripViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("trips", trips);
        }
    }
}

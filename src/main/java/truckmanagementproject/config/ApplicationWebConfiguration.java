package truckmanagementproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import truckmanagementproject.web.interceptors.AllTripDocsInterceptor;
import truckmanagementproject.web.interceptors.AllTripExpensesInterceptor;
import truckmanagementproject.web.interceptors.CurrentTripsInterceptor;
import truckmanagementproject.web.interceptors.FinishedTripsInterceptor;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    private final AllTripDocsInterceptor allTripDocsInterceptor;
    private final AllTripExpensesInterceptor allTripExpensesInterceptor;
    private final CurrentTripsInterceptor currentTripsInterceptor;
    private final FinishedTripsInterceptor finishedTripsInterceptor;

    @Autowired
    public ApplicationWebConfiguration(AllTripDocsInterceptor allTripDocsInterceptor, AllTripExpensesInterceptor allTripExpensesInterceptor, CurrentTripsInterceptor currentTripsInterceptor, FinishedTripsInterceptor finishedTripsInterceptor) {
        this.allTripDocsInterceptor = allTripDocsInterceptor;
        this.allTripExpensesInterceptor = allTripExpensesInterceptor;
        this.currentTripsInterceptor = currentTripsInterceptor;
        this.finishedTripsInterceptor = finishedTripsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allTripDocsInterceptor).addPathPatterns("/documents/trip/all");
        registry.addInterceptor(allTripExpensesInterceptor).addPathPatterns("/expenses/trip/all");
        registry.addInterceptor(currentTripsInterceptor).addPathPatterns("/trips/current");
        registry.addInterceptor(finishedTripsInterceptor).addPathPatterns("/trips/finished");
    }

}

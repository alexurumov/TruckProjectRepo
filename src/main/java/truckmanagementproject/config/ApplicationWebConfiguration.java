package truckmanagementproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import truckmanagementproject.web.interceptors.AllTripDocsInterceptor;
import truckmanagementproject.web.interceptors.AllTripExpensesInterceptor;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    private final AllTripDocsInterceptor allTripDocsInterceptor;
    private final AllTripExpensesInterceptor allTripExpensesInterceptor;

    @Autowired
    public ApplicationWebConfiguration(AllTripDocsInterceptor allTripDocsInterceptor, AllTripExpensesInterceptor allTripExpensesInterceptor) {
        this.allTripDocsInterceptor = allTripDocsInterceptor;
        this.allTripExpensesInterceptor = allTripExpensesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allTripDocsInterceptor).addPathPatterns("/documents/trip/all");
        registry.addInterceptor(allTripExpensesInterceptor).addPathPatterns("/expenses/trip/all");
    }
}

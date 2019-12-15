package truckmanagementproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import truckmanagementproject.web.interceptors.AllTripDocsInterceptor;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    private final AllTripDocsInterceptor allTripDocsInterceptor;

    @Autowired
    public ApplicationWebConfiguration(AllTripDocsInterceptor allTripDocsInterceptor) {
        this.allTripDocsInterceptor = allTripDocsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allTripDocsInterceptor).addPathPatterns("/documents/trip/all");
    }
}

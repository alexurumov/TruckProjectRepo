package truckmanagementproject.web.interceptors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.documents.TripDocumentViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllTripDocsInterceptor extends HandlerInterceptorAdapter {

    private final DocumentService documentService;
    private final ModelMapper mapper;

    @Autowired
    public AllTripDocsInterceptor(DocumentService documentService, ModelMapper mapper) {
        this.documentService = documentService;
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
            List<TripDocumentViewModel> documents = documentService.getAllTripDocsByDriver(user.getUsername())
                    .stream()
                    .map(doc -> mapper.map(doc, TripDocumentViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("documents", documents);
            modelAndView.setViewName("documents/trip/all");
        } else {
            List<TripDocumentViewModel> documents = documentService.getAllTripDocs()
                    .stream()
                    .map(doc -> mapper.map(doc, TripDocumentViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("documents", documents);
            modelAndView.setViewName("documents/trip/all");
        }
    }
}

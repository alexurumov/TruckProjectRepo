package truckmanagementproject.web.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.web.models.managers.ManagerResponseModel;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ManagersApiController {

    private final ManagerService managerService;
    private final ModelMapper mapper;

    @Autowired
    public ManagersApiController(ManagerService managerService, ModelMapper mapper) {
        this.managerService = managerService;
        this.mapper = mapper;
    }

    @GetMapping("/api/managers")
    public List<ManagerResponseModel> getAllManagers() throws InterruptedException {
        Thread.sleep(5000);
        return managerService.getAllManagers()
                .stream()
                .map(man -> mapper.map(man, ManagerResponseModel.class))
                .collect(Collectors.toList());
    }
}

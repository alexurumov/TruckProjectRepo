package truckmanagementproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import truckmanagementproject.web.controllers.UploadController;

import java.io.File;

@SpringBootApplication
public class TruckmanagementprojectApplication {

    public static void main(String[] args) {
        new File(UploadController.uploadDirectory).mkdir();
        SpringApplication.run(TruckmanagementprojectApplication.class, args);

    }

}

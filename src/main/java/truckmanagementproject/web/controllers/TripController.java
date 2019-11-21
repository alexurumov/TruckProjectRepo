package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.web.models.AddDriverModel;
import truckmanagementproject.web.models.AddTripModel;

@Controller
public class TripController {

    @GetMapping("/trips/add")
    public String getTripAddForm() {
        return "add-trip";
    }

    @PostMapping("/trips/add")
    public String tripAdd(@ModelAttribute AddTripModel model) {
        //service -> add trip
        System.out.println();
        return "redirect:/trips/current";
    }

    @GetMapping("/trips/current")
    public String getAllCurrentTrips() {
        return "all-current-trips";
    }

    @GetMapping("/trips/finished")
    public String getAllFinishedTrips() {
        return "all-finished-trips";
    }
}

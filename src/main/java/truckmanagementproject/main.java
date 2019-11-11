package truckmanagementproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import truckmanagementproject.data.models.*;
import truckmanagementproject.data.repositories.DocumentRepository;
import truckmanagementproject.data.repositories.VehicleRepository;

import java.sql.Date;

@Component
public class main implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public main(VehicleRepository vehicleRepository, DocumentRepository documentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Truck truck = new Truck();
        truck.setRegNumber("E 0212 KT");

        this.vehicleRepository.save(truck);

        Trailer trailer = new Trailer();
        trailer.setRegNumber("E 1955 EE");

        this.vehicleRepository.save(trailer);

        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.setName("GO");
        vehicleDocument.setPicture("url...");
        vehicleDocument.setExpiryDate(Date.valueOf("2019-12-21"));
        vehicleDocument.setVehicle(trailer);

        this.documentRepository.save(vehicleDocument);

        VehicleDocument vehicleDocument2 = new VehicleDocument();
        vehicleDocument2.setName("Kasko");
        vehicleDocument2.setPicture("url...");
        vehicleDocument2.setExpiryDate(Date.valueOf("2019-11-26"));
        vehicleDocument2.setVehicle(truck);

        this.documentRepository.save(vehicleDocument2);

        CompanyDocument companyDocument = new CompanyDocument();
        companyDocument.setName("License");
        companyDocument.setPicture("url...");
        companyDocument.setExpiryDate(Date.valueOf("2020-06-12"));

        this.documentRepository.save(companyDocument);
    }
}

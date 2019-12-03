package truckmanagementproject.services;

import truckmanagementproject.services.models.documents.AddCompanyDocServiceModel;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;
import truckmanagementproject.services.models.documents.AddVehicleDocServiceModel;

public interface DocumentService {
    void addTripDocument(AddTripDocServiceModel model);

    void addDriverDocument(AddDriverDocServiceModel docServiceModel);

    void addVehicleDocument(AddVehicleDocServiceModel docServiceModel);

    void addCompanyDocument(AddCompanyDocServiceModel docServiceModel);
}

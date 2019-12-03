package truckmanagementproject.services;

import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;

public interface DocumentService {
    void addTripDocument(AddTripDocServiceModel model);

    void addDriverDocument(AddDriverDocServiceModel docServiceModel);
}

package truckmanagementproject.services.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import truckmanagementproject.data.models.milestones.Milestone;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.repositories.milestones.MilestoneRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.services.base.BaseServiceTest;
import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.services.milestones.MilestoneService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;
import truckmanagementproject.web.models.milestones.AddMilestoneModel;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MilestoneServiceTest extends BaseServiceTest {

    @MockBean
    MilestoneRepository milestoneRepository;

    @MockBean
    TripRepository tripRepository;

    @MockBean
    ValidationUtil validationUtil;

    @Autowired
    MilestoneService service;

    @Test
    public void addMilestone_withInvalidTrip_shouldThrowException() {
        AddMilestoneServiceModel model = new AddMilestoneServiceModel();

        String tripRef = "ref";
        Trip trip = null;

        Mockito.when(tripRepository.getByReference(tripRef))
                .thenReturn(trip);

        assertThrows(Exception.class,
                () -> service.addMilestone(model));
    }

    @Test
    public void addMilestone_withInvalidMilestone_shouldThrowException() {
        AddMilestoneServiceModel model = new AddMilestoneServiceModel();

        String tripRef = "ref";
        Trip trip = new Trip();

        Mockito.when(tripRepository.getByReference(tripRef))
                .thenReturn(trip);

        Milestone milestone = new Milestone();

        Mockito.when(validationUtil.isValid(milestone))
                .thenReturn(false);

        assertThrows(Exception.class,
                () -> service.addMilestone(model));
    }

    @Test
    public void updateMilestone_withInvalidMilestone_shouldThrowException() {
        String id = "id";
        Milestone milestone = null;
        Mockito.when(milestoneRepository.getById(id))
                .thenReturn(milestone);

        assertThrows(Exception.class,
                () -> service.updateMilestone(id));

    }

    @Test
    public void updateMilestone_withValidMilestone_shouldSetFinishedToTrue() throws Exception {
        String id = "id";
        Milestone milestone = new Milestone();
        Mockito.when(milestoneRepository.getById(id))
                .thenReturn(milestone);

        service.updateMilestone(id);

        assertTrue(milestone.getIsFinished());

    }

    @Test
    public void isMilestoneValid_whenNoName_shouldReturnFalse() {
        AddMilestoneModel model = new AddMilestoneModel();
        model.setName("");

        assertFalse(service.isMilestoneValid(model));
    }

    @Test
    public void isMilestoneValid_whenNoAddress_shouldReturnFalse() {
        AddMilestoneModel model = new AddMilestoneModel();
        model.setName("someName");
        model.setAddress("");

        assertFalse(service.isMilestoneValid(model));
    }

    @Test
    public void isMilestoneValid_whenNoDetails_shouldReturnFalse() {
        AddMilestoneModel model = new AddMilestoneModel();
        model.setName("someName");
        model.setAddress("someAddress");
        model.setDetails("");

        assertFalse(service.isMilestoneValid(model));
    }

    @Test
    public void isMilestoneValid_whenAllValid_shouldReturnTrue() {
        AddMilestoneModel model = new AddMilestoneModel();
        model.setName("someName");
        model.setAddress("someAddress");
        model.setDetails("someDetails");

        assertTrue(service.isMilestoneValid(model));
    }


}
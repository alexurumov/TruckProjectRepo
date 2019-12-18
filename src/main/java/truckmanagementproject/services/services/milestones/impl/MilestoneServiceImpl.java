package truckmanagementproject.services.services.milestones.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.milestones.Milestone;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.repositories.milestones.MilestoneRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.models.milestones.MilestoneServiceModel;
import truckmanagementproject.services.services.milestones.MilestoneService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.milestones.AddMilestoneModel;

import javax.transaction.Transactional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final TripRepository tripRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public MilestoneServiceImpl(MilestoneRepository milestoneRepository, TripRepository tripRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.milestoneRepository = milestoneRepository;
        this.tripRepository = tripRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addMilestone(AddMilestoneServiceModel collectionModel) throws Exception {
        Milestone collection = mapper.map(collectionModel, Milestone.class);
        Trip trip = tripRepository.getByReference(collectionModel.getTripReference());
        if (trip == null) {
            throw new Exception("Invalid Trip");
        }
        collection.setTrip(trip);

        if (validationUtil.isValid(collection)) {
            milestoneRepository.saveAndFlush(collection);
        } else throw new Exception("Invalid milestone!");
    }

    @Override
    @Transactional
    public void updateMilestone(String id) throws Exception {
        Milestone milestone = milestoneRepository.getById(id);
        if (milestone == null) {
            throw new Exception("Invalid Milestone");
        }
        milestone.setIsFinished(true);
        milestoneRepository.saveAndFlush(milestone);
    }

    @Override
    public MilestoneServiceModel getById(String id) {
        return mapper.map(milestoneRepository.getById(id), MilestoneServiceModel.class);
    }

    @Override
    public boolean isMilestoneValid(AddMilestoneModel addMilestoneModel) {
        return !addMilestoneModel.getName().trim().isEmpty() &&
                !addMilestoneModel.getAddress().trim().isEmpty() &&
                !addMilestoneModel.getDetails().trim().isEmpty();
    }
}

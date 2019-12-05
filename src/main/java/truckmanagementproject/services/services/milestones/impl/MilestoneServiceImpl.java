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

import javax.transaction.Transactional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final TripRepository tripRepository;
    private final ModelMapper mapper;

    @Autowired
    public MilestoneServiceImpl(MilestoneRepository milestoneRepository, TripRepository tripRepository, ModelMapper mapper) {
        this.milestoneRepository = milestoneRepository;
        this.tripRepository = tripRepository;
        this.mapper = mapper;
    }

    @Override
    public void addCollection(AddMilestoneServiceModel collectionModel) {
        Milestone collection = mapper.map(collectionModel, Milestone.class);
        Trip trip = tripRepository.getByReference(collectionModel.getTripReference());
        collection.setTrip(trip);
        milestoneRepository.saveAndFlush(collection);
    }

    @Override
    @Transactional
    public void updateMilestone(String id) {
        Milestone one = milestoneRepository.getById(id);
        one.setIsFinished(true);
        milestoneRepository.saveAndFlush(one);
    }

    @Override
    public MilestoneServiceModel getById(String id) {
        return mapper.map(milestoneRepository.getById(id), MilestoneServiceModel.class);
    }
}

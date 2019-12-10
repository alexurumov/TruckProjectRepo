package truckmanagementproject.data.models.milestones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;
import truckmanagementproject.data.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "milestones")
@Getter
@Setter
@NoArgsConstructor
public class Milestone extends BaseEntity {

    @Column(name = "milestone_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MilestoneType milestoneType;

    @Column(name = "location_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished = false;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

}

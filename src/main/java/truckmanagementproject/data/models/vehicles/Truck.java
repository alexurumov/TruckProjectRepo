package truckmanagementproject.data.models.vehicles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Truck")
@Getter
@Setter
@NoArgsConstructor
public class Truck extends Vehicle {
    //TODO implement more properties if needed
}

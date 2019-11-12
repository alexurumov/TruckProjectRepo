package truckmanagementproject.data.models.vehicles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Trailer")
@Getter
@Setter
@NoArgsConstructor
public class Trailer extends Vehicle {
    //TODO implement more properties if needed
}

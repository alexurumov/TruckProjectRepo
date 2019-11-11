package truckmanagementproject.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "Company")
@Getter
@Setter
@NoArgsConstructor
public class CompanyDocument extends Document {
    public CompanyDocument(String type) {
        super(type);
    }
}

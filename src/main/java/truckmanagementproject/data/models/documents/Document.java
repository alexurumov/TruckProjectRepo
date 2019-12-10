package truckmanagementproject.data.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truckmanagementproject.data.models.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Document extends BaseEntity {

    @Column(name = "picture", nullable = false)
    @NotEmpty
    private String picture;

}

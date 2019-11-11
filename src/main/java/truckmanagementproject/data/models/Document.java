package truckmanagementproject.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
@NoArgsConstructor
public abstract class Document extends BaseEntity {

    @Column(updatable = false, insertable = false)
    private String type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "picture")
    private String picture;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    protected Document(String type) {
        this.type = type;
    }
}

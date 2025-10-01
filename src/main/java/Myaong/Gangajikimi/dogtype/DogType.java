package Myaong.Gangajikimi.dogtype;

import Myaong.Gangajikimi.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class DogType extends BaseEntity {

    @Column(nullable = false, unique = true)
    String type;

}

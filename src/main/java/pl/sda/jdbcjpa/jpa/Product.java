package pl.sda.jdbcjpa.jpa;

import lombok.Getter;
import lombok.Setter;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("P")
@DiscriminatorColumn(name="Type",discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
public abstract class Product extends BaseEntity {

    private String productName;

}

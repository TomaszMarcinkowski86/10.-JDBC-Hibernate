package pl.sda.jdbcjpa.jpa;


import lombok.Getter;
import lombok.Setter;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
@Setter
@Getter
@Entity
public class Cart extends BaseEntity {


    @OneToOne
    private Customer customer; // uzytw w mappedby w Custamerze

}

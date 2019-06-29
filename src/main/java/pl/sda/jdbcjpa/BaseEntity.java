package pl.sda.jdbcjpa;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


// dziedziczymy te pola
@MappedSuperclass  // ta adnotacja pozwala używać Encji tych pól tutaj zadeklarowanych
@Getter
@ToString
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // duży int musi być
}

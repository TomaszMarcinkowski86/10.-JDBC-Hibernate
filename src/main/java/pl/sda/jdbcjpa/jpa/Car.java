package pl.sda.jdbcjpa.jpa;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

    @Entity
    public class Car {

        @Id
        @GeneratedValue
        private Integer id;
    }


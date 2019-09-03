package pl.sda.jdbcjpa.jpa;


import lombok.*;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orderHeader")
public class OrderLine extends BaseEntity {

    @ManyToOne
    private Order orderHeader; // nie używamy słowa order jako nazwy bo to słowo jest Kluczowym w SQL

    private String productName;

    private BigDecimal cost;



}

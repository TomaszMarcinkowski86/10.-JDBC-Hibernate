package pl.sda.jdbcjpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor //bezpzraetrowy konstruktor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // duży int musi być
 //   private Integer value;
    private BigDecimal totalCost;
    private String customerName;

    @ManyToOne
    private Customer customer;

}

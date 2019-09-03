package pl.sda.jdbcjpa.jpa;

import lombok.*;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor //bezpzraetrowy konstruktor
@Table(name = "Orders")
@ToString(exclude = "customer")
public class Order extends BaseEntity {

 //   private Integer value;
    private BigDecimal totalCost;
    private String customerName;

    @OneToMany (mappedBy = "orderHeader" , cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines;

    @ManyToOne
    private Customer customer;

}

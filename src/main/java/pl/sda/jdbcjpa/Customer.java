package pl.sda.jdbcjpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter //opcjonalne hibernate poradzi sobie bez getterów i setterów
@Setter
@Entity // to oznacza że to jest encją i możemy to zmapować na obiekty bazodanowe
@Table(name ="Customers")
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    private String pesel;

    @ElementCollection
    private Set<String> nickname=new HashSet<String>();


    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER) // fetchType EAGER oznacza że jak bedziemy szukać cusomera to zamówienia też będą zaciągane
    private List<Order> orders;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // zapisujemy wartość słowną enuma, bo domyślnie zrobiłby 0,1,itd i jak ktoś b dodał w enumie dodatkowy wpis to by była lipa
    private CustomerStatus status;

    @OneToOne(mappedBy = "customer")
    private Cart cart;
    @Transient // to nie będzie zapisywać
    private String string;


}//POJO plain old java object

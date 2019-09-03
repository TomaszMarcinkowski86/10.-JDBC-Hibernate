package pl.sda.jdbcjpa.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter //opcjonalne hibernate poradzi sobie bez getterów i setterów
@Setter
@Entity // to oznacza że to jest encją i możemy to zmapować na obiekty bazodanowe
@Table(name ="Customers")
@ToString (exclude = "transientField")
@NamedQueries(
        {
                @NamedQuery(
                       name = "findByFirstName", query = "select c from Customer c where c.firstName= :fn"
                )
        }
)
public class Customer extends BaseEntity {

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

    @Embedded // element zapisany w tej samej tabeli w sql ale znajdujący się w innym obiekcie w javie
    private Address address;

    @Enumerated(EnumType.STRING) // zapisujemy wartość słowną enuma, bo domyślnie zrobiłby 0,1,itd i jak ktoś b dodał w enumie dodatkowy wpis to by była lipa
    private CustomerStatus status;

    @OneToOne(mappedBy = "customer",  // wskazanie właściciela relacji (encja cart bedzie wlascicielem)
            cascade = CascadeType.PERSIST) // operacja kaskadowa zabierze Carta wraz z Customerem do Persistent Contekstu
    private Cart cart;
    @Transient // to nie będzie zapisywać w bazie danych
    private String string;


}//POJO plain old java object

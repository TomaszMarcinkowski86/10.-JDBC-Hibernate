package pl.sda.jdbcjpa.jpa2;

import lombok.*;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(callSuper = true) // będzie siegał do nadklasy
@Builder
public class Post extends BaseEntity {

    private  String title;
    private  String body;

//    @Singular // bedzie można w mainie w builderze pojedyńcze cmmenty nie zadziałało
    @OneToMany
    private List<Comment> comments;

}

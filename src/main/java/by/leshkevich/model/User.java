package by.leshkevich.model;

import lombok.*;


/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is the entity to hold the User object
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private int id;
    private String login;
    private String lastname;
    private String firstname;
}

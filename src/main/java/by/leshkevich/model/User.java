package by.leshkevich.model;

import lombok.*;


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

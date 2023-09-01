package by.leshkevich.model;

import by.leshkevich.utils.DateManager;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Account {
    private int id;
    private String number;
    private User User;
    private Bank Bank;
    private double balance;
    private DateManager openData;

}

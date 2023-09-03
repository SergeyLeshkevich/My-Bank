package by.leshkevich.model;

import by.leshkevich.services.AccountService;
import by.leshkevich.services.BankService;
import by.leshkevich.utils.enums.TypeOperation;
import by.leshkevich.utils.exceptions.AccountException;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.BalanceException;
import lombok.*;

import java.util.Map;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is the entity to hold the Bank object
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Bank {
    int id;
    String name;
}

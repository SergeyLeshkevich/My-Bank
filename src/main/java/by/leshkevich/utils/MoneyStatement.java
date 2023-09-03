package by.leshkevich.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is the entity to hold the bank statement object
 */
@Data
@Builder
public class MoneyStatement {
    String bankName;
    String userLastName;
    String userFirstName;
    String numberAccount;
    DateManager openAccountDate;
    double balance;
    double sumPositive;
    double sumNegative;
    private DateManager dateFor;
    private DateManager dateBefore;
}

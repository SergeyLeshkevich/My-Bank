package by.leshkevich.model;

import by.leshkevich.utils.DateManager;
import lombok.*;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is the entity to hold the Transaction object
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Transaction {
   private int id;
   private DateManager dataOperation;
   private Account senderAccount;
   private Account beneficiaryAccount;
   private Bank senderBank;
   private Bank beneficiaryBank;
   private String typeOperation;
   private String status;
   private double amount;
}

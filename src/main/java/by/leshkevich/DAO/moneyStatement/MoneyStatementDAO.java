package by.leshkevich.DAO.moneyStatement;



import by.leshkevich.utils.MoneyStatement;
import by.leshkevich.utils.exceptions.DAOException;

import java.time.LocalDateTime;

/**
 * @author S.Leshkevich
 * @version 1.0
 * an interface that provides methods for working with MoneyStatement's DAO  for implementation by the class implementing it
 * */
public interface MoneyStatementDAO {
    /**
     * get method MoneyStatement object
     */
    MoneyStatement getMoneyStatement(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore)
            throws DAOException;
}

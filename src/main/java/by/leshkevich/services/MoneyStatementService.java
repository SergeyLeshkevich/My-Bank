package by.leshkevich.services;

import by.leshkevich.DAO.moneyStatement.MoneyStatementDAO;
import by.leshkevich.DAO.moneyStatement.MoneyStatementPostgresDAOImpl;
import by.leshkevich.utils.MoneyStatement;
import by.leshkevich.utils.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains the main logic for working with the entity MoneyStatement
 */
public class MoneyStatementService {
    private static final Logger logger = LogManager.getLogger(MoneyStatementService.class);

    private final MoneyStatementDAO MONEY_STATEMENT = new MoneyStatementPostgresDAOImpl();

    /**
     * the method is designed to get the formed MoneyStatement object from the DAO layer
     * @return  returns null if the object could not be formed
     */
    public MoneyStatement getMoneyStatement(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore) {
        logger.info("Getting an extract {}{}{}", numberSenderAccount, dateFor, dateBefore);
        MoneyStatement moneyStatement;
        try {
            moneyStatement = MONEY_STATEMENT.getMoneyStatement(numberSenderAccount, dateFor, dateBefore);
            logger.info("Extract {}", moneyStatement);
            return moneyStatement;

        } catch (DAOException e) {
            logger.info("Extract {}", "empty");
            return null;
        }
    }
}

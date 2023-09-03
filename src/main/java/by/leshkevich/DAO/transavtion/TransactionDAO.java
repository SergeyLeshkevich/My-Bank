package by.leshkevich.DAO.transavtion;

import by.leshkevich.model.Account;
import by.leshkevich.utils.MoneyStatement;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author S.Leshkevich
 * @version 1.0
 * an interface that provides methods for working with Transaction's DAO  for implementation by the class implementing it
 * */
public interface TransactionDAO {
    /**
     * Transaction object save method
     */
    Transaction save(Transaction transaction)throws DAOException;

    /**
     * get method Transaction object
     */
    Transaction get(int idTransaction)throws DAOException;

    /**
     * Transaction object deletion method
     */
    boolean delete(int transactionId) throws DAOException;

    /**
     * method for getting a list of all Transaction objects
     */
    List<Transaction> getListForPeriod(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore) throws DAOException;

    /**
     * method for updating the status field of the Transaction object
     */
    boolean updateStatus(Transaction transaction) throws DAOException;

    /**
     * method for updating the status and amount fields of the Transaction object
     */
    boolean updateStatusAndAmount(Transaction transaction) throws DAOException;

}

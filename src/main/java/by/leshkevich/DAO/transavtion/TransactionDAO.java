package by.leshkevich.DAO.transavtion;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> getAllTransactionsBySenderAccount(String numberSenderAccount) throws DAOException;
    Transaction save(Transaction transaction)throws DAOException;
    Transaction get(int idTransaction)throws DAOException;
    boolean delete(int transactionId) throws DAOException;
    List<Transaction> getListForPeriod(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore) throws DAOException;
    boolean updateStatus(Transaction transaction) throws DAOException;
}

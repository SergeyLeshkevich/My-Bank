package by.leshkevich.services;

import by.leshkevich.DAO.transavtion.TransactionDAO;
import by.leshkevich.DAO.transavtion.TransactionPostgresDAOImpl;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Transaction;
import by.leshkevich.utils.file.FileHandler;
import by.leshkevich.utils.file.StringsForFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains the main logic for working with the entity Transaction
 */
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);
    private final TransactionDAO TRANSACTION_DAO = new TransactionPostgresDAOImpl();

    /**
     * the method is for updating the status field in the transaction object
     *
     * @param transaction required fields in the Transaction object must be id and typeOperation
     * @return returns true on success
     */
    public boolean updateStatus(Transaction transaction) {
        logger.info("Обновление статуса транзакции: {}", transaction);
        boolean status;
        try {
            status = TRANSACTION_DAO.updateStatus(transaction);
            return status;
        } catch (DAOException e) {
            logger.info("Статус: {}", false);
            return false;
        }
    }

    /**
     * the method is for updating the status and amount fields in the Transaction object
     *
     * @param transaction required fields in the transaction object must be all fields except id
     * @return returns true on success
     */
    public boolean updateStatusAndAmount(Transaction transaction) {
        logger.info("Обновление статуса и суммы транзакции: {}", transaction);
        boolean status;
        try {
            status = TRANSACTION_DAO.updateStatusAndAmount(transaction);
            logger.info("Статус: {}", status);
            return status;
        } catch (DAOException e) {
            logger.info("Статус: {}", false);
            return false;
        }
    }

    /**
     * the method is for updating the status field in the Transaction object
     *
     * @param transaction required fields in the Transaction object must be id ,status and amount
     */
    public void createCheck(Transaction transaction) {
        logger.info("создание чека: {}", transaction);
        String check = StringsForFile.StringForCheck(transaction);
        FileHandler.writeFileTXT(AppConstant.PATH_CHECK +
                "check №" +
                transaction.getId() + ".txt", check);
        FileHandler.writeFilePDF(AppConstant.PATH_CHECK +
                "check №" +
                transaction.getId() + ".pdf", check);
    }

    /**
     * the method is intended to be stored in the Transaction object
     *
     * @param transaction required fields in the transaction object must be all fields except id
     * @return returns a Transaction object with an id assigned to id
     */
    public Transaction saveTransaction(Transaction transaction) {
        logger.info("Сохранение транзакции: {}", transaction);
        Transaction transactionBD;
        try {
            transactionBD = TRANSACTION_DAO.save(transaction);
        } catch (DAOException e) {
            transactionBD = null;
        }
        logger.info("Транзакция сохранена: {}", transaction);
        return transactionBD;
    }

    /**
     * the method is designed to get the Transaction object from the dao layer
     *
     * @return returns a Transaction object
     */
    public Transaction getTransaction(int id) {
        logger.info("получение транзакции {}", id);
        Transaction transaction;
        try {
            transaction = TRANSACTION_DAO.get(id);
        } catch (DAOException e) {
            //Log
            transaction = null;
        }
        logger.info("транзакция {}", transaction);
        return transaction;
    }

    /**
     * the method is for deleting the Transaction object
     *
     * @return returns true on success
     */
    public boolean delete(int id) {
        logger.info("удаление транзакции {}", id);

        try {
            boolean isDel = TRANSACTION_DAO.delete(id);
            logger.info("удаление транзакции {}", isDel);
            return isDel;
        } catch (DAOException e) {
            logger.info("удаление транзакции {}", false);
            return false;
        }
    }

    /**
     * the method is designed to receive all transactions found by the given parameters
     *
     * @param accountNumber number account
     * @param dateFor       search start date
     * @param dateBefore    search end date
     * @return returns an arraylist with Transaction objects. If nothing is found, an empty list is returned
     */
    public List<Transaction> getAllTransactionsListForPeriod(String accountNumber, LocalDateTime dateFor, LocalDateTime dateBefore) {
        try {
            logger.info("Получение списка транзакций за период: {},{},{}", accountNumber, dateFor, dateBefore);
            List<Transaction> list = TRANSACTION_DAO.getListForPeriod(accountNumber, dateFor, dateBefore);
            logger.info("Список за период: {}", list);
            return list;
        } catch (DAOException e) {
            logger.info("Список за период: {}", "пусто");
            return new ArrayList<>();
        }
    }

    /**
     * the method is designed to receive a penalty for a transaction to the account of another bank
     */
    public double getFine(double sum, int percent) {
        logger.info("Рассчет суммы пени: {},{}", sum, percent);
        double fine = (Math.abs(sum) * percent) / 100;
        logger.info("Пеня: {}", fine);
        return fine;
    }

}

package by.leshkevich.services;

import by.leshkevich.DAO.transavtion.TransactionDAO;
import by.leshkevich.DAO.transavtion.TransactionPostgresDAOImpl;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Transaction;
import by.leshkevich.utils.file.FileHandler;
import by.leshkevich.utils.file.StringsForFile;

public class TransactionService {
    private final TransactionDAO TRANSACTION_DAO = new TransactionPostgresDAOImpl();


    public boolean updateStatus(Transaction transaction) {

        try {
            return TRANSACTION_DAO.updateStatus(transaction);
        } catch (DAOException e) {
            return false;
        }
    }

    public void createCheck(Transaction transaction) {
        String check = StringsForFile.StringForCheck(transaction);
        FileHandler.writeFileTXT(AppConstant.PATH_CHECK +
                "check №" +
                transaction.getId() + ".txt", check);
        FileHandler.writeFilePDF(AppConstant.PATH_CHECK +
                "check №" +
                transaction.getId() + ".pdf", check);
    }

    public Transaction saveTransaction(Transaction transaction) {
        Transaction transactionBD;
        try {
            transactionBD = TRANSACTION_DAO.save(transaction);
        } catch (DAOException e) {
            transactionBD = null;
        }
        return transactionBD;
    }

    public Transaction getTransaction(int id) {
        Transaction transaction;
        try {
            transaction = TRANSACTION_DAO.get(id);
        } catch (DAOException e) {
            //Log
            transaction = null;
        }
        return transaction;
    }

    public boolean delete(int id) {
        try {
            return TRANSACTION_DAO.delete(id);
        } catch (DAOException e) {
            return false;
        }
    }

    public double getFine(double sum, int percent) {
        return (Math.abs(sum) * percent) / 100;
    }

}

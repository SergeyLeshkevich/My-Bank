package by.leshkevich.DAO.transavtion;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.db.ConnectionManager;
import by.leshkevich.utils.db.SQLRequest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionPostgresDAOImpl implements TransactionDAO {

    @Override
    public List<Transaction> getAllTransactionsBySenderAccount(String numberSenderAccount) throws DAOException {
        List<Transaction> list = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_TRANSACTION_BY_NUMBER_SENDER_ACCOUNT);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_BENEFICIARY_USER)) {
            pst1.setString(1, numberSenderAccount);

            list = transactionsListFabric(pst1, pst2);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }


    @Override
    public Transaction save(Transaction transaction) throws DAOException {
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.INSERT_TRANSACTION_TO_DB, Statement.RETURN_GENERATED_KEYS)) {

            pst.setTimestamp(1, transaction.getDataOperation().getDateForDB());
            pst.setString(2, transaction.getSenderAccount().getNumber());
            pst.setString(3, transaction.getBeneficiaryAccount().getNumber());
            pst.setString(4, transaction.getTypeOperation());
            pst.setDouble(5, transaction.getAmount());
            pst.setDouble(6, transaction.getSenderBank().getId());
            pst.setDouble(7, transaction.getBeneficiaryBank().getId());
            pst.setString(8, transaction.getStatus());

            if (pst.executeUpdate() > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    transaction.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return transaction;
    }

    @Override
    public Transaction get(int idTransaction) throws DAOException {
        Transaction transaction = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_TRANSACTION_BY_ID);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_BENEFICIARY_USER)) {
            pst1.setInt(1, idTransaction);

            List<Transaction> list = transactionsListFabric(pst1, pst2);
            if (!list.isEmpty()) transaction = list.get(0);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return transaction;
    }

    @Override
    public boolean delete(int transactionId) throws DAOException {
        boolean isDel = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.DELETE_TRANSACTION_BY_ID)) {

            pst.setInt(1, transactionId);
            if (pst.executeUpdate() > 0) {
                isDel = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isDel;
    }


    @Override
    public List<Transaction> getListForPeriod(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore) throws DAOException {
        List<Transaction> list = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_TRANSACTION_BY_PERIOD);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_BENEFICIARY_USER)) {
            pst1.setTimestamp(1, Timestamp.valueOf(dateFor));
            pst1.setTimestamp(2, Timestamp.valueOf(dateBefore));
            pst1.setString(3, numberSenderAccount);

            list = transactionsListFabric(pst1, pst2);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    private List<Transaction> transactionsListFabric(PreparedStatement pst1, PreparedStatement pst2) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        Transaction transaction = null;

        ResultSet rs = pst1.executeQuery();
        while (rs.next()) {
            int idDB = rs.getInt(1);
            Timestamp dateBD = rs.getTimestamp(2);

            int idSAccountDB = rs.getInt(3);
            String numberSAccountBD = rs.getString(4);
            String numberBAccountBD = rs.getString(5);

            int idSAUserBD = rs.getInt(6);
            String sAUserLoginBD = rs.getString(7);
            String sAUserLastNameBD = rs.getString(8);
            String sAUserFirstNameBD = rs.getString(9);

            int idSABankBD = rs.getInt(10);
            String sANameBankBD = rs.getString(11);

            double sBalance = rs.getDouble(12);
            Timestamp sOpenDateBD = rs.getTimestamp(13);

            int idBBankDB = rs.getInt(14);
            String sBNameBankBD = rs.getString(15);

            String typeOperationDB = rs.getString(16);
            double amountDB = rs.getDouble(17);

            String status = rs.getString(18);

            User bUserBD = null;
            pst2.setString(1, numberBAccountBD);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                String bUserLastNameBD = rs2.getString(1);
                String bUserFirstNameBD = rs2.getString(2);
                bUserBD = User.builder()
                        .lastname(bUserLastNameBD)
                        .firstname(bUserFirstNameBD).build();
            }

            User sAUser = new User(idSAUserBD, sAUserLoginBD, sAUserLastNameBD, sAUserFirstNameBD);
            Bank sBank = new Bank(idSABankBD, sANameBankBD);
            Bank bBank = new Bank(idBBankDB, sBNameBankBD);
            Account accountS = new Account(idSAccountDB,
                    numberSAccountBD,
                    sAUser,
                    sBank,
                    sBalance,
                    new DateManager(sOpenDateBD));
            Account accountB = Account.builder()
                    .number(numberBAccountBD)
                    .User(bUserBD)
                    .Bank(bBank).build();

            transaction = new Transaction(
                    idDB,
                    new DateManager(dateBD),
                    accountS, accountB,
                    sBank,
                    bBank,
                    typeOperationDB, status,
                    amountDB);
            list.add(transaction);
        }
        return list;

    }
    @Override
    public boolean updateStatus(Transaction transaction) throws DAOException {
        boolean isUpdate = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.UPDATE_TRANSACTION_STATUS)) {

            pst1.setString(1, transaction.getStatus());
            pst1.setDouble(2, transaction.getId());

            int res = pst1.executeUpdate();

            if (res > 0) {
                isUpdate = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isUpdate;
    }
}

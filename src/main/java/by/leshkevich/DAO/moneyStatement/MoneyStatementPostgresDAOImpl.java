package by.leshkevich.DAO.moneyStatement;

import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.MoneyStatement;
import by.leshkevich.utils.db.ConnectionManager;
import by.leshkevich.utils.db.SQLRequest;
import by.leshkevich.utils.exceptions.DAOException;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class implements the MoneyStatementDAO interface and implements its methods for working with the database.
 */
public class MoneyStatementPostgresDAOImpl implements MoneyStatementDAO {
    /**
     * get method MoneyStatement object from database PostgresQL
     */
    @Override
    public MoneyStatement getMoneyStatement(String numberSenderAccount, LocalDateTime dateFor, LocalDateTime dateBefore)
            throws DAOException {
        MoneyStatement moneyStatement = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.SELECT_MONEY_STATEMENT)) {
            pst.setString(1, numberSenderAccount);
            pst.setString(2, numberSenderAccount);
            pst.setTimestamp(3, Timestamp.valueOf(dateFor));
            pst.setTimestamp(4, Timestamp.valueOf(dateBefore));
            pst.setString(5, numberSenderAccount);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String sBNameBankBD = rs.getString(1);
                String sAUserLastNameBD = rs.getString(2);
                String sAUserFirstNameBD = rs.getString(3);
                String numberSAccountBD = rs.getString(4);
                Timestamp openSAccountDateBD = rs.getTimestamp(5);
                double balanceDB = rs.getDouble(6);
                double sumPositive = rs.getDouble(7);
                double sumNegative = rs.getDouble(8);
                moneyStatement = MoneyStatement.builder()
                        .bankName(sBNameBankBD)
                        .userLastName(sAUserLastNameBD)
                        .userFirstName(sAUserFirstNameBD)
                        .numberAccount(numberSAccountBD)
                        .openAccountDate(new DateManager(openSAccountDateBD))
                        .balance(balanceDB)
                        .sumPositive(sumPositive)
                        .sumNegative(sumNegative)
                        .dateFor(new DateManager(Timestamp.valueOf(dateFor)))
                        .dateBefore(new DateManager(Timestamp.valueOf(dateBefore))).build();
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return moneyStatement;
    }
}

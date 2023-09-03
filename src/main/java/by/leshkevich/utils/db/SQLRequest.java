package by.leshkevich.utils.db;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains static final fields for working with SQL queries and database
 */
public class SQLRequest {
    public static final String ID_COLUMN = "id";
    public static final String LOGIN_COLUMN = "login";
    public static final String FIRSTNAME_COLUMN = "firstname";
    public static final String LASTNAME_COLUMN = "lastname";
    public static final String PASSWORD_COLUMN = "password";
    public static final String NUMBER_COLUMN = "number";
    public static final String ID_USER_COLUMN = "id_user";
    public static final String ID_BANK_COLUMN = "id_bank";
    public static final String BALANCE_COLUMN = "balance";
    public static final String DATE_COLUMN = "date";
    public static final String NUMBER_SENDER_ACCOUNT_COLUMN = "number_sender_account";
    public static final String NUMBER_BENEFICIARY_ACCOUNT_COLUMN = "number_beneficiary_account";
    public static final String TYPE_OPERATION_COLUMN = "type_operation";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String NAME_COLUMN = "name";


    public static final String INSERT_USER_TO_DB =
            "insert into " +
                    "users(" +
                    "login," +
                    "firstname," +
                    "lastname) " +
                    "values (?,?,?)";
    public static final String INSERT_ACCOUNT_TO_DB =
            "insert into " +
                    "accounts(" +
                    "number," +
                    "id_user," +
                    "id_bank," +
                    "balance," +
                    "date) " +
                    "values (?,?,?,?,?)";
    public static final String INSERT_TRANSACTION_TO_DB =
            "insert into " +
                    "transactions(" +
                    "date," +
                    "number_sender_account," +
                    "number_beneficiary_account," +
                    "type_operation," +
                    "amount," +
                    "id_sender_bank," +
                    "id_beneficiary_bank," +
                    "status) " +
                    "values (?,?,?,?,?,?,?,?)";

    public static final String SELECT_USER_BY_LOGIN =
            "select " +
                    "us.id," +
                    "us.login," +
                    "us.lastname," +
                    "us.firstname " +
                    "from users " +
                    "as us " +
                    "where " +
                    "us.login=?";

    public static final String SELECT_PASSWORD_BY_ID_USER =
            "select " +
                    "ps.password " +
                    "from passwords " +
                    "as ps " +
                    "where " +
                    "ps.id_user=?";

    public static final String SELECT_ACCOUNT_BY_NUMBER =
            "select " +
                    "ac.id, " +
                    "ac.number, " +
                    "ac.id_user, " +
                    "ac.id_bank, " +
                    "ac.balance,  " +
                    "ac.date " +
                    "from accounts " +
                    "as ac " +
                    "where number=?";

    public static final String SELECT_ACCOUNTS_LIST_BY_ID_USER =
            "select " +
                    "ac.id, " +
                    "ac.number, " +
                    "ac.id_user, " +
                    "ac.id_bank, " +
                    "ac.balance,  " +
                    "ac.date " +
                    "from accounts " +
                    "as ac " +
                    "where id_user=?";

    public static final String SELECT_TRANSACTION_BY_PERIOD =
            "select " +
                    "tr.id, " +
                    "tr.date, " +
                    "sa.id, " +
                    "sa.number, " +
                    "tr.number_beneficiary_account, " +
                    "asu.id, " +
                    "asu.login, " +
                    "asu.lastname, " +
                    "asu.firstname, " +
                    "asb.id, " +
                    "asb.name, " +
                    "sa.balance,  " +
                    "sa.date,  " +
                    "bb.id,  " +
                    "bb.name, " +
                    "tr.type_operation, " +
                    "tr.amount," +
                    "tr.status " +
                    "from transactions " +
                    "as tr  " +
                    "inner join accounts as sa on sa.number = number_sender_account " +
                    "inner join users as asu on asu.id = id_user inner " +
                    "join banks as asb on asb.id = id_bank " +
                    "inner join banks as bb on bb.id = id_beneficiary_bank  " +
                    "where tr.date>? " +
                    "and " +
                    "tr.date<? " +
                    "and " +
                    "number_sender_account=?";

    public static final String DELETE_ACCOUNT_BY_ID = "delete from accounts where id=?";
    public static final String DELETE_TRANSACTION_BY_ID = "delete from transactions where id=?";

    public static final String UPDATE_ACCOUNT_BALANCE_BY_NUMBER =
            "update " +
                    "accounts " +
                    "set " +
                    "balance=(balance+(?)) " +
                    "where number=?";


    public static final String INSERT_PASSWORD_FOR_USER =
            "insert into " +
                    "passwords(" +
                    "id_user," +
                    "password) " +
                    "values (?,?)";
    public static final String SELECT_ID_USER_BY_LOGIN =
            "select " +
                    "us.id " +
                    "from users " +
                    "as us " +
                    "where " +
                    "us.login=?";
    public static final String SELECT_USER_BY_ID =
            "select " +
                    "* " +
                    "from users " +
                    "where id=?";
    public static final String SELECT_BANK_BY_ID =
            "select * from banks where id=?";
    public static final String SELECT_BENEFICIARY_USER =
            "select " +
                    "u.lastname, " +
                    "u.firstname " +
                    "from users " +
                    "as u " +
                    "inner join accounts " +
                    "on u.id = id_user and number=?";
    public static final String SELECT_TRANSACTION_BY_ID =
            "select " +
                    "tr.id, " +
                    "tr.date, " +
                    "sa.id, " +
                    "sa.number, " +
                    "tr.number_beneficiary_account," +
                    "asu.id, " +
                    "asu.login," +
                    "asu.lastname," +
                    "asu.firstname, " +
                    "asb.id," +
                    "asb.name," +
                    "sa.balance, " +
                    "sa.date," +
                    "bb.id, " +
                    "bb.name, " +
                    "tr.type_operation," +
                    " tr.amount, " +
                    "tr.status " +
                    "from transactions as tr  " +
                    "inner join accounts as sa on sa.number = number_sender_account  " +
                    "inner join users as asu on asu.id = id_user  " +
                    "inner join banks as asb on asb.id = id_bank  " +
                    "inner join banks as bb on bb.id = id_beneficiary_bank where tr.id = ?";
    public static final String UPDATE_TRANSACTION_STATUS =
            "update " +
                    "transactions " +
                    "set status=? " +
                    "where id=?";

    public static final String UPDATE_TRANSACTION_STATUS_END_AMOUNT =
            "update " +
                    "transactions " +
                    "set status=?, " +
                    "amount=? " +
                    "where id=?";
    public static final String SELECT_ALL_ACCOUNTS_LIST =
            "select " +
                    "ac.id, " +
                    "ac.number, " +
                    "ac.id_user, " +
                    "ac.id_bank, " +
                    "ac.balance,  " +
                    "ac.date " +
                    "from accounts " +
                    "as ac";
    public static final String DELETE_BANK_BY_ID = "delete from banks where id=?";
    public static final String INSERT_BANK_TO_DB =
            "insert into " +
                    "banks(" +
                    "name) " +
                    "values (?)";
    public static final String UPDATE_BANK_NAME_BY_NUMBER =
            "update " +
                    "banks " +
                    "set name=? " +
                    "where id=?";
    public static final String DELETE_USER_BY_ID = "with del1 as (delete  from users where id=?)" +
            " delete from passwords where id_user=?";
    public static final String UPDATE_USER_LASTNAME_BY_LOGIN =
            "update " +
                    "users " +
                    "set lastname=? " +
                    "where login=?";
    ;

    public static final String SELECT_MONEY_STATEMENT =
            "select " +
                    "asb.name, " +
                    "asu.lastname, " +
                    "asu.firstname, " +
                    "sa.number, " +
                    "sa.date, " +
                    "sa.balance, " +
                    "(SELECT " +
                    "SUM(" +
                    "CASE WHEN " +
                    "tr.amount > 0 " +
                    "AND tr.type_operation IN ('REFILL', 'ACCRUAL_OF_INTEREST') " +
                    "THEN tr.amount " +
                    "ELSE 0 END) " +
                    "FROM transactions as tr " +
                    "WHERE tr.status <> 'REJECTED' " +
                    "AND tr.number_sender_account = ?) " +
                    "as sum_positive, " +
                    "(SELECT " +
                    "SUM(" +
                    "CASE WHEN " +
                    "tr.amount < 0 " +
                    "AND tr.type_operation IN ('TRANSLATION', 'WITHDRAWAL') " +
                    "THEN tr.amount " +
                    "ELSE 0 END) " +
                    "FROM transactions as tr " +
                    "WHERE tr.status <> 'REJECTED' " +
                    "AND tr.number_sender_account = ?)" +
                    "as sum_negative " +
                    "from transactions as tr " +
                    "inner join accounts as sa on sa.number = number_sender_account " +
                    "inner join users as asu on asu.id = id_user inner join banks as asb on asb.id = id_bank " +
                    "inner join banks as bb on bb.id = id_beneficiary_bank " +
                    "where " +
                    "tr.date > ? " +
                    "and " +
                    "tr.date < ? " +
                    "and number_sender_account = ?";
}

package by.leshkevich.utils.db;

import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.YmlManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is for getting database connection object
 */
public class ConnectionManager {

    private static Connection cn;
private static YmlManager ymlManager=new YmlManager();


    /**
     * This code executes the JDBC driver load operation at runtime
     */
    static {

        try {
            Class.forName(ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.DRIVER_VALUE));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * the method is to get the connection object
     * @return Connection
     */
    public static Connection getConnection() throws SQLException {
        if (cn == null || cn.isClosed()) {
            cn = DriverManager.getConnection(ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.URL_PROPERTY),
                   ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.USER_VALUE),
                   ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.PASSWORD_VALUE));
        }
        return cn;
    }


}

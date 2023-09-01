package by.leshkevich.utils.db;

import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.YmlManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection cn;
private static YmlManager ymlManager=new YmlManager();


    static {

        try {
            Class.forName(ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.DRIVER_VALUE));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (cn == null || cn.isClosed()) {
            cn = DriverManager.getConnection(ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.URL_PROPERTY),
                   ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.USER_VALUE),
                   ymlManager.getValue(AppConstant.JDBC_YAMl,AppConstant.PASSWORD_VALUE));
        }
        return cn;
    }


}

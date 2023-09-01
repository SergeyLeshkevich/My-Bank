package by.leshkevich.DAO.user;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.User;

public interface UserDAO {
    User getUser(String login) throws DAOException;
    String getPassword(String login) throws DAOException;
    int save(User user, String password)throws DAOException;
    boolean removeUser(int idUser) throws DAOException;
    boolean updateLastname(User user) throws DAOException;
}

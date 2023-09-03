package by.leshkevich.utils.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.leshkevich.utils.constants.AppConstant;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is for encrypting and verifying against the user's encrypted password value.
 * It is based on the bcrypt key generation hash function.
 */
public class PasswordHashing {

    /**
     * password encryption method
     */
    public static String scryptPasswordHashing(String password) {
        return BCrypt.withDefaults().hashToString(AppConstant.PASSWORD_COST, password.toCharArray());
    }

    /**
     * method of checking against the encrypted value of the user's password
     */
    public static boolean verificationPasswordHashing(String password, String bcryptHashString) {
        if (bcryptHashString.equals("")) return false;
        return BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified;
    }
}

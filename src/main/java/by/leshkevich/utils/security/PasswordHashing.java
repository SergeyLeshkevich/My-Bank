package by.leshkevich.utils.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.leshkevich.utils.constants.AppConstant;

public class PasswordHashing {
    public static String scryptPasswordHashing(String password) {
        return BCrypt.withDefaults().hashToString(AppConstant.PASSWORD_COST, password.toCharArray());
    }

    public static boolean verificationPasswordHashing(String password, String bcryptHashString) {
        if(bcryptHashString.equals(""))return false;
        return BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified;
    }
}

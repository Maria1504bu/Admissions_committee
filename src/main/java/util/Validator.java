package util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Logger LOG = Logger.getLogger(Validator.class);
    private static final String REGEX_FOR_ID = "^[1-9]\\d*$";
    private static final String VALID_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
    private static final String VALID_EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";

    public static int validateId(String id) throws NotValidException {
        int validateId;
            if (id == null || id.isEmpty()) {
                LOG.trace("Login is not filled!");
                throw new NotValidException("Id is not filled");
            }
        Pattern pattern = Pattern.compile(REGEX_FOR_ID);
        Matcher matcher = pattern.matcher(id);
        boolean result = matcher.matches();
        LOG.trace(" Validation for id: " + result);
        if (!result) {
            LOG.trace("Validation is completed with problems! ");
            throw new NotValidException("Id is not valid");
        } else {
            LOG.trace("Validation is successfully completed! ");
        }
         try{validateId = Integer.parseInt(id);}
         catch (NumberFormatException e){
             throw new NotValidException("Id is not a number");
         }
         return validateId;
    }

    public static void validateDate(String input) {
        try {
            new SimpleDateFormat(VALID_DATE_FORMAT).parse(input);
            LOG.trace("Validation of date is successfully completed! ");
        } catch (ParseException e) {
            LOG.trace("Validation of date is completed with problems!");
            throw new NotValidException("Date is not valid");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            LOG.trace("Email is not filled!");
            throw new NotValidException("Email is not filled");
        }
        LOG.trace("Validation for email Collocations: ");
        Matcher matcher = Pattern.compile(VALID_EMAIL).matcher(email);
        boolean result = matcher.matches();
        LOG.trace(" Validation for Email: " + result);
        if (!result) {
            LOG.trace("Validation is completed with problems! ");
            throw new NotValidException("Email is not valid");
        } else {
            LOG.trace("Validation is successfully completed! ");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            LOG.trace("Password is not filled!");
            throw new NotValidException("Password is not filled");
        }
        Matcher matcher = Pattern.compile(VALID_PASSWORD).matcher(password);
        boolean result = matcher.matches();
        LOG.trace(" Validation for Password: " + result);
        if (!result) {
            LOG.trace("Validation is completed with problems! ");
            throw new NotValidException("Password is not valid");
        } else {
            LOG.trace("Validation is successfully completed! ");
        }
    }
}

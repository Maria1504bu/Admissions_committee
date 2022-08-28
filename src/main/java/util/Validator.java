package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String REGEX_FOR_ID ="\\d+";


    public static void validateId(int id) throws NotValidException{
        Pattern pattern = Pattern.compile(REGEX_FOR_ID);
        Matcher matcher = pattern.matcher(String.valueOf(id));
        if(!matcher.matches()) throw new NotValidException("Id is not valid");
    }
}

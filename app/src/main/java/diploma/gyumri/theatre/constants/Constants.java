package diploma.gyumri.theatre.constants;

import java.util.regex.Pattern;

/**
 * Created by root on 7/30/17.
 */

public class Constants {
    public static final String BASE_URL = "https://theater-cs50artashes.cs50.io/";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,12}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_REGEX = Pattern.compile("^[0-9\\-]{8,13}$\n");
    public static String FACEBOOK_URL = "https://www.facebook.com/gyumritheatre";
    public static String FACEBOOK_PAGE_ID = "1163153797037461";
}

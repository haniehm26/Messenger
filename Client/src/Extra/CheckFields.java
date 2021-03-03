package Extra;

public class CheckFields {

    public static boolean notEmpty(String... fields) {
        for (String field : fields) {
            if (field.length() == 0)
                return false;
        }
        return true;
    }

    public static boolean isUsernameValid(String username) {
        char[] chars = username.toCharArray();
        for (char aChar : chars) {
            if (!((aChar >= 'A' && aChar <= 'Z') || (aChar >= 'a' && aChar <= 'z') || (aChar <= '9' && aChar >= '0') || aChar == '_'))
                return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String password){
        return password.length() >= 3;
    }
}

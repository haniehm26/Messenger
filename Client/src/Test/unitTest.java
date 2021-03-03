package Test;

import Extra.CheckFields;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class unitTest {
    @Test
    public static void validUsernameTest() {
        boolean check;
        check = CheckFields.isUsernameValid("123");
        Assertions.assertTrue(check);
        check = CheckFields.isUsernameValid("ABC");
        Assertions.assertTrue(check);
        check = CheckFields.isUsernameValid("abc");
        Assertions.assertTrue(check);
        check = CheckFields.isUsernameValid("abc_123_Abc");
        Assertions.assertTrue(check);
        check = CheckFields.isUsernameValid("abc 123 Abc");
        Assertions.assertFalse(check);
        check = CheckFields.isUsernameValid("!@#$%&^*");
        Assertions.assertFalse(check);
        check = CheckFields.isUsernameValid("12$%^GTDuo+_+");
        Assertions.assertFalse(check);
    }
}

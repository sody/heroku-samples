package com.example.ui.pages.component;

import com.example.ui.base.BasePage;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 */
public class FormSection extends BasePage {

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String about;

    @Property
    private String password;

    @Property
    private String confirmPassword;

    @OnEvent(value = EventConstants.VALIDATE, component = "credentialsSection")
    void validateCredentials() throws ValidationException {
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            throw new ValidationException(message("error.password-match"));
        }
    }
}

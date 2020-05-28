package com.example.foodchoise.auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static EmailValidator emailValidator;

    private EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public static EmailValidator getInstance() {
        if (emailValidator == null) {
            emailValidator = new EmailValidator();
        }
        return emailValidator;
    }

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}

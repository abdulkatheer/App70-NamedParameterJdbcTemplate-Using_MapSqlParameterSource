package org.katheer.validator;

import org.katheer.dto.Account;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Properties;
import java.util.regex.Pattern;

public class AccountValidator implements Validator {
    private Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            Properties errorMessages =
                    PropertiesLoaderUtils.loadProperties(resource);
            Account account = (Account) target;

            //checking name
            if (account.getName() == null || account.getName().equals("")) {
                errors.rejectValue("name", "error.name.required",
                        errorMessages.getProperty("error.name.required"));
            } else {
                Pattern pattern = Pattern.compile("^[a-zA-Z\\s]{4,30}$");
                if(!pattern.matcher(account.getName()).matches()) {
                    errors.rejectValue("name", "error.name.invalid",
                            errorMessages.getProperty("error.name.invalid"));
                }
            }

            //checking branch
            if (account.getBranch() == null || account.getBranch().equals("")) {
                errors.rejectValue("branch", "error.branch.required",
                        errorMessages.getProperty("error.branch.required"));
            } else {
                Pattern pattern = Pattern.compile("^[a-zA-Z\\s]{4,40}$");
                if(!pattern.matcher(account.getBranch()).matches()) {
                    errors.rejectValue("branch", "error.branch.invalid",
                            errorMessages.getProperty("error.branch.invalid"));
                }
            }

            //checking mobile number
            if (account.getMobile() == null || account.getMobile().equals("")) {
                errors.rejectValue("mobile", "error.mobile.required",
                        errorMessages.getProperty("error.mobile.required"));
            } else {
                Pattern pattern = Pattern.compile("^[6-9]\\d{9}$");
                if(!pattern.matcher(account.getMobile()).matches()) {
                    errors.rejectValue("mobile", "error.mobile.invalid",
                            errorMessages.getProperty("error.mobile.invalid"));
                }
            }

            //checking email address
            if(account.getEmail() == null || account.getEmail().equals("")) {
                errors.rejectValue("email", "error.email.required",
                        errorMessages.getProperty("error.email.required"));
            } else {
                Pattern pattern = Pattern.compile("[(\\w+@[a-zA-Z]+?\\" +
                        ".[a-zA-Z]{2,6})]{4,40}$");
                if(!pattern.matcher(account.getEmail()).matches()) {
                    errors.rejectValue("email", "error.email.invalid",
                            errorMessages.getProperty("error.email.invalid"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

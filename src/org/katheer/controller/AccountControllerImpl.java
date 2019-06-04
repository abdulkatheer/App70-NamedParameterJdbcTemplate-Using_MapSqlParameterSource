package org.katheer.controller;

import org.katheer.service.AccountService;
import org.katheer.dto.Account;
import org.katheer.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("accountController")
public class AccountControllerImpl implements AccountController {
    private BufferedReader reader;
    @Autowired
    private AccountValidator accountValidator;
    @Autowired
    private AccountService accountService;

    public AccountControllerImpl() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void createAccount() {
        System.out.println("====CREATE ACCOUNT====");
        System.out.println("Dear Customer, Welcome to Axis Bank!");
        System.out.println("Please enter your details");
        System.out.println();
        Account account = new Account();
        try {
            System.out.print("Name      : ");
            account.setName(reader.readLine().trim());
            System.out.print("Mobile    : ");
            account.setMobile(reader.readLine().trim());
            System.out.print("Email     : ");
            account.setEmail(reader.readLine().trim());
            System.out.print("Branch    : ");
            account.setBranch(reader.readLine().trim());
            System.out.print("Initial(₹): ");
            account.setBalance(Double.parseDouble(reader.readLine().trim()));
        } catch (Exception e) {
            System.out.println("Data Incorrect!");
            e.printStackTrace();
            return;
        }

        if (this.validateAccount(account)) {
            int accNo = accountService.createAccount(account);
            if (accNo == -1) {
                System.out.println("Account creation failed!");
            } else {
                System.out.println("Hello " + account.getName() + ", Account " +
                        "created successfully!");
                System.out.println("Your Account Number is " + accNo + ".");
                System.out.println();
            }
        } else {
            System.out.println("Account creation failed!");
            return;
        }
    }

    @Override
    public void getAccountDetails() {
        System.out.println("====GET ACCOUNT DETAILS====");
        System.out.print("Enter your account number : ");
        int accNo;
        Account account;
        try {
            accNo = Integer.parseInt(reader.readLine().trim());
            account = accountService.getAccountDetails(accNo);
            if (account != null) {
                System.out.println("Account Info");
                System.out.println("------------");
                this.printData(account);
            } else {
                System.out.println("Account Not Found!");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Failed to get account details!");
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void depositAmount() {
        System.out.println("====DEPOSIT AMOUNT====");
        System.out.print("Enter your account number : ");
        int accNo;
        try {
            accNo = Integer.parseInt(reader.readLine().trim());
            Account account = accountService.getAccountDetails(accNo);

            if (account != null) {
                System.out.println("Your current balance is " + account.getBalance() + ".");
                System.out.print("Enter amount to deposit : ");
                double amount = Double.parseDouble(reader.readLine().trim());

                double newBalance = accountService.depositAmount(accNo,
                        amount + account.getBalance());

                System.out.println("Your new balance is " + newBalance + ".");
            } else {
                System.out.println("Account not found!");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void withdrawAmount() {
        System.out.println("====WITHDRAW AMOUNT====");
        System.out.print("Enter your account number : ");
        int accNo;

        try {
            accNo = Integer.parseInt(reader.readLine().trim());
            Account account = accountService.getAccountDetails(accNo);

            if (account != null) {
                System.out.println("Your available balance is " + account.getBalance());
                System.out.print("Enter amount to withdraw : ");
                double amount = Double.parseDouble(reader.readLine().trim());

                if (amount > account.getBalance()) {
                    System.out.println("Amount exceeds available amount!");
                } else {
                    double newBalance = accountService.withdrawAmount(accNo,
                            account.getBalance() - amount);
                    System.out.println("Your new available balance is " + newBalance + ".");
                }
            } else {
                System.out.println("Account not found!");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    private boolean validateAccount(Account account) {
        Map<String, String> error = new HashMap<String, String>();
        MapBindingResult errors = new MapBindingResult(error, "Account");
        accountValidator.validate(account, errors);
        int errorCount = errors.getErrorCount();

        if(errorCount == 0) {
            return true;
        }

        List<ObjectError> allErrors = errors.getAllErrors();
        for(ObjectError error1 : allErrors) {
            System.out.println(error1.getDefaultMessage());
        }
        return false;
    }

    private void printData(Account account) {
        System.out.println("Name    : " + account.getName());
        System.out.println("Mobile  : " + account.getMobile());
        System.out.println("Email   : " + account.getEmail());
        System.out.println("Branch  : " + account.getBranch());
        System.out.println("Balance : " + account.getBalance());
    }
}

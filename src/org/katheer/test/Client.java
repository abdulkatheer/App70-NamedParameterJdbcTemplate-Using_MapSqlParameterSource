package org.katheer.test;

import org.katheer.controller.AccountController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("org" +
                "/katheer/resource/applicationContext.xml");
        AccountController accountController =
                (AccountController) context.getBean("accountController");

        while (true) {
            System.out.println("====BANKING APPLICATION====");
            System.out.println("1.CREATE ACCOUNT");
            System.out.println("2.GET ACCOUNT DETAILS");
            System.out.println("3.DEPOSIT AMOUNT");
            System.out.println("4.WITHDRAW AMOUNT");
            System.out.println("5.EXIT");
            System.out.println();
            System.out.print("Enter your choice : ");

            int choice;
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));

            try {
                choice = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            switch (choice) {
                case 1 :
                    accountController.createAccount();
                    break;
                case 2:
                    accountController.getAccountDetails();
                    break;
                case 3:
                    accountController.depositAmount();
                    break;
                case 4:
                    accountController.withdrawAmount();
                    break;
                case 5:
                    System.out.println("ThanQ for using our application...!");
                    return;
                default:
                    System.out.println("Wrong Choice! Enter choice from [1,2,3,4," +
                            "5]...!");
            }
            System.out.println();
        }
    }
}

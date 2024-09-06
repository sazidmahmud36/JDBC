package BankingManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    protected static final String url = "jdbc:mysql://localhost:3306/banking_system";
    protected static final String username = "root";
    protected static final String password = "sazid364436";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }



        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection,scanner);
            AccountManager accountManager = new AccountManager(connection,scanner);
            Accounts accounts = new Accounts(connection, scanner);

            String email;
            long account_number;


            while (true){
                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");

                System.out.println("Enter Your Choice: ");
                int choice1 = scanner.nextInt();
                switch (choice1){
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        if (email != null){
                            System.out.println();
                            System.out.println("User Logged In!");
                            if (!accounts.account_exist(email)){
                                System.out.println();
                                System.out.println("1.Open a new bank account");
                                System.out.println("2. EXIT");
                                if (scanner.nextInt() == 1){
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully!");
                                    System.out.println("Your Account Number is: " + account_number);
                                }else {
                                    break;
                                }
                            }
                            account_number = accounts.getAccountNumber(email);
                            int choice2 = 0;
                            while (choice2 != 5){
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println(" Enter Your choice: ");
                                choice2 = scanner.nextInt();

                                switch (choice2){
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.get_balance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                        break;
                                }
                            }
                        }
                        else {
                            System.out.println("Incorrect Email or Password");
                        }


                    case 3:
                        System.out.println("Thankyou for using banking system!");
                        System.out.println("Exiting System!");
                    default:
                        System.out.println("Enter Valid Choice!");

                }
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

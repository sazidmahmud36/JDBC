import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";

        String withdrawQuery = "UPDATE accounts SET balance = balance-? WHERE account_number = ?";
        String depositQuery = "UPDATE accounts SET balance = balance+? WHERE account_number = ?";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");
            con.setAutoCommit(false); // by default autoCommit(true)

            try {
                PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);

                withdrawStatement.setDouble(1,500.00);
                withdrawStatement.setString(2,"account456");
                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2,"account12");
                int rowsAffectedwithdrawal = withdrawStatement.executeUpdate();
                int rowsAffectedDeposit = depositStatement.executeUpdate();

                if (rowsAffectedwithdrawal > 0 && rowsAffectedDeposit > 0){
                    con.commit();
                    System.out.println("Transaction Successfull!");
                }else {
                    con.rollback();
                    System.out.println("Transaction failed!");
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

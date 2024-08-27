import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PreparedStatementInsert {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";

        String query = "INSERT INTO employees(id,name,job_title,salary) VALUES(?,?,?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");

            Scanner sc =new Scanner(System.in);
            System.out.print("Enter Id : ");
            int id = sc.nextInt();
            System.out.println("Enter Name: ");
            String name = sc.next();
            System.out.println("Enter Job Title: ");
            String jobTitle = sc.next();
            System.out.println("Enter Salary: ");
            Double salary = sc.nextDouble();




            java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, jobTitle);
            preparedStatement.setDouble(4, salary);


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Data Inserted Successfully!!");
            }else {
                System.out.println("Failed! ");
            }

            preparedStatement.close();
            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}

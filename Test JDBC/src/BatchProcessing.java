import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");
            con.setAutoCommit(false);

//            Statement statement = con.createStatement();
//            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(7,'Sarwar', 'Marketing', 5000)");
//            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(8,'Omayer', 'Designer', 6000)");
//            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(9,'Amit', 'Cyber Security', 9000)");
//            int[] batchResult = statement.executeBatch();
//            con.commit();
//            System.out.println("Batch Executed Successfully!!");



            String query = "INSERT INTO employees(name, job_title, salary) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                System.out.print("Job Title: ");
                String job_title = scanner.nextLine();
                System.out.print("Salary: ");
                Double salary = scanner.nextDouble();

                preparedStatement.setString(1,name);
                preparedStatement.setString(2,job_title);
                preparedStatement.setDouble(3, salary);

                preparedStatement.addBatch();

                System.out.println("Add more values? Y/N: ");
                String decision = scanner.next();

                if (decision.toUpperCase().equals("N")){
                    break;
                }
            }
            int[] batchResult = preparedStatement.executeBatch();

            con.commit();
            System.out.println("Batch executed successfully!!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

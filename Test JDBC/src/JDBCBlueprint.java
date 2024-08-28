import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCBlueprint {

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



            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

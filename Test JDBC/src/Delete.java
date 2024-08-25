import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {



    public static void main(String[] args){

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";
        String query = "DELETE FROM employees WHERE id = 3";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }



        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");
            Statement stmt = con.createStatement();
            int rowsAffected= stmt.executeUpdate(query);
            if (rowsAffected > 0 ){
                System.out.println("Deletation Successful! " + rowsAffected + " row(s)");
            }else {
                System.out.println("Deletation Failed!!");
            }


//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String job_title = rs.getString("job_title");
//                Double salary = rs.getDouble("salary");
//                System.out.println("=============================================");
//                System.out.println("ID: "+id);
//                System.out.println("Name: "+name);
//                System.out.println("Job Title: "+job_title);
//                System.out.println("Salary: "+salary);
//            }

//            rs.close();
            stmt.close();
            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}

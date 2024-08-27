import java.sql.*;

public class PreparedStatementRead {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";

        String query = "select * from employees where name = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");

            java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,"Mahmud");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String jobTitle = resultSet.getString("job_title");
                double salary = resultSet.getDouble("salary");


                System.out.println("Id : " + id);
                System.out.println("Name : " + name);
                System.out.println("Job Title : " + jobTitle);
                System.out.println("Salary : " + salary);
            }

            resultSet.close();
            preparedStatement.close();

            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

}

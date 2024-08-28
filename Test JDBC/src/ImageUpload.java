import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUpload {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";


        String image_path = "/Users/macbookpro/Desktop/JDBC/images/nature.jpg";
        String query = "INSERT INTO image_table(image_data) VALUES(?)";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");


            FileInputStream fileInputStream = new FileInputStream(image_path);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setBytes(1,imageData);

            int affectRows = preparedStatement.executeUpdate();

            if (affectRows > 0){
                System.out.println("Uploaded Successfully!");
            }else {
                System.out.println("Uploading Failed!");
            }



            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

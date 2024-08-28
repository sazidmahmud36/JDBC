import java.io.*;
import java.sql.*;

public class ImageRead {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "sazid364436";
        String folder_path = "/Users/macbookpro/Desktop/JDBC/images/";

        String query = "SELECT image_data FROM image_table WHERE image_id = (?)";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully!!");


            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                byte[] image_data = resultSet.getBytes("image_data");
                String image_path = folder_path + "extracted_image.jpg";
                OutputStream outputStream = new FileOutputStream(image_path);

                outputStream.write(image_data);
                System.out.println("Image Found Successfully!");

            }else {
                System.out.println("Image Not found");
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

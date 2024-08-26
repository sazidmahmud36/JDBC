import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String userName = "root";
    private static final String password = "sazid364436";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }




        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            System.out.println();
            System.out.println("HOTEL MANAGEMENT SYSTEM");
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Reserve a room");
            System.out.println("2. View Reservations");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservations");
            System.out.println("5. Delete Reservations");
            System.out.println("0. Exit");
            System.out.println("Choose an option: ");
            int choise = scanner.nextInt();

            switch (choise){
                case 1:
                    reserveRoom(connection, scanner);
                    break;
                case 2:
                    viewReservations(connection);
                    break;
                case 3:
                    getRoomNumber(connection, scanner);
                    break;
                case 4:
                    updateReservation(connection, scanner);
                    break;
                case 5:
                    deleteReservation(connection, scanner);
                    break;
                case 0:
                    exit();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid Choice. Try again.");



            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e );
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner){
        try {
            System.out.println("Enter GuestName: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.println("Enter Room Number: ");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter Contact Number: ");
            String contactNumber = scanner.next();

            String sql = "INSERT INTO reservations(guest_name, room_number, contact_number)"+
                    "VALUES('"+ guestName +"','"+ roomNumber +"','"+contactNumber +"')";

            try (Statement statement = connection.createStatement()){
                int affectRows = statement.executeUpdate(sql);
                if (affectRows > 0){
                    System.out.println("Reservations Successfull!!");
                }else {
                    System.out.println("Reservation Failed!!");
                }


            }catch (SQLException e){
                System.out.println(e.getMessage());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private static void viewReservations(Connection connection) throws SQLException{
        String sql = "SELECT reservations_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){

            System.out.println("Current Reservation : ");
            System.out.println("+---------------+---------------+-------------+--------------------+---------------------+");
            System.out.println("|Reservation Id |Guest          |Room Number  |Contact Number      |Reservation Date     |");
            System.out.println("+---------------+---------------+-------------+--------------------+---------------------+");

            while (resultSet.next()){
                int reservationId = resultSet.getInt("reservations_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp( "reservation_date").toString();

//                format & Display
                System.out.printf("|%-14d |%-15s|%-13d|%-20s|%-19s| \n",
                        reservationId,guestName,roomNumber,contactNumber,reservationDate);

//                System.out.printf(reservationId   +"|"+ guestName       +"|"+ roomNumber       +"|"+ contactNumber    +"|"+ reservationDate +"\n");
            }
            System.out.println("+---------------+---------------+-------------+--------------------+---------------------+");

        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner){
        try {
            System.out.println("Enter Reservation Id : ");
            int reservationId = scanner.nextInt();
            System.out.println("Enter Guest Name : ");
            String guestName = scanner.next();

            String sql = "SELECT room_number FROM reservations WHERE reservations_id = " + reservationId +" AND guest_name = '"+guestName+"'";

            try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
                if (resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room Number for Reservation ID " + reservationId + " and Guest "+guestName+ " is: " + roomNumber);
                }else {
                    System.out.println("Reservation Not found for the given Id and guest name");
                }

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    private static void updateReservation(Connection connection, Scanner scanner){
        try {
            System.out.println("Enter Reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            if (!resevationExists(connection,reservationId)){
                System.out.println("Reservation not found for the given Id.");
                return;
            }
            System.out.println("Enter New Guest Name: ");
            String newGuestName = scanner.nextLine();
            System.out.println("Enter New Room Numbber: ");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter New Contact Number: ");
            String newContactNumber = scanner.next();

            String sql = "UPDATE reservations SET guest_name = '"+newGuestName+"',room_number='"+newRoomNumber+"', contact_number='"+newContactNumber+"' WHERE reservations_id='"+reservationId+"'";


            try (Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0){
                    System.out.println("Reservation Updated Successfully!!");

                }else {
                    System.out.println("Update Failed!!");
                }
            }


        }catch (Exception e ){
            e.printStackTrace();
        }
    }


    private static void deleteReservation(Connection connection, Scanner scanner){
        try {
            System.out.print("Enter Reservation Id to Delete: ");
            int reservationId = scanner.nextInt();

            if (!resevationExists(connection,reservationId)){
                System.out.println("Reservation not found for the given Id.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservations_id = '"+ reservationId+"'";
            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0){
                    System.out.println("Reservation Deleted Successfully!!");
                }else {
                    System.out.println("Reservation Deletion Failed!!");
                }


            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean resevationExists(Connection connection, int reservationId){
        try {
            String sql = "SELECT reservations_id FROM reservations WHERE reservations_id ='"+ reservationId+"'";
            try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
                return resultSet.next();
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thankyou For Using Hotel Reservation System!!!");

    }






}

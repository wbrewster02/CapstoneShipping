import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Class;
 
 
 
 
 
 
public class DataBaseConnection{
    public static void main(String args[]){
        Connection connection = null;
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://50.6.18.240:3306/ukjirumy_ElevateRetail", "ukjirumy_er_app", "dbPa$$Capstone26");
 
            Statement statement = connection.createStatement();
 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");
 
            while (resultSet.next()){
                int id = resultSet.getInt("Customer_ID");
                String firstName = resultSet.getString("First_Name").trim();
                String lastName = resultSet.getString("First_Name").trim();
                String Email = resultSet.getString("Email").trim();
                String Phone = resultSet.getString("Phone").trim();
 
 
                System.out.println("ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: "+ lastName + "\nEmail: " + Email + "\nPhone: " + Phone);
            }
            resultSet.close();
            statement.close();
            connection.close();
 
 
        } catch (ClassNotFoundException e){
            System.out.println(e);
            e.printStackTrace();
           
        } catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
 
 
 
    }
}
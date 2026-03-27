import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
 
 
 
 
 
 
public class DataBaseConnection{
    public static void main(String args[]){
        Connection connection = null;
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //load db properties
            Properties props = new Properties();
            props.load(new FileInputStream("db.properties"));

            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String dbname = props.getProperty("db.name");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url = "jdbc:mysql://" + host + ";" + port + "/" + dbname;

            connection = DriverManager.getConnection(url, user, password);
 
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
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
 
 
 
    }
}
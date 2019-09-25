
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Tietokantayhteydet {

  private static Connection conn;

  public static Connection getConnection(){
    Scanner lukija = new Scanner(System.in);
    Connection yhteys = null;
    PreparedStatement kysely = null;
    // Jos tietokantayhteys on jo olemassa, ei tarvitse avata uutta
    // Jos tietokantayhteys on jo olemassa, ei tarvitse avata uutta
    if (conn == null) {
      try {

      String driver = "com.mysql.jdbc.Driver"; // JDBC-ajurin luokan nimi
      //Class.forName("com.mysql.jdbc.Driver");
      String url = "jdbc:mysql://localhost/";
      String dbName = "demo";
      Class.forName(driver).newInstance();
        System.out.println("Loaded the appropriate driver.");
        //yhteys = DriverManager.getConnection(kanta, "root", "lasse");

        String userName = "root";
        String password = "lasse";
        // Avataan yhteys
        conn = DriverManager.getConnection(url + dbName, userName, password);

        System.out.println("Yhteys test-tietokantaan muodostettiin onnistuneesti.");
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Tietokantayhteyden muodostaminen epäonnistui", e);
      }
    }
    return conn;
  }

  

  public static void closeConnection() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();//
      }
    }
  }
}

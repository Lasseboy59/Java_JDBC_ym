

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Tuotepaivitys {

  private Connection conn = null;
  private PreparedStatement lis‰‰TuotePst = null;
  private PreparedStatement p‰ivit‰TuotePst = null;
  private PreparedStatement p‰ivit‰Tuoteryhm‰Pst = null;
  private PreparedStatement luoUusiTuoteryhm‰Pst = null;
  private PreparedStatement haeTuoteryhm‰Pst = null;
  Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());

  public Tuotepaivitys(Connection conn) {
    this.conn = conn;
  }

  public void lis‰‰Tuote(Tuote t) {
    try {
      if (this.lis‰‰TuotePst == null) {
        String sql = "INSERT INTO tuote (id, nimi, hinta, julkaisupvm, tuoteryhmaid) "
                + "VALUES (?, ?, ?, ?, ?)";
        this.lis‰‰TuotePst = this.conn.prepareStatement(sql);
      }

      lis‰‰TuotePst.setInt(1, t.getId());
      lis‰‰TuotePst.setString(2, t.getNimi());
      lis‰‰TuotePst.setDouble(3, t.getHinta());
      lis‰‰TuotePst.setDate(4, new java.sql.Date(t.getJulkaisupvm().getTime()));
      lis‰‰TuotePst.setInt(5, t.getTuoteryhmaid());

      lis‰‰TuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen lis‰ys tietokantaan ep‰onnistui", e);
    }
  }

  public void poistaKannasta(int poistettavaID) throws SQLException {
    try {
      int iId = poistettavaID;
      PreparedStatement pst = conn.prepareStatement("DELETE FROM tuote WHERE id = ?");
      pst.setInt(1, iId); // nimi

      pst.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public void p‰ivit‰Tuote(Tuote t) {
    try {

      if (this.p‰ivit‰TuotePst == null) {
        String sql = "UPDATE tuote "
                + "SET nimi = ?, hinta = ?, julkaisupvm = ?, tuoteryhmaid = ?"
                + "WHERE id = ?";
        this.p‰ivit‰TuotePst = conn.prepareStatement(sql);
      }

      p‰ivit‰TuotePst.setString(1, t.getNimi());
      p‰ivit‰TuotePst.setDouble(2, t.getHinta());
      p‰ivit‰TuotePst.setDate(3, new java.sql.Date(t.getJulkaisupvm().getTime()));
      p‰ivit‰TuotePst.setInt(4, t.getTuoteryhmaid());
      p‰ivit‰TuotePst.setInt(5, t.getId());

      p‰ivit‰TuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen tietojen p‰ivitt‰minen tietokantaan ep‰onnistui", e);
    }
  }

  public void p‰ivit‰TuoteRyhm‰(Tuote t) {
    try {

      if (this.p‰ivit‰TuotePst == null) {
        String sql = "UPDATE tuote "
                + "SET nimi = ?, hinta = ?, julkaisupvm = ?, tuoteryhmaid = ?"
                + "WHERE id = ?";
        this.p‰ivit‰TuotePst = conn.prepareStatement(sql);
      }

      p‰ivit‰TuotePst.setString(1, t.getNimi());
      p‰ivit‰TuotePst.setDouble(2, t.getHinta());
      p‰ivit‰TuotePst.setDate(3, new java.sql.Date(t.getJulkaisupvm().getTime()));
      p‰ivit‰TuotePst.setInt(4, t.getTuoteryhmaid());
      p‰ivit‰TuotePst.setInt(5, t.getId());

      p‰ivit‰TuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen tietojen p‰ivitt‰minen tietokantaan ep‰onnistui", e);
    }
  }

  public void p‰ivit‰Tuoteryhm‰Tuotteille(Tuote[] tuotteet, String tuoteryhm‰Nimi) {
    try {

      // Asetetaan aluksi autocommit-asetus pois p‰‰lt‰. Haluamme, ett‰ 
      // kaikki operaatiot kuuluvat samaan transaktioon. 
      this.conn.setAutoCommit(false);

      // Kysely on tallennettu luokkatason muuttujaan ja 
      // kysely t‰ytyy alustaa siten vain, jos sit‰ ei ole 
      // viel‰ alustettu. 
      if (this.p‰ivit‰Tuoteryhm‰Pst == null) {
        String sql = "UPDATE tuote SET tuoteryhmaid = ? WHERE id = ?";
        this.p‰ivit‰Tuoteryhm‰Pst = this.conn.prepareStatement(sql);
      }

      // Tutkitaan onko haluttu tuoteryhm‰ jo olemassa. Jos ei ole, 
      // luodaan kantaan uusi tuoteryhm‰. 
      int tuoteryhm‰nId = palautaTuoteryhm‰Id(tuoteryhm‰Nimi);
      if (tuoteryhm‰nId == -1) {
        // Tuoteryhm‰n tietoja ei lˆytynyt. Luodaan kantaan uusi tuoteryhm‰
        tuoteryhm‰nId = lis‰‰Tuoteryhm‰(tuoteryhm‰Nimi);
      }

      // K‰yd‰‰n l‰pi kaikki p‰ivitett‰v‰t tuotteet ja p‰ivitet‰‰n
      // niille haluttu tuoteryhm‰‰
      for (int i = 0; i < tuotteet.length; i++) {
        p‰ivit‰Tuoteryhm‰Pst.setInt(1, tuoteryhm‰nId);
        p‰ivit‰Tuoteryhm‰Pst.setInt(2, tuotteet[i].getId());
        p‰ivit‰Tuoteryhm‰Pst.executeUpdate();
      }

      // Hyv‰ksyt‰‰n kaikki muutokset
      this.conn.commit();

    } catch (Exception e) {

      // Perutaan kaikki tehdyt muutokset
      try {
        this.conn.rollback();
      } catch (SQLException sqle) {
        throw new RuntimeException("Rollback-operaatio ep‰onnistui", sqle);
      }

      // Tulostetaan virheen tiedot ruudulle
      e.printStackTrace();
      throw new RuntimeException("Tuotteen lis‰ys tietokantaan ep‰onnistui", e);

    } finally {
      // Palautetaan autocommit-asetus alkuper‰iseen arvoon
      try {
        this.conn.setAutoCommit(true);
      } catch (SQLException sqle) {
        throw new RuntimeException("Autocommit-asetus ep‰onnistui", sqle);
      }
    }
  }
  
  
   private int palautaTuoteryhm‰Id(String tuoteryhm‰nNimi)
    {
      try {

        int tuoteryhm‰nId = 1;
        
        // Kysely on tallennettu luokkatason muuttujaan ja 
        // kysely t‰ytyy alustaa siten vain, jos sit‰ ei ole 
        // viel‰ alustettu. 
        if (this.haeTuoteryhm‰Pst == null) {
          String sql = "SELECT id from tuoteryhma WHERE nimi = ?";
          this.haeTuoteryhm‰Pst = conn.prepareStatement(sql);
        }
        
        // Asetetaan sql-lauseeseen poistettavan tuotteen id
        haeTuoteryhm‰Pst.setString(1, tuoteryhm‰nNimi);

        // Haetaan tuoteryhm‰n tiedot
          ResultSet rs = haeTuoteryhm‰Pst.executeQuery();
          
          // Tutkitaan tulos
          if (rs.next() == false) {
            // Mit‰‰n tietoja ei lˆytynyt
            tuoteryhm‰nId = -1;
          } else {
            // Lˆytyi jotain tietoja, haetaan tiedot ensimm‰iselt‰ rivilt‰
            tuoteryhm‰nId = rs.getInt("id");
          }
          
          return tuoteryhm‰nId;
          
      } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Tuoteryhm‰n tietojen haku ep‰onnistui", e);
      }
    }


    private int lis‰‰Tuoteryhm‰(String tuoteryhm‰nNimi)
    {
      try {

        if (this.luoUusiTuoteryhm‰Pst == null) {
          String sql = "INSERT INTO tuoteryhma (id, nimi)" + 
        "VALUES (?, ?)";
          this.luoUusiTuoteryhm‰Pst = conn.prepareStatement(sql);
        }
        
        // Uusi tuoteryhm‰n id
        int id = luoUusiAvain();
        
        // Annetaan kyselylle vaadittavat tiedot
        luoUusiTuoteryhm‰Pst.setInt(1, id);
        luoUusiTuoteryhm‰Pst.setString(2, tuoteryhm‰nNimi);

        // Suoritetaan p‰ivitys
        luoUusiTuoteryhm‰Pst.executeUpdate();
        
        return id;

      } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Uuden tuoteryhm‰n luominen ep‰onnistui", e);
      }
    }

  
  

  public void luoUusiTuote(Scanner lukija, Tuotepaivitys paivitys) throws NumberFormatException {
    System.out.print("Anne tuotteen nimi: ");
    String sName = lukija.nextLine();
    System.out.print("Anna hinta ");
    double dHinta = Double.parseDouble(lukija.nextLine());
    System.out.print("Anna p‰iv‰ys xx.mm.yyyy: ");
    String sPvm = lukija.nextLine();
    System.out.print("Anna tuoteryhm‰ ID: ");
    int iTuoteryhma = Integer.parseInt(lukija.nextLine());
    System.out.print("Anna tuoteryhm‰n nimi: ");
    String sTuoteryhmanNimi = lukija.nextLine();
    Tuote t = new Tuote(Tuotepaivitys.luoUusiAvain(), sName, dHinta, Tuotepaivitys.stringToDate(sPvm), iTuoteryhma, sTuoteryhmanNimi);
    paivitys.lis‰‰Tuote(t);
  }

  public void tuotteenPaivitys(Scanner lukija, Tuotehaku haku, Tuotepaivitys paivitys) throws NumberFormatException {
    int id = 0; // metodin ylikuormittaminen
    System.out.print("Mik‰ tuote haetaan: ");
    String sName = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimell‰(sName);
    Tuotepaivitys.tulostaTuotteet(tuotteet);
    Tuote t = haku.haeNimell‰(sName, id);
    System.out.println("P‰ivit‰ uusi tuoteryhma, anna ryhm‰n ID: ?");
    int iTuoteryhma = Integer.parseInt(lukija.nextLine());
    t.setTuoteryhmaid(iTuoteryhma);
    paivitys.p‰ivit‰Tuote(t);
    System.out.println(t);
  }

  public static void tulostaTuotteet(Tuote[] tuotteet) {
    if (tuotteet != null) {
      for (int i = 0; i < tuotteet.length; i++) {
        System.out.println(tuotteet[i]);
      }
    }
  }

  public static java.sql.Date stringToDate(String s) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    try {
      return new java.sql.Date(formatter.parse(s).getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public static int luoUusiAvain() {
    return new Integer(new Long(System.currentTimeMillis()).intValue()).intValue() * (-1);
  }
}

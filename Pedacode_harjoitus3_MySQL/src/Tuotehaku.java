

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Tuotehaku {

  private Connection conn = null;
  private PreparedStatement hakuNimell‰Pst = null;

  public Tuotehaku(Connection conn) {
    this.conn = conn;
  }

  public Tuote[] haeNimell‰(String haettavaNimi) {
    try {
      if (this.hakuNimell‰Pst == null) {
        String sql = "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tuoteryhmaid, tr.nimi "
                + "FROM tuote t, tuoteryhma tr "
                + "WHERE tr.id = t.tuoteryhmaid "
                + "AND t.nimi = ? "
                + "ORDER BY t.julkaisupvm ";
        this.hakuNimell‰Pst = this.conn.prepareStatement(sql);
      }

      hakuNimell‰Pst.setString(1, haettavaNimi);
      ResultSet rs = hakuNimell‰Pst.executeQuery();
      ArrayList tulokset = new ArrayList();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);

        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        tulokset.add(t);

      }

      Tuote[] tuotteet = new Tuote[tulokset.size()];
      for (int i = 0; i < tulokset.size(); i++) {
        tuotteet[i] = (Tuote) tulokset.get(i);
      }
      return tuotteet;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public Tuote haeNimell‰(String haettavaNimi, int ID) {
    // metodin ylikuormittaminen
    try {
      if (this.hakuNimell‰Pst == null) {
        String sql = "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tuoteryhmaid, tr.nimi "
                + "FROM tuote t, tuoteryhma tr "
                + "WHERE tr.id = t.tuoteryhmaid "
                + "AND t.nimi = ? "
                + "ORDER BY t.julkaisupvm ";
        this.hakuNimell‰Pst = this.conn.prepareStatement(sql);
      }

      hakuNimell‰Pst.setString(1, haettavaNimi);
      ResultSet rs = hakuNimell‰Pst.executeQuery();
      //ArrayList tulokset = new ArrayList();
      Tuote apu = null;

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);

        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        apu = t;
      }
      return apu;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public void haeKaikkiNimikkeet() {
    try {
      String sNimi = "";

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi <> ? "
              + "ORDER BY tr.id ");

      s.setString(1, sNimi);
      ResultSet rs = s.executeQuery();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);
        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        System.out.println(t);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public int palautaTuotteenID(Tuote[] tuotteet, String sNimi) {
    int iId = 0;
    if (tuotteet != null) {
      for (int i = 0; i < tuotteet.length; i++) {
        if (tuotteet[i].getNimi().equalsIgnoreCase(sNimi)) {
          iId = tuotteet[i].getId();
          //System.out.println("id: " + tuotteet[i].getId());
        }
      }
    }
    return iId;
  }

  public int haeTuoteId(String sTuoteryhmanNimi) {
    try {
      int tuoteryhmaid = 0;

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi = ? ");

      s.setString(1, sTuoteryhmanNimi);
      ResultSet rs = s.executeQuery();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);
        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        System.out.println(t);
      }
      return tuoteryhmaid;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public boolean lueKannastaTuoteryhmanNimi(String sNimi) {
    boolean loytyiko = false;
    try {

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi = ? "
              + "ORDER BY t.julkaisupvm ");

      s.setString(1, sNimi);

      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        int id = rs.getInt(1);
        int tuoteId = id;
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        String tuoteryhm‰ = rs.getString(5);
        String tuoteryhm‰nimi = rs.getString(6);
        if (tuoteryhm‰nimi.equalsIgnoreCase(sNimi)) {
          loytyiko = true;
        }
      }
      return loytyiko;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  public void tuotteenHaku(Scanner lukija, Tuotehaku haku) {
    System.out.print("Mik‰ tuote haetaan: ");
    String sName = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimell‰(sName);
    Tuotepaivitys.tulostaTuotteet(tuotteet);
  }

  public static void haeJaTulostaTiedot() throws ClassNotFoundException {
    try {
      // Muodostetaan yhteys tietokantaan
      Connection conn = Tietokantayhteydet.getConnection();
      Statement s = conn.createStatement();

      // Kysely, jolla tiedot haetaan
      ResultSet rs = s.executeQuery(
              "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid");

      // Tulostetaan ruudulle eri kenttien nimet
      System.out.println("\n\n" + fill("ID", 4) + fill("Nimi", 50)
              + fill("Hinta", 8) + fill("Julkaisupvm", 12) + fill("Tuoteryhm‰", 13));
      System.out.println("----------------------------------------------------------------------------------------");

      // K‰yd‰‰n l‰pi tietokannasta haetut tiedot rivi kerrallaan ja tulostetaan ne ruudulle
      while (rs.next()) {

        // Tallennetaan yhden rivin kent‰t muuttujiin
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        String tuoteryhm‰ = rs.getString(5);

        // Tulostetaan muuttujien arvot ruudulle. 
        System.out.println(fill("" + id, 4) + fill(nimi, 50)
                + fill("" + hinta, 8) + fill(pvm.toString(), 12) + fill(tuoteryhm‰, 13));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
    }
  }

  private static String fill(String merkkijono, int kokonaispituus) {
    String s = null;
    if (merkkijono != null) {
      s = merkkijono;
      while (s.length() < kokonaispituus) {
        s = s.concat(" ");
      }
    }
    return s;
  }
}

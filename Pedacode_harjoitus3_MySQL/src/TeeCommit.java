

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TeeCommit {

  private Connection conn = null;
  Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());
  Tuotepaivitys paivitys = new Tuotepaivitys(Tietokantayhteydet.getConnection());

  public TeeCommit(Connection conn) {
    this.conn = conn;
  }

  public void teeCommit() throws SQLException {
    int iMaara = 0;
    int iTuoteryhma = 0;
    int tuoteId = 0, tuoteId2 = 0;
    Scanner lukija = new Scanner(System.in);
    System.out.print("Mik‰ tuotenimike (1): ");
    String sNimi = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimell‰(sNimi);
    tuoteId = haku.palautaTuotteenID(tuotteet, sNimi);
    paivitys.tulostaTuotteet(tuotteet);
    System.out.println("");

    System.out.print("Mik‰ tuotenimike (2): ");
    String sNimi2 = lukija.nextLine();
    Tuote[] tuotteet2 = haku.haeNimell‰(sNimi2);
    tuoteId2 = haku.palautaTuotteenID(tuotteet2, sNimi2);
    paivitys.tulostaTuotteet(tuotteet2);
    //System.out.print(sNimi2 + " Commit TuoteId: " + tuoteId2 + "\n");
    //System.out.println("Tulosta kaikki tuotteet: ");
    paivitys.tulostaTuotteet(tuotteet);

    System.out.print("Anna tuoteryhm‰nimi: ");
    String sTuoteryhmanNimi = lukija.nextLine();
    System.out.println("Antamasi TuoteryhmanNimi " + sTuoteryhmanNimi);

    boolean loytyiko = haku.lueKannastaTuoteryhmanNimi(sTuoteryhmanNimi);
    if (loytyiko) {
      System.out.println("Tuoteryhm‰ " + sTuoteryhmanNimi + " on kannassa");
      iTuoteryhma = haku.haeTuoteId(sTuoteryhmanNimi);
    } else {
      System.out.println("Tuoteryhm‰‰ " + sTuoteryhmanNimi + " ei ole kannassa - > luodaan kantaan");

      Statement st = conn.createStatement();
      ResultSet res = st.executeQuery("SELECT COUNT(*) FROM TUOTERYHMA");
      while (res.next()) {
        iMaara = res.getInt(1);
      }

      System.out.print("---- Seuraavan tuoteryhm‰n indeksi: " + (iMaara + 1) + " -------------\n");
    }

    if (loytyiko) {
      conn.setAutoCommit(false);
      try {

        PreparedStatement s =
                conn.prepareStatement("UPDATE tuote SET tuoteryhmaid = ? "
                + "WHERE id = ?");

        s.setInt(1, iTuoteryhma);
        s.setInt(2, tuoteId);
        s.executeUpdate();          // transaktion suoritus alkaa 1. operaatiosta

        s.setInt(1, iTuoteryhma);
        s.setInt(2, tuoteId2);
        s.executeUpdate();          // p‰ivitys kuuluu samaan transaktioon kuin edellinen p‰ivitys     
        // transaktio p‰‰ttyy, hyv‰ksyt‰‰n muutokset
        conn.commit();

      } catch (SQLException e) {
        conn.rollback(); // virheen sattuessa perutaan transaktio
        throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
      }
      conn.setAutoCommit(true);
    } else {
      Statement st2 = conn.createStatement();
      String tableName = "TUOTERYHMA";
      st2.execute("INSERT INTO " + tableName + " values (" + (iMaara + 1) + ",'" + sTuoteryhmanNimi + "')");

      conn.setAutoCommit(false);
      try {

        PreparedStatement s =
                conn.prepareStatement("UPDATE tuote SET tuoteryhmaid = ? "
                + "WHERE id = ?");

        s.setInt(1, (iMaara + 1));
        s.setInt(2, tuoteId);
        s.executeUpdate();          // transaktion suoritus alkaa 1. operaatiosta

        s.setInt(1, (iMaara + 1));
        s.setInt(2, tuoteId2);
        s.executeUpdate();          // p‰ivitys kuuluu samaan transaktioon kuin edellinen p‰ivitys     
        // transaktio p‰‰ttyy, hyv‰ksyt‰‰n muutokset
        conn.commit();

      } catch (SQLException e) {
        conn.rollback(); // virheen sattuessa perutaan transaktio
        throw new RuntimeException("Tietojen haku tietokannasta ep‰onnistui", e);
      }
      conn.setAutoCommit(true);

    }

  }
}

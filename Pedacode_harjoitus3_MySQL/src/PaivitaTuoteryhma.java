

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PaivitaTuoteryhma {

  private Connection conn = null;
  Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());
  Tuotepaivitys paivitys = new Tuotepaivitys(Tietokantayhteydet.getConnection());

  public PaivitaTuoteryhma(Connection conn) {
    this.conn = conn;
  }

  public void paivitaTuoteryhma() throws SQLException {
    int iMaara = 0;
    int iTuoteryhma = 0;
    int tuoteId = 0, tuoteId2 = 0;
    Scanner lukija = new Scanner(System.in);
    System.out.print("Mikä tuotenimike: ");
    String sNimi = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimellä(sNimi);
    tuoteId = haku.palautaTuotteenID(tuotteet, sNimi);
    paivitys.tulostaTuotteet(tuotteet);
    System.out.println("");

    System.out.print("Anna tuoteryhmänimi: ");
    String sTuoteryhmanNimi = lukija.nextLine();
    System.out.println("Antamasi TuoteryhmanNimi " + sTuoteryhmanNimi);

    boolean loytyiko = haku.lueKannastaTuoteryhmanNimi(sTuoteryhmanNimi);
    if (loytyiko) {
      System.out.println("Tuoteryhmä " + sTuoteryhmanNimi + " on kannassa");
      iTuoteryhma = haku.haeTuoteId(sTuoteryhmanNimi);
    } else {
      System.out.println("Tuoteryhmää " + sTuoteryhmanNimi + " ei ole kannassa - > luodaan kantaan");

      Statement st = conn.createStatement();
      ResultSet res = st.executeQuery("SELECT COUNT(*) FROM TUOTERYHMA");
      while (res.next()) {
        iMaara = res.getInt(1);
      }

      System.out.print("---- Seuraavan tuoteryhmän indeksi: " + (iMaara + 1) + " -------------\n");
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

        conn.commit();

      } catch (SQLException e) {
        conn.rollback(); // virheen sattuessa perutaan transaktio
        throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
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

        conn.commit();

      } catch (SQLException e) {
        conn.rollback(); // virheen sattuessa perutaan transaktio
        throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
      }
      conn.setAutoCommit(true);

    }

  }
}

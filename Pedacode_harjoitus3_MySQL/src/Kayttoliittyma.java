

import java.util.Scanner;

public class Kayttoliittyma {

  public void kysyJaSuoritaToiminto() throws Exception {
    Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());
    Tuotehaku haeKaikki = new Tuotehaku(Tietokantayhteydet.getConnection());
    Tuotepaivitys poisto = new Tuotepaivitys(Tietokantayhteydet.getConnection());
    Tuotepaivitys paivitys = new Tuotepaivitys(Tietokantayhteydet.getConnection());
    TeeCommit commit = new TeeCommit(Tietokantayhteydet.getConnection());
    PaivitaTuoteryhma paivita = new PaivitaTuoteryhma(Tietokantayhteydet.getConnection());
    Scanner lukija = new Scanner(System.in);

    while (true) {
      System.out.print("\n------ Komennot ---------------");
      System.out.print("\n- 1 Hae nimike \n- 2 Poista ID:ll‰ \n- 3 Luo uusi tuote\n- 4 P‰ivit‰ tuotteen tuoteryhm‰ (input ID)"
              + "\n- 5 P‰ivit‰ tuotteen tuoteryhm‰ (input nimi)\n- 6 Hae kaikki\n- 7 COMMIT\n- 8 Commit2\n- 9 Lopetus\n");
      System.out.println("-------------------------------");
      String komento = lukija.nextLine();
      if (komento.equals("9")) {
        break;
      } else if (komento.equals("1")) {
        haku.tuotteenHaku(lukija, haku);
      } else if (komento.equals("2")) {
        System.out.println("Anna poistettavan nimikeen ID");
        Integer iId = Integer.parseInt(lukija.nextLine());
        poisto.poistaKannasta(iId);
      } else if (komento.equals("3")) {
        paivitys.luoUusiTuote(lukija, paivitys);
      } else if (komento.equals("4")) {
        paivitys.tuotteenPaivitys(lukija, haku, paivitys);
      } else if (komento.equals("6")) {
        haeKaikki.haeKaikkiNimikkeet();
      } else if (komento.equals("7")) {
        commit.teeCommit();
      } else if (komento.equals("5")) {
        paivita.paivitaTuoteryhma();
      } else if (komento.equals("8")) {
        //Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());
        System.out.print("Mille tuotteelle tehd‰‰n commit: ");
        String sTuoteNimike = lukija.nextLine();
        System.out.print("Anna tuoteryhm‰n nimi: ");
        String sTuoteRyhma = lukija.nextLine();
        Tuote[] tuotteet = haku.haeNimell‰(sTuoteNimike);
        //Tuoteryhm‰p‰ivitys p‰ivitys = new Tuoteryhm‰p‰ivitys(Tietokantayhteydet.getConnection());
        paivitys.p‰ivit‰Tuoteryhm‰Tuotteille(tuotteet, sTuoteRyhma);
        haku.haeJaTulostaTiedot();
        // 
        // P‰ivitet‰‰n edell‰ luodun tuotteen tuoteryhm‰ksi 
        // takaisin "Muut"      
        tuotteet = haku.haeNimell‰(sTuoteNimike);
        paivitys.p‰ivit‰Tuoteryhm‰Tuotteille(tuotteet, "Muut");
        haku.haeJaTulostaTiedot();
      }

    }
  }

  public static void main(String[] args) throws Exception {
    Kayttoliittyma k1 = new Kayttoliittyma();
    k1.kysyJaSuoritaToiminto();

  }
}

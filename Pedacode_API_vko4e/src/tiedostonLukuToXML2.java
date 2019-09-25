
// Tiedoston luku ja muunnosmetodit + testaus
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;

public class tiedostonLukuToXML2 {

  public static void muunna(String keskuskonetiedostonNimi, String xmlTiedostonNimi,
          String lajittelukentta, String lajittelujarjestys)
          throws FileNotFoundException, IOException {

    String jajestys = lajittelujarjestys;
    String parametri = lajittelukentta;

    BufferedReader keskuskonetiedosto = null;
    PrintWriter xmlTiedosto = null;


    // Koodin mukaan laskevaan ja nousevaan j‰rjestykseen
    TreeSet<Tuote> codeComp = new TreeSet<Tuote>(new MyCodeComp());
    TreeSet<Tuote> codeCompUp = new TreeSet<Tuote>(new MyCodeCompUp());
    // Nimen mukaan laskevaan ja nousevaan j‰rjestykseen
    TreeSet<Tuote> nameComp = new TreeSet<Tuote>(new MyNameComp());
    TreeSet<Tuote> nameCompUp = new TreeSet<Tuote>(new MyNameCompUp());
    // Hinnan mukaan laskevaan ja nousevaan j‰rjestykseen
    TreeSet<Tuote> priceComp = new TreeSet<Tuote>(new MyPriceComp());
    TreeSet<Tuote> priceCompUp = new TreeSet<Tuote>(new MyPriceCompUp());


    try {
      Locale.setDefault(new Locale("en", "US"));
      keskuskonetiedosto = new BufferedReader(new FileReader(keskuskonetiedostonNimi));


      // Luetaan rivej‰ tiedostosta niin kauan, kun tietoja riitt‰‰. 
      // Kun tietoja ei en‰‰ lˆydy, palauttaa readLine()-metodi arvon null
      String rivi = null;
      while ((rivi = keskuskonetiedosto.readLine()) != null) {

        Scanner rivinTiedot = new Scanner(rivi);
        rivinTiedot.useDelimiter("::");

        String koodi = rivinTiedot.next(); // 1. kentt‰ on tuotekoodi
        String nimi = rivinTiedot.next(); // 2. kentt‰ on tuotteen nimi
        double hinta = rivinTiedot.nextDouble(); // 3. kentt‰ on hinta

        // k‰ytet‰‰n TreeSet comparaattoreita talletukseen
        if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("nimi")) {
          nameComp.add(new Tuote(koodi, nimi, hinta));
        }
        if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("nimi")) {
          nameCompUp.add(new Tuote(koodi, nimi, hinta));
        }
        if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("hinta")) {
          priceComp.add(new Tuote(koodi, nimi, hinta));
        }
        if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("hinta")) {
          priceCompUp.add(new Tuote(koodi, nimi, hinta));
        }
        if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("koodi")) {
          codeComp.add(new Tuote(koodi, nimi, hinta));
        }
        if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("koodi")) {
          codeCompUp.add(new Tuote(koodi, nimi, hinta));
        }

      }

    } finally {
      if (keskuskonetiedosto != null) {
        keskuskonetiedosto.close();
      }
    }

    try {
      Locale.setDefault(new Locale("en", "US"));
      xmlTiedosto = new PrintWriter(new BufferedWriter(new FileWriter(xmlTiedostonNimi)));
      alustaXmlTiedosto(xmlTiedosto);

      if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("nimi")) {
        for (Tuote e : nameComp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
      if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("nimi")) {
        for (Tuote e : nameCompUp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
      if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("koodi")) {
        for (Tuote e : codeComp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
      if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("koodi")) {
        for (Tuote e : codeCompUp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
      if (jajestys.equalsIgnoreCase("laskeva") && parametri.equalsIgnoreCase("hinta")) {
        for (Tuote e : priceComp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
      if (jajestys.equalsIgnoreCase("nouseva") && parametri.equalsIgnoreCase("hinta")) {
        for (Tuote e : priceCompUp) {
          kirjoitaTuotteenTiedot(xmlTiedosto, e.getCode(), e.getName(), e.getPrice());
        }
      }
    } finally {

      paataXmlTiedosto(xmlTiedosto);  // XML-tiedoston loppurivi (tai oma metodi)

      if (xmlTiedosto != null) {
        xmlTiedosto.close();
      }
    }
  } // main

  public static void kirjoitaTuotteenTiedot(PrintWriter xmlTiedosto, String koodi, String nimi, double hinta) {
    xmlTiedosto.println("  <tuote>");
    xmlTiedosto.println("    <koodi>" + koodi + "</koodi>");
    xmlTiedosto.println("    <nimi>" + nimi + "</nimi>");
    xmlTiedosto.println("    <hinta>" + hinta + "</hinta>");
    xmlTiedosto.println("  </tuote>");
  }

  private static void alustaXmlTiedosto(PrintWriter xmlTiedosto) {
    xmlTiedosto.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    xmlTiedosto.println("<tiedostot>");
  }

  private static void paataXmlTiedosto(PrintWriter xmlTiedosto) {
    xmlTiedosto.println("</tiedostot>"); // XML-tiedoston loppurivi (tai oma metodi)
  }
} // class
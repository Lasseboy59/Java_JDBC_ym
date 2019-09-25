// apuluokka Gui-luokan ja tiedostonLukuToXML2 luokan välissä
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

public class TiedostoMuunninTest {

  private boolean onnistuikoMuunnos;

  public boolean onnistuikoMuunnos() {
    if (onnistuikoMuunnos) {
      return true;
    } return false; 
  }

  public void tiedostomuunnin(String keskuskonetiedostonNimi, String xmltiedostonNimi, String kenttaS, String jarjestysS) throws FileNotFoundException, IOException{

    String keskuskonetiedosto = keskuskonetiedostonNimi;
    String xmltiedosto = xmltiedostonNimi;
    String kentta = kenttaS;
    String jarjestys = jarjestysS;
    onnistuikoMuunnos = true;

    Locale.setDefault(new Locale("en", "US"));

    try {
      tiedostonLukuToXML2.muunna(keskuskonetiedosto, xmltiedosto, kentta, jarjestys);

    } catch (Exception e) {
      onnistuikoMuunnos = false;
      System.out.println("Tietojen muuttaminen EI onnistunut");
      e.printStackTrace();
      
    }
    if (onnistuikoMuunnos) {
      System.out.println("Tietojen muuttaminen onnistui");
    }
  }
}

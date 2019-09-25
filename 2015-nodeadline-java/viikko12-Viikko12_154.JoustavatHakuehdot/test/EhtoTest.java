
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Scanner;
import lukija.ehdot.Ehto;
import lukija.ehdot.SisaltaaSanan;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EhtoTest {

    Reflex.ClassRef<Object> classRef;

    @Points("154.1")
    @Test
    public void kaikkiRivitOlemassa() {
        String klassname = "lukija.ehdot.KaikkiRivit";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.1")
    @Test
    public void kaikkiRivitKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.KaikkiRivit";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "()",
                classRef.constructor().takingNoParams().isPublic());

        String v = "virheen aiheutti koodi new " + post(klassname) + "();";
        classRef.constructor().takingNoParams().withNiceError(v).invoke();
    }

    private Ehto kaikkiRivit() throws Throwable {
        String klassname = "lukija.ehdot.KaikkiRivit";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().takingNoParams().invoke();
    }

    @Points("154.1")
    @Test
    public void kaikkiRivitOnEhto() {
        onEhto("lukija.ehdot.KaikkiRivit");
    }

    @Points("154.1")
    @Test
    public void kaikkiRivitToimii() throws Throwable {
        String[][] sanat = {
            {"testi", "t"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti.", "t"},
            {"eins dwei drei", "t"},
            {"Each and every day, I have less and less hope for the 2012-2013 season.", "t"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "t"},
            {"Kotimainen hunaja uhkaa loppua ennen kevättä", "t"},
            {"", "t"}
        };

        Ehto e = kaikkiRivit();

        testaa("lukija.ehdot.KaikkiRivit", e, "Ehto ehto = new KaikkiRivit();", sanat);
    }

    /*
     * 
     */
    @Points("154.2")
    @Test
    public void loppuuHuutoTaiKysymysmerkkiinOlemassa() {
        String klassname = "lukija.ehdot.LoppuuHuutoTaiKysymysmerkkiin";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.2")
    @Test
    public void loppuuHuutoTaiKysymysmerkkiinKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.LoppuuHuutoTaiKysymysmerkkiin";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "()",
                classRef.constructor().takingNoParams().isPublic());

        String v = "virheen aiheutti koodi new " + post(klassname) + "();";
        classRef.constructor().takingNoParams().withNiceError(v).invoke();
    }

    private Ehto loppuuHuutoTaiKysymysmerkkiin() throws Throwable {
        String klassname = "lukija.ehdot.LoppuuHuutoTaiKysymysmerkkiin";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().takingNoParams().invoke();
    }

    @Points("154.2")
    @Test
    public void loppuuHuutoTaiKysymysmerkkiinOnEhto() {
        onEhto("lukija.ehdot.LoppuuHuutoTaiKysymysmerkkiin");
    }

    @Points("154.2")
    @Test
    public void loppuuHuutoTaiKysymysmerkkiinToimii() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"testi!", "t"},
            {"testi?", "t"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti?", "t"},
            {"eins dwei drei!", "t"},
            {"Each and every day, I have less and less hope for the 2012-2013 season!", "t"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa!", "t"},
            {"Kotimainen hunaja uhkaa loppua ennen kevättä?", "t"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti.", "f"},
            {"eins dwei drei!a", "f"},
            {"Each and every day! I have less and less hope for the 2012-2013 season.", "f"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "f"},
            {"??!?!?!?!?Kotimainen hunaja uhkaa loppua ennen kevättä", "f"},
            {"!", "t"},
            {"", "f"}
        };

        Ehto e = loppuuHuutoTaiKysymysmerkkiin();

        testaa("lukija.ehdot.LoppuuHuutoTaiKysymysmerkkiin", e, "Ehto ehto = new LoppuuHuutoTaiKysymysmerkkiin();", sanat);
    }
    /*
     * 
     */

    @Points("154.3")
    @Test
    public void pituusVahintaanOlemassa() {
        String klassname = "lukija.ehdot.PituusVahintaan";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.3")
    @Test
    public void pituusVahintaanKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.PituusVahintaan";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "(int pituus)",
                classRef.constructor().taking(int.class).isPublic());

        String v = "virheen aiheutti koodi new " + post(klassname) + "(10);";
        classRef.constructor().taking(int.class).withNiceError(v).invoke(10);
    }

    private Ehto pituusVahintaan(int p) throws Throwable {
        String klassname = "lukija.ehdot.PituusVahintaan";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().taking(int.class).invoke(p);
    }

    @Points("154.3")
    @Test
    public void pituusVahintaanOnEhto() {
        onEhto("lukija.ehdot.PituusVahintaan");
    }

    @Points("154.3")
    @Test
    public void pituusVahintaanToimii1() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti.", "t"},
            {"eins dwei drei", "f"},
            {"Each and every day, I have less and less hope for the 2012-2013 season.", "t"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "t"},
            {"Kotimainen hunaja uhkaa loppua ennen kevättä", "t"},
            {"", "f"}
        };

        Ehto e = pituusVahintaan(20);

        testaa("lukija.ehdot.PituusVahintaan", e, "Ehto ehto = new PituusVahintaan(20);", sanat);
    }

    @Points("154.3")
    @Test
    public void pituusVahintaanToimii2() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti.", "t"},
            {"eins dwei drei", "f"},
            {"Each and every day, I have less and less hope for the 2012-2013 season.", "t"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "t"},
            {"Kotimainen hunaja uhkaa loppua ennen kevättä", "f"},
            {"", "f"}
        };

        Ehto e = pituusVahintaan(45);

        testaa("lukija.ehdot.PituusVahintaan", e, "Ehto ehto = new PituusVahintaan(45);", sanat);
    }

    @Points("154.3")
    @Test
    public void pituusVahintaanToimii3() throws Throwable {
        for (int i = 5; i < 30; i++) {
            String s1 = luoSana(i - 1);
            String s2 = luoSana(i);
            String[][] sanat = {
                {s1, "f"},
                {s2, "t"}
            };

            Ehto e = pituusVahintaan(i);

            testaa("lukija.ehdot.PituusVahintaan", e, "Ehto ehto = new PituusVahintaan(" + i + ");", sanat);
        }

    }

    /*
     * 
     */
    @Points("154.4")
    @Test
    public void molemmatOlemassa() {
        String klassname = "lukija.ehdot.Molemmat";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.4")
    @Test
    public void molemmatKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.Molemmat";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "(Ehto ehto1, Ehto ehto2)",
                classRef.constructor().taking(Ehto.class, Ehto.class).isPublic());

        Ehto e1 = new SisaltaaSanan("maito");
        Ehto e2 = new SisaltaaSanan("vesi");

        String v = "virheen aiheutti koodi new " + post(klassname) + "(new SisaltaaSanan(\"maito\"),"
                + "new SisaltaaSanan(\"vesi\"));";
        classRef.constructor().taking(Ehto.class, Ehto.class).withNiceError(v).invoke(e1, e2);
    }

    private Ehto molemmat(Ehto e1, Ehto e2) throws Throwable {
        String klassname = "lukija.ehdot.Molemmat";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().taking(Ehto.class, Ehto.class).invoke(e1, e2);
    }

    @Points("154.4")
    @Test
    public void molemmatOnEhto() {
        onEhto("lukija.ehdot.Molemmat");
    }

    @Points("154.4")
    @Test
    public void molemmatToimii1() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"vesi vanhin voitehista, maito myös hyvä", "t"},
            {"vesi vanhin voitehista", "f"},
            {"maito myös hyvä", "f"},
            {"maitopoika ja vesimies", "t"},
            {"juo maitoa ja vettä", "f"},
            {"olutta sen pitää olla!", "f"},
            {"", "f"}
        };

        Ehto e1 = new SisaltaaSanan("maito");
        Ehto e2 = new SisaltaaSanan("vesi");
        Ehto e = molemmat(e1, e2);

        testaa("lukija.ehdot.Molemmat", e,
                "Ehto ehto = new Molemmat("
                + "new SisaltaaSanan(\"maito\"), "
                + "new SisaltaaSanan(\"vesi\") );", sanat);
    }

    @Points("154.4")
    @Test
    public void molemmatToimii2() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"java ja ruby ovat ohjelmointikieliä", "t"},
            {"java kehitettiin 90-luvulla", "f"},
            {"ruby kehitettiin 2000-luvulla", "f"},
            {"java on syntaksiltaan c++:n kaltainen. ruby on smalltalkihmisten mieleen", "t"},
            {"hyvä meininki", "f"},
            {"e = mc^2", "f"},
            {"", "f"}
        };

        Ehto e1 = new SisaltaaSanan("java");
        Ehto e2 = new SisaltaaSanan("ruby");
        Ehto e = molemmat(e1, e2);

        testaa("lukija.ehdot.Molemmat", e,
                "Ehto ehto = new Molemmat("
                + "new SisaltaaSanan(\"java\n), "
                + "new SisaltaaSanan(\"ruby\n) );", sanat);
    }

    /*
     * 
     */
    @Points("154.5")
    @Test
    public void ehtoEiOlemassa() {
        String klassname = "lukija.ehdot.Ei";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.5")
    @Test
    public void ehtoEiKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.Ei";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "(Ehto ehto)",
                classRef.constructor().taking(Ehto.class).isPublic());

        Ehto e = new SisaltaaSanan("maito");
        String v = "virheen aiheutti koodi new " + post(klassname) + "(new SisaltaaSanan(\"maito\"));";
        classRef.constructor().taking(Ehto.class).withNiceError(v).invoke(e);
    }

    private Ehto ei(Ehto e) throws Throwable {
        String klassname = "lukija.ehdot.Ei";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().taking(Ehto.class).invoke(e);
    }

    @Points("154.5")
    @Test
    public void ehtoEiOnEhto() {
        onEhto("lukija.ehdot.Ei");
    }

    @Points("154.5")
    @Test
    public void ehtoEiToimii1() throws Throwable {
        String[][] sanat = {
            {"testi", "t"},
            {"java ja ruby ovat ohjelmointikieliä", "f"},
            {"java kehitettiin 90-luvulla", "f"},
            {"ruby kehitettiin 2000-luvulla", "t"},
            {"java on syntaksiltaan c++:n kaltainen. ruby on smalltalkihmisten mieleen", "f"},
            {"hyvä meininki", "t"},
            {"e = mc^2", "t"},
            {"", "t"}
        };

        Ehto e = ei(new SisaltaaSanan(("java")));

        testaa("lukija.ehdot.Ei", e, "Ehto ehto = new Ei( new SisaltaaSanan(\"java\") );", sanat);
    }

    @Points("154.5")
    @Test
    public void ehtoEiToimii2() throws Throwable {
        String[][] sanat = {
            {"testi", "t"},
            {"testi!", "f"},
            {"testi?", "f"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti?", "f"},
            {"eins dwei drei!", "f"},
            {"Each and every day, I have less and less hope for the 2012-2013 season!", "f"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa!", "f"},
            {"Kotimainen hunaja uhkaa loppua ennen kevättä?", "f"},
            {"Huomaa, että ehtoja voi kombinoida mielivaltaisesti.", "t"},
            {"eins dwei drei!a", "t"},
            {"Each and every day! I have less and less hope for the 2012-2013 season.", "t"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "t"},
            {"??!?!?!?!?Kotimainen hunaja uhkaa loppua ennen kevättä", "t"},
            {"!", "f"},
            {"", "t"}
        };

        Ehto e = ei(loppuuHuutoTaiKysymysmerkkiin());

        testaa("lukija.ehdot.Ei", e, "Ehto ehto = new Ei( new LoppuuHuutoTaiKysymysmerkkiin() );", sanat);
    }

    /*
     * 
     */
    @Points("154.6")
    @Test
    public void vahintaanYksiOlemassa() {
        String klassname = "lukija.ehdot.VahintaanYksi";
        classRef = Reflex.reflect(klassname);
        assertTrue("tee pakkauseen " + pre(klassname) + " luokka " + post(klassname), classRef.isPublic());
    }

    @Points("154.6")
    @Test
    public void EhtoVahintaanYksiKonstruktori() throws Throwable {
        String klassname = "lukija.ehdot.VahintaanYksi";
        classRef = Reflex.reflect(klassname);
        assertTrue("Tee luokalle " + post(klassname) + " konstruktori public " + post(klassname) + "(Ehto... ehdot)",
                classRef.constructor().taking(Ehto[].class).isPublic());

        Ehto e1 = new SisaltaaSanan("maito");
        Ehto e2 = new SisaltaaSanan("vesi");
        Ehto e3 = new SisaltaaSanan("kahvi");

        assertEquals("Luokalla VahintaanYksi liikaa konstruktoreja", 1, classRef.cls().getConstructors().length);

        String v = "Onhan luokalla " + post(klassname) + " konstruktori public " + post(klassname) + "(Ehto... ehdot)\n";

        assertTrue(v,vaihtuvaMaaraParametrejaKonstruktorilla());
    }

    private Ehto vahintaanYksi(Ehto... ehdot) throws Throwable {
        String klassname = "lukija.ehdot.VahintaanYksi";
        classRef = Reflex.reflect(klassname);
        return (Ehto) classRef.constructor().taking(Ehto[].class).invoke(ehdot);
    }

    @Points("154.6")
    @Test
    public void vahintaanYksiOnEhto() {
        onEhto("lukija.ehdot.VahintaanYksi");
    }

    @Points("154.6")
    @Test
    public void vahintaanYksiToimii1() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"vesi vanhin voitehista, maito myös hyvä", "t"},
            {"vesi vanhin voitehista", "t"},
            {"maito myös hyvä", "t"},
            {"maitopoika ja vesimies", "t"},
            {"klara vappen", "f"},
            {"juo maitoa ja vettä", "t"},
            {"olutta sen pitää olla!", "f"},
            {"", "f"}
        };

        Ehto e1 = new SisaltaaSanan("maito");
        Ehto e2 = new SisaltaaSanan("vesi");
        Ehto e = vahintaanYksi(e1, e2);

        testaa("lukija.ehdot.VahintaanYksi", e,
                "Ehto ehto = new VahintaanYksi("
                + "new SisaltaaSanan(\"maito\"), "
                + "new SisaltaaSanan(\"vesi\") );", sanat);
    }

    @Points("154.6")
    @Test
    public void vahintaanYksiToimii2() throws Throwable {
        String[][] sanat = {
            {"testi", "f"},
            {"java ja ruby ovat ohjelmointikieliä", "t"},
            {"fortran kehitettiin 50-luvulla", "f"},
            {"ruby kehitettiin 2000-luvulla", "t"},
            {"java on syntaksiltaan c++:n kaltainen. ruby on smalltalkihmisten mieleen", "t"},
            {"hyvä meininki", "f"},
            {"Each and every day, I have less and less hope for the 2012-2013 season.", "f"},
            {"Talvivaara puhutti Ruotsin kaivosmielenosoituksessa", "f"},            
            {"luokkakaavioilla voidaan kuvata ohjelman rakennetta", "f"},
            {"ei yhtään ohjelmointikieltä mainittu", "f"},
            {"", "f"}
        };

        Ehto e1 = new SisaltaaSanan("ruby");
        Ehto e2 = new SisaltaaSanan("java");
        Ehto e3 = new SisaltaaSanan("c++");
        Ehto e = vahintaanYksi(e1, e2, e3);

        testaa("lukija.ehdot.VahintaanYksi", e,
                "Ehto ehto = new VahintaanYksi("
                + "new SisaltaaSanan(\"java\"), "
                + "new SisaltaaSanan(\"ruby\"),"
                + "new SisaltaaSanan(\"c++\") );", sanat);
    }

    /*
     * 
     */

    private String pre(String klassname) {
        int kohta = klassname.lastIndexOf(".");
        return klassname.substring(0, kohta);
    }

    private String post(String klassname) {
        int kohta = klassname.lastIndexOf(".");
        return klassname.substring(kohta + 1);
    }

    private void onEhto(String klassname) {
        classRef = Reflex.reflect(klassname);
        Class clazz = classRef.cls();

        boolean toteuttaaRajapinnan = false;
        Class ehto = Ehto.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(ehto)) {
                toteuttaaRajapinnan = true;
            }
        }
        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka " + post(klassname) + " rajapinnan Ehto?");
        }
    }

    private boolean toteutuu(String klassname, Ehto e, String rivi, String v) throws Throwable {
        classRef = Reflex.reflect(klassname);
        return classRef.method(e, "toteutuu").returning(boolean.class).taking(String.class).withNiceError(v).invoke(rivi);

    }

    private void testaa(String klassname, Ehto e, String v, String[][] rivit) throws Throwable {
        for (String[] rivi : rivit) {
            boolean odotettu = rivi[1].equals("t") ? true : false;
            String f = v + "\nehto.toteutuu(\"" + rivi[0] + "\");\n";
            assertEquals(f, odotettu, toteutuu(klassname, e, rivi[0], "\nVirhe tapahtui koodilla:\n" + f));
        }
    }

    private String luoSana(int pit) {
        String s = "";
        for (int i = 0; i < pit; i++) {
            s += "a";
        }
        return s;
    }

    private boolean vaihtuvaMaaraParametrejaKonstruktorilla() {

        boolean ok = false;
        try {
            Scanner lukija = new Scanner(new File("src/lukija/ehdot/VahintaanYksi.java"));
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();

                if (rivi.indexOf("//") > -1) {
                    rivi = rivi.substring(0, rivi.indexOf("//"));
                }

                if (rivi.contains("Ehto... ") && rivi.contains("VahintaanYksi")) {
                    ok = true;
                    break;
                }

            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
        return ok;
    }
}

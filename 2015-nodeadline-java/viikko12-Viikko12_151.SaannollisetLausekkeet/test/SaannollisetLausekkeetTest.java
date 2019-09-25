
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class SaannollisetLausekkeetTest {

    String klassName = "Paaohjelma";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Points("151.1")
    @Test
    public void onMetodiOnViikonpaiva() {
        String metodi = "onViikonpaiva";
        assertTrue("tee luokkaan Paaohjelma metodi public static boolean onViikonpaiva(String merkkijono)", klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("151.1")
    @Test
    public void eiKiellettyjaKomentoja() {
        noForbiddens();
    }

    @Points("151.1")
    @Test
    public void onViikonpaivaHyvaksyy() throws Throwable {
        String[] mj = {"ma", "ti", "ke", "to", "pe", "la", "su"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: "
                    + "onViikonpaiva(\"" + pv + "\")\n";
            assertEquals(v, true, onViikonpaiva(pv, v));
        }

    }

    @Points("151.1")
    @Test
    public void onViikonpaivaHylkaa() throws Throwable {
        String[] mj = {"m", "maa", "maanantai", "", "titi", "arto", "koe", "ma "};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: onViikonpaiva(\"" + pv + "\")\n";
            assertEquals(v, false, onViikonpaiva(pv, v));
        }
    }

    @Points("151.2")
    @Test
    public void onMetodikaikkiVokaaleja() {
        String virhe = "tee luokkaan Paaohjelma metodi public static boolean kaikkiVokaaleja(String merkkijono)";
        String metodi = "kaikkiVokaaleja";
        assertTrue(virhe, klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("151.2")
    @Test
    public void hyvaksyyVokaaleistaKoostuvat() throws Throwable {
        String[] mj = {"a", "aeiouäö", "aaa", "uiuiui", "uaa", "aaai", "ai"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: kaikkiVokaaleja(\"" + pv + "\")\n";
            assertEquals(v, true, kaikkiVokaaleja(pv, v));
        }

    }

    @Points("151.2")
    @Test
    public void hylkaaJosEiVokaaleitaJoukossa() throws Throwable {
        String[] mj = {"fågel", "aaaab", "waeiou", "x", "aaaaaaqaaaaaaaaa", "ala"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: onViikonpaiva(\"" + pv + "\")\n";
            assertEquals(v, false, kaikkiVokaaleja(pv, v));
        }
    }

    @Points("151.2")
    @Test
    public void eiKiellettyjaKomentoja2() {
        noForbiddens();
    }

    @Points("151.3")
    @Test
    public void onMetodiKellonaika() {
        String virhe = "tee luokkaan Paaohjelma metodi public static boolean kellonaika(String merkkijono)";
        String metodi = "kellonaika";
        assertTrue(virhe, klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    private boolean kellonaika(String mj, String v) throws Throwable {
        String metodi = "kellonaika";
        return klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke(mj);
    }

    private boolean onViikonpaiva(String mj, String v) throws Throwable {
        String metodi = "onViikonpaiva";
        return klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke(mj);
    }

    @Points("151.3")
    @Test
    public void kellonaikaHyvaksyyOikeat() throws Throwable {
        String[] mj = {"20:00:00", "11:24:00", "04:59:31", "14:41:16", "23:32:23", "20:00:59"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: kellonaika(\"" + pv + "\")\n";
            assertEquals(v, true, kellonaika(pv, v));
        }

    }

    @Points("151.3")
    @Test
    public void kellonaikaHylkaaVaarat() throws Throwable {
        String[] mj = {"a", "aaaaaaa", "3:59:31", "24:41:16", "23:61:23", "20:00:79",
            "13:9:31", "21:41:6", "23,61:23", "20:00;79"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: kellonaika(\"" + pv + "\")\n";
            assertEquals(v, false, kellonaika(pv, v));
        }

    }

    private boolean kaikkiVokaaleja(String m, String v) throws Throwable {
        String metodi = "kaikkiVokaaleja";

        return klass.staticMethod(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke(m);
    }

    private void noForbiddens() {
        try {
            Scanner lukija = new Scanner(new File("src/Paaohjelma.java"));
            int mainissa = 0;
            while (lukija.hasNext()) {

                String virhe = "Koska harjoittelemme String.match-komennon käyttöä, älä käytä komentoa ";

                String rivi = lukija.nextLine();

                if (rivi.contains("void main(") || rivi.contains("boolean kellonaika(")) {
                    mainissa++;
                } else if (mainissa > 0) {

                    if (rivi.contains("{") && !rivi.contains("}")) {
                        mainissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        mainissa--;
                    }
                    continue;
                }

                if (mainissa > 0) {
                    continue;
                }

                String f = "equals";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "charAt";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "indexOf";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "contains";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "substring";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

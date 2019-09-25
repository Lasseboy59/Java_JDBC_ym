
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("155")
public class StringBuilderTest {

    Reflex.ClassRef<Object> classRef;

    @Before
    public void setUp() {
        classRef = Reflex.reflect("Main");
    }

    @Test
    public void muotoiluToimii() throws Throwable {
        int[][] tt = {
            {1, 2, 3, 4},
            {1, 2, 3},
            {1, 2},
            {1, 2, 3, 4, 5},
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}
        };
        String[] vv = {""
            + "{\n"
            + " 1, 2, 3, 4\n"
            + "}",
            ""
            + "{\n"
            + " 1, 2, 3\n"
            + "}",
            ""
            + "{\n"
            + " 1, 2\n"
            + "}",
            ""
            + "{\n"
            + " 1, 2, 3, 4,\n"
            + " 5\n"
            + "}",
            ""
            + "{\n"
            + " 1, 2, 3, 4,\n"
            + " 5, 6, 7, 8,\n"
            + " 9\n"
            + "}",
            ""
            + "{\n"
            + " 15, 14, 13, 12,\n"
            + " 11, 10, 9, 8,\n"
            + " 7, 6, 5, 4,\n"
            + " 3, 2, 1\n"
            + "}"};

        for (int i = 0; i < vv.length; i++) {
            testaa(vv[i].split("\n"), tt[i]);
        }
    }

    @Test
    public void kayttaaStringBilderia() throws Throwable {
        int[] t = new int[20000];
        long aika = System.currentTimeMillis();
        muotoile(t, "int{[ t = //taulukon koko "+t.length+"\n"
                + "muotoile(t);");
        aika = System.currentTimeMillis() - aika;
        assertTrue("int{[ t = //taulukon koko "+t.length+"\n"
                + "muotoile(t);\n"
                + "aikaa kului "+aika+" millisekuntia, se on liikaa\n"
                + "et käyttänyt StringBuilderia tai teit joitan muuta liikaa aikaavievää!",aika<500);
    }

    private String muotoile(int[] t, String v) throws Throwable {
        return classRef.staticMethod("muotoile").returning(String.class).taking(int[].class).withNiceError(v).invoke(t);
    }

    private void testaa(String[] odotettu, int[] t) throws Throwable {
        String v = "int[] t = " + Arrays.toString(t).replace('[', '{').replace(']', '}')
                + "\nmuotoile(t);\n";
        String tt = muotoile(t, v);
        String[] tulos = tt.split("\n");
        assertEquals("väärä määrä rivejä\n" + v + "tulos:\n" + tt + "\n", odotettu.length, tulos.length);
        assertEquals("viimeisellä rivillä pitäisi olla vain }\n" + v + "tulos:\n" + tt + "\n", "}", tulos[tulos.length - 1].replaceAll(" ", ""));
        assertEquals("ensimmäisellä rivillä pitäisi olla vain {\n" + v + "tulos:\n" + tt + "\n", "{", tulos[0].replaceAll(" ", ""));
        for (int i = 1; i < tulos.length - 1; i++) {
            String pitas = odotettu[i].replaceAll("\n", "");
            assertTrue((i + 1) + ":llä rivillä pitäisi olla alussa välilyönti\n" + v + "tulos:\n" + tt + "\n", tulos[i].startsWith(" "));
            assertEquals((i + 1) + ":llä rivillä pilkkujen määrä väärä\n" + v + "tulos:\n" + tt + "\n", pilkut(pitas), pilkut(tulos[i]));
            assertTrue((i + 1) + ":llä rivillä pitäisi olla " + pitas + "\n" + v + "tulos:\n" + tt + "\n", tulos[i].startsWith(pitas));
            assertTrue(tulos[i]+ "pitaisi olla "+pitas , tulos[i].replaceAll(" ", "").equals(pitas.replaceAll(" ", "")));
        }

        assertEquals("viimeisellä rivillä pitäisi olla vain }\n" + v + "tulos:\n" + tt + "\n", "}", tulos[tulos.length - 1].replaceAll(" ", ""));
    }

    private int pilkut(String rivi) {
        int p = 0;
        for (int i = 0; i < rivi.length(); i++) {
            if (rivi.charAt(i) == ',') {
                p++;
            }
        }
        return p;
    }
}

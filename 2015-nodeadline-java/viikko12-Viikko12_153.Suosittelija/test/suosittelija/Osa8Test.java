package suosittelija;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef3;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class Osa8Test  {

    String suosittelijaLuokka = "Suosittelija";
    Class suosittelijaClass;
    String comparatorPakkaus = "suosittelija.comparator";
    String elokuvaComparatorLuokka = "ElokuvaComparator";
    Class elokuvaComparatorClass;
    String henkiloComparatorLuokka = "HenkiloComparator";
    Class henkiloComparatorClass;
    String pakkaus = "suosittelija";
    String arvioRekisteriLuokka = "ArvioRekisteri";
    Class arvioRekisteriClass;
    String domainPakkaus = "suosittelija.domain";
    String henkiloLuokka = "Henkilo";
    Class henkiloClass;
    String elokuvaLuokka = "Elokuva";
    Class elokuvaClass;
    String arvioLuokka = "Arvio";
    Class arvioClass;
    
    @Before
    public void setUp() {
        try {
            henkiloClass = ReflectionUtils.findClass(domainPakkaus + "." + henkiloLuokka);
        } catch (Throwable t) {
        }

        try {
            elokuvaClass = ReflectionUtils.findClass(domainPakkaus + "." + elokuvaLuokka);
        } catch (Throwable t) {
        }

        try {
            arvioClass = ReflectionUtils.findClass(domainPakkaus + "." + arvioLuokka);
        } catch (Throwable t) {
        }
        try {
            arvioRekisteriClass = ReflectionUtils.findClass(pakkaus + "." + arvioRekisteriLuokka);
        } catch (Throwable t) {
        }
        try {
            elokuvaComparatorClass = ReflectionUtils.findClass(comparatorPakkaus + "." + elokuvaComparatorLuokka);
        } catch (Throwable t) {
        }

        try {
            henkiloComparatorClass = ReflectionUtils.findClass(comparatorPakkaus + "." + henkiloComparatorLuokka);
        } catch (Throwable t) {
        }
        try {
            suosittelijaClass = ReflectionUtils.findClass(pakkaus + "." + suosittelijaLuokka);
        } catch (Throwable t) {
        }
    }

    @Test

    public void onSuosittelija() {
    }

    @Test
    @Points(Tehtava.ID + ".8")
    public void Osa8_suositteleeSopivintaElokuvaa() throws Throwable {
        onSuosittelija();

        Object rekisteri = Reflex.reflect(arvioRekisteriClass).constructor().takingNoParams().invoke();

        Object leffa1 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("eka");
        Object leffa2 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("toka");
        Object leffa3 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("kolmas");
        Object leffa4 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("neljas");

        Object pekka = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Pekka");
        Object mikke = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Mikke");
        Object antti = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Antti");

        Object arvioOk = Enum.valueOf(arvioClass, "OK");
        Object arvioHuono = Enum.valueOf(arvioClass, "HUONO");

        MethodRef3 ref = Reflex.reflect(arvioRekisteriClass).method("lisaaArvio").returningVoid().taking(henkiloClass, elokuvaClass, arvioClass);
        ref.invokeOn(rekisteri, pekka, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa2, arvioOk);
        ref.invokeOn(rekisteri, pekka, leffa3, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, antti, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, antti, leffa3, arvioHuono);
        ref.invokeOn(rekisteri, antti, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, mikke, leffa1, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa3, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa4, arvioOk);

        Object suosittelija = Reflex.reflect(suosittelijaClass).constructor().taking(arvioRekisteriClass).invoke(rekisteri);
        Object suositus = Reflex.reflect(suosittelijaClass).method("suositteleElokuva").returning(elokuvaClass).taking(henkiloClass).invokeOn(suosittelija, antti);
        if (suositus == null) {
            fail("Suosituksen ei pitäisi olla null jos suositeltavaa on.");
        }


        Object leffanNimi = Reflex.reflect(elokuvaClass).method("getNimi").returning(String.class).takingNoParams().invokeOn(suositus);
        assertEquals("Arvioijan tulee palauttaa henkilölle parhaiten sopiva hänen vielä katsomaton elokuva, nyt ei palauttanut.", "toka", leffanNimi);
    }

    @Test
    @Points(Tehtava.ID + ".8")
    public void Osa8_suositteleeSopivintaElokuvaaPalauttaaNullJosEiVaihtoehtoja() throws Throwable {
        onSuosittelija();

        Object rekisteri = Reflex.reflect(arvioRekisteriClass).constructor().takingNoParams().invoke();

        Object leffa1 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("eka");
        Object leffa2 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("toka");
        Object leffa3 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("kolmas");
        Object leffa4 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("neljas");

        Object pekka = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Pekka");
        Object mikke = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Mikke");
        Object antti = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Antti");

        Object arvioOk = Enum.valueOf(arvioClass, "OK");
        Object arvioHuono = Enum.valueOf(arvioClass, "HUONO");

        MethodRef3 ref = Reflex.reflect(arvioRekisteriClass).method("lisaaArvio").returningVoid().taking(henkiloClass, elokuvaClass, arvioClass);
        ref.invokeOn(rekisteri, pekka, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa2, arvioOk);
        ref.invokeOn(rekisteri, pekka, leffa3, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, antti, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, antti, leffa3, arvioHuono);
        ref.invokeOn(rekisteri, antti, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, mikke, leffa1, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa2, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa3, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa4, arvioOk);

        Object suosittelija = Reflex.reflect(suosittelijaClass).constructor().taking(arvioRekisteriClass).invoke(rekisteri);
        Object suositus = Reflex.reflect(suosittelijaClass).method("suositteleElokuva").returning(elokuvaClass).taking(henkiloClass).invokeOn(suosittelija, mikke);
        assertNull("Jos henkilöllä ei ole katsomattomia elokuvia, tulee suosittelijan palauttaa null.", suositus);
    }

    @Test
    @Points(Tehtava.ID + ".8")
    public void Osa8_suositteleeSopivintaElokuvaaKaksi() throws Throwable {
        onSuosittelija();

        Object rekisteri = Reflex.reflect(arvioRekisteriClass).constructor().takingNoParams().invoke();

        Object leffa1 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("eka1");
        Object leffa2 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("toka1");
        Object leffa3 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("kolmas1");
        Object leffa4 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("neljas1");

        Object pekka = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Pekka");
        Object mikke = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Mikke");
        Object antti = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Antti");

        Object arvioOk = Enum.valueOf(arvioClass, "OK");
        Object arvioHyva = Enum.valueOf(arvioClass, "HYVA");
        Object arvioHuono = Enum.valueOf(arvioClass, "HUONO");

        MethodRef3 ref = Reflex.reflect(arvioRekisteriClass).method("lisaaArvio").returningVoid().taking(henkiloClass, elokuvaClass, arvioClass);
        ref.invokeOn(rekisteri, pekka, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa2, arvioOk);
        ref.invokeOn(rekisteri, pekka, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, antti, leffa3, arvioHyva);
        ref.invokeOn(rekisteri, antti, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, mikke, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, mikke, leffa2, arvioOk);
        ref.invokeOn(rekisteri, mikke, leffa3, arvioHyva);
        ref.invokeOn(rekisteri, mikke, leffa4, arvioHyva);

        Object suosittelija = Reflex.reflect(suosittelijaClass).constructor().taking(arvioRekisteriClass).invoke(rekisteri);
        Object suositus = Reflex.reflect(suosittelijaClass).method("suositteleElokuva").returning(elokuvaClass).taking(henkiloClass).invokeOn(suosittelija, antti);

        if (suositus == null) {
            fail("Suosituksen ei pitäisi olla null jos suositeltavaa on.");
        }

        Object leffanNimi = Reflex.reflect(elokuvaClass).method("getNimi").returning(String.class).takingNoParams().invokeOn(suositus);
        assertEquals("Arvioijan tulee palauttaa henkilölle parhaiten sopiva hänen vielä katsomaton elokuva, nyt ei palauttanut.", "toka1", leffanNimi);
    }

    @Test
    @Points(Tehtava.ID + ".8")
    public void Osa8_suositteleeSopivintaElokuvaaKolme() throws Throwable {
        onSuosittelija();

        Object rekisteri = Reflex.reflect(arvioRekisteriClass).constructor().takingNoParams().invoke();

        Object leffa1 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("eka1");
        Object leffa2 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("toka1");
        Object leffa3 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("kolmas1");
        Object leffa4 = Reflex.reflect(elokuvaClass).constructor().taking(String.class).invoke("neljas1");

        Object pekka = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Pekka");
        Object mikke = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Mikke");
        Object antti = Reflex.reflect(henkiloClass).constructor().taking(String.class).invoke("Antti");

        Object arvioOk = Enum.valueOf(arvioClass, "OK");
        Object arvioHyva = Enum.valueOf(arvioClass, "HYVA");
        Object arvioHuono = Enum.valueOf(arvioClass, "HUONO");

        MethodRef3 ref = Reflex.reflect(arvioRekisteriClass).method("lisaaArvio").returningVoid().taking(henkiloClass, elokuvaClass, arvioClass);
        ref.invokeOn(rekisteri, pekka, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, pekka, leffa2, arvioOk);
        ref.invokeOn(rekisteri, pekka, leffa3, arvioOk);
        ref.invokeOn(rekisteri, pekka, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, antti, leffa3, arvioHyva);
        ref.invokeOn(rekisteri, antti, leffa4, arvioHuono);

        ref.invokeOn(rekisteri, mikke, leffa1, arvioHuono);
        ref.invokeOn(rekisteri, mikke, leffa2, arvioOk);

        Object suosittelija = Reflex.reflect(suosittelijaClass).constructor().taking(arvioRekisteriClass).invoke(rekisteri);
        Object suositus = Reflex.reflect(suosittelijaClass).method("suositteleElokuva").returning(elokuvaClass).taking(henkiloClass).invokeOn(suosittelija, mikke);


        if (suositus == null) {
            fail("Suosituksen ei pitäisi olla null jos suositeltavaa on.");
        }

        Object leffanNimi = Reflex.reflect(elokuvaClass).method("getNimi").returning(String.class).takingNoParams().invokeOn(suositus);
        assertEquals("Arvioijan tulee palauttaa henkilölle parhaiten sopiva hänen vielä katsomaton elokuva, nyt ei palauttanut.", "kolmas1", leffanNimi);
    }

}

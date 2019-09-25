package suosittelija;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Osa1_7Test<_Henkilo, _Elokuva, _Arvio, _Rekisteri, _HComp, _EComp, _Suosittelija> {

    Reflex.ClassRef<_Arvio> _ArvioRef;
    Reflex.ClassRef<_Henkilo> _HenkiloRef;
    Reflex.ClassRef<_Elokuva> _ElokuvaRef;
    Reflex.ClassRef<_Rekisteri> _RekisteriRef;
    Reflex.ClassRef<_Suosittelija> _SuosittelijaRef;
    String henkiloLuokka = "suosittelija.domain.Henkilo";
    String elokuvaLuokka = "suosittelija.domain.Elokuva";
    String arvioLuokka = "suosittelija.domain.Arvio";
    String rekisteriLuokka = "suosittelija.ArvioRekisteri";
    String suosittelijaLuokka = "suosittelija.Suosittelija";
    String elokuvaComparatorLuokka = "suosittelija.comparator.ElokuvaComparator";
    String henkiloComparatorLuokka = "suosittelija.comparator.HenkiloComparator";

    @Before
    public void setUp() {
        try {
            _HenkiloRef = Reflex.reflect(henkiloLuokka);
            _ElokuvaRef = Reflex.reflect(elokuvaLuokka);
            _ArvioRef = Reflex.reflect(arvioLuokka);
            _RekisteriRef = Reflex.reflect(rekisteriLuokka);
            _SuosittelijaRef = Reflex.reflect(suosittelijaLuokka);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void onLuokkaHenkilo() {
        String luokanNimi = henkiloLuokka;
        _HenkiloRef = Reflex.reflect(luokanNimi);
        assertTrue("tee pakkauseen suosittelija.domain luokka Henkilo", _HenkiloRef.isPublic());
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void henkilollaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle Henkilo konstruktori public Henkilo(String nimi)",
                _HenkiloRef.constructor().taking(String.class).isPublic());

        String luokanNimi = henkiloLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);


        assertEquals("Luokalla Henkilo tulee olla vain yksi konstruktori, nyt konstruktoreja on", 1, c.getConstructors().length);

        _HenkiloRef.constructor().taking(String.class).withNiceError("\nVirheeseen johtanut koodi new Henkilo(\"Arto\"); ").invoke("arto");
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void eiYlimaaraisiaMuuttujiaHenkilolla() {
        saniteettitarkastus(henkiloLuokka, 1, "nimen tallettavan oliomuuttujan");
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void getNimiHenkilo() throws Throwable {
        String metodi = "getNimi";
        String virhe = "tee luokalle Henkilo metodi public String getNimi()";

        assertTrue(virhe, _HenkiloRef.method(metodi).returning(String.class).takingNoParams().isPublic());

        String v = ""
                + "Henkilo h = new Henkilo(\"Pekka\");\n"
                + "h.getNimi()\n";
        _Henkilo h = newHenkilo("Pekka");
        assertEquals(v, "Pekka", _HenkiloRef.method(metodi).returning(String.class).takingNoParams().withNiceError(v).invokeOn(h));

        v = ""
                + "Henkilo h = new Henkilo(\"Mikko\");}n"
                + "h.getNimi()\n";
        h = newHenkilo("Mikko");
        assertEquals(v, "Mikko", _HenkiloRef.method(metodi).returning(String.class).takingNoParams().withNiceError(v).invokeOn(h));
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void henkilonEqualsJaHashCode() throws Throwable {
        _Henkilo h1 = newHenkilo("Pekka");
        _Henkilo h2 = newHenkilo("Jukka");
        _Henkilo h3 = newHenkilo("Pekka");

        String v = "Ylikirjoita luokan Henkilo metodi equals(Object);\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Jukka\");\n"
                + "Henkilo h3 = new Henkilo(\"Pekka\");\n"
                + "h1.equals(h2);\n";

        assertEquals(v, false, _HenkiloRef.method(h1, "equals").returning(boolean.class).taking(Object.class).withNiceError(v).invoke(h2));

        v = "Ylikirjoita luokan Henkilo metodi equals(Object);\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Jukka\");\n"
                + "Henkilo h3 = new Henkilo(\"Pekka\");\n"
                + "h1.equals(h3);\n";

        assertEquals(v, true, _HenkiloRef.method(h1, "equals").returning(boolean.class).taking(Object.class).withNiceError(v).invoke(h3));

        v = ""
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Jukka\");\n"
                + "Henkilo h3 = new Henkilo(\"Pekka\");\n"
                + "h1.hashCode() == h3.hashCode();\n";
        assertEquals(v, true, h1.hashCode() == h3.hashCode());

        v = ""
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Jukka\");\n"
                + "Henkilo h3 = new Henkilo(\"Pekka\");\n"
                + "h1.hashCode() == h2.hashCode();\n";
        assertEquals(v, false, h1.hashCode() == h2.hashCode());
    }

    /*
     * 
     */
    @Test
    @Points(Tehtava.ID + ".1")
    public void onLuokkaElokuva() {
        String luokanNimi = elokuvaLuokka;
        _ElokuvaRef = Reflex.reflect(luokanNimi);
        assertTrue("tee pakkauseen suosittelija.domain luokka Elokuva", _ElokuvaRef.isPublic());
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void elokuvallaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle Elokuva konstruktori public Elokuva(String nimi)",
                _ElokuvaRef.constructor().taking(String.class).isPublic());

        String luokanNimi = elokuvaLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);


        assertEquals("Luokalla Elokuva tulee olla vain yksi konstruktori, nyt konstruktoreja on", 1, c.getConstructors().length);

        _ElokuvaRef.constructor().taking(String.class).withNiceError("\nVirheeseen johtanut koodi new Elokuva(\"Rambo\"); ").invoke("Rambo");
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void eiYlimaaraisiaMuuttujiaElokuvalla() {
        saniteettitarkastus(elokuvaLuokka, 1, "nimen tallettavan oliomuuttujan");
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void getNimiElokuva() throws Throwable {
        String metodi = "getNimi";
        String virhe = "tee luokalle Elokuva metodi public String getNimi()";

        assertTrue(virhe, _ElokuvaRef.method(metodi).returning(String.class).takingNoParams().isPublic());

        String v = ""
                + "Elokuva e = new Elokuva(\"Rambo\");\n"
                + "e.getNimi()\n";
        _Elokuva h = newElokuva("Rambo");
        assertEquals(v, "Rambo", _ElokuvaRef.method(metodi).returning(String.class).takingNoParams().withNiceError(v).invokeOn(h));

        v = ""
                + "Elokuva e = new Elokuva(\"Blue Velvet\");}n"
                + "e.getNimi()\n";
        h = newElokuva("Blue Velvet");
        assertEquals(v, "Blue Velvet", _ElokuvaRef.method(metodi).returning(String.class).takingNoParams().withNiceError(v).invokeOn(h));
    }

    @Test
    @Points(Tehtava.ID + ".1")
    public void elokuvanEqualsJaHashCode() throws Throwable {
        _Elokuva h1 = newElokuva("Rambo");
        _Elokuva h2 = newElokuva("Commando");
        _Elokuva h3 = newElokuva("Rambo");

        String v = "Ylikirjoita luokan Elokuva metodi equals(Object o)\n"
                + "Elokuva e1 = new Elokuva(\"Rambo\");\n"
                + "Elokuva e1  = new Elokuva(\"Commando\");\n"
                + "Elokuva e1  = new Elokuva(\"Rambo\");\n"
                + "e1.equals(e2);\n";

        assertEquals(v, false, _ElokuvaRef.method(h1, "equals").returning(boolean.class).taking(Object.class).withNiceError(v).invoke(h2));

        v = "Ylikirjoita luokan Elokuva metodi equals(Object o)\n"
                + "Elokuva e1 = new Elokuva(\"Rambo\");\n"
                + "Elokuva e1  = new Elokuva(\"Commando\");\n"
                + "Elokuva e1  = new Elokuva(\"Rambo\");\n"
                + "e1.equals(e3);\n";
        assertEquals(v, true, _ElokuvaRef.method(h1, "equals").returning(boolean.class).taking(Object.class).withNiceError(v).invoke(h3));

        v = ""
                + "Elokuva e1 = new Elokuva(\"Rambo\");\n"
                + "Elokuva e1  = new Elokuva(\"Commando\");\n"
                + "Elokuva e1  = new Elokuva(\"Rambo\");\n"
                + "e1.hashCode() == e3.hashCode();\n";
        assertEquals(v, true, h1.hashCode() == h3.hashCode());

        v = ""
                + "Elokuva e1 = new Elokuva(\"Rambo\");\n"
                + "Elokuva e1  = new Elokuva(\"Commando\");\n"
                + "Elokuva e1  = new Elokuva(\"Rambo\");\n"
                + "e1.hashCode() == e2.hashCode();\n";
        assertEquals(v, false, h1.hashCode() == h2.hashCode());
    }

    /*
     * 
     */
    @Points(Tehtava.ID + ".2")
    @Test
    public void onEnumArvio() {
        String luokanNimi = arvioLuokka;
        try {
            _ArvioRef = Reflex.reflect(luokanNimi);
        } catch (Throwable t) {
            fail("tee pakkauseen suosittelija.domain enum Arvio");
        }
        assertTrue("tee pakkauseen suosittelija.domain enum Arvio", _ArvioRef.isPublic());
        Class c = _ArvioRef.cls();
        assertTrue("tee pakkauseen suosittelija.domain enum Koulutus, nyt taisit tehdä normaalin luokan", c.isEnum());
    }

    @Points(Tehtava.ID + ".2")
    @Test
    public void enumillaOikeatTunnukset() {
        String luokanNimi = arvioLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);
        Object[] vakiot = c.getEnumConstants();
        assertEquals("enumin Arvio tulisi määritellä oikea määrä tunnuksia", 6, vakiot.length);

        String[] t = {"HUONO", "VALTTAVA", "EI_NAHNYT", "NEUTRAALI", "OK", "HYVA"};

        for (String tunnus : t) {
            assertTrue("Enumin Arvio tulisi määritellä tunnus " + tunnus, sis(tunnus, vakiot));
        }
    }

    @Points(Tehtava.ID + ".2")
    @Test
    public void enumillametodiGetArvo() {
        String metodi = "getArvo";
        String virhe = "tee enumille Arvio metodi public int getArvo()";

        assertTrue(virhe, _ArvioRef.method(metodi).returning(int.class).takingNoParams().isPublic());
    }

    @Points(Tehtava.ID + ".2")
    @Test
    public void enumillaOikeatArvot() throws Throwable {
        String luokanNimi = arvioLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);
        Object[] vakiot = c.getEnumConstants();

        for (Object object : vakiot) {
            int arvo = _ArvioRef.method("getArvo").returning(int.class).takingNoParams().invokeOn((_Arvio) object);

            String tunnus = object.toString();

            if (tunnus.equals("HUONO")) {
                assertEquals("Arvio arvio = Arvio.HUONO;\n arvio.getArvo();\n", -5, arvo);
            } else if (tunnus.equals("VALTTAVA")) {
                assertEquals("Arvio arvio = Arvio.VALTTAVA;\n arvio.getArvo();\n", -3, arvo);
            } else if (tunnus.equals("EI_NAHNYT")) {
                assertEquals("Arvio arvio = Arvio.EI_NAHNYT;\n arvio.getArvo();\n", 0, arvo);
            } else if (tunnus.equals("NEUTRAALI")) {
                assertEquals("Arvio arvio = Arvio.NEUTRAALI;\n arvio.getArvo();\n", 1, arvo);
            } else if (tunnus.equals("OK")) {
                assertEquals("Arvio arvio = Arvio.OK;\n arvio.getArvo();\n", 3, arvo);
            } else if (tunnus.equals("HYVA")) {
                assertEquals("Arvio arvio = Arvio.HYVA;\n arvio.getArvo();\n", 5, arvo);
            }
        }

    }

    /*
     * 
     */
    @Test
    @Points(Tehtava.ID + ".3")
    public void onLuokkaArvioRekisteri() {
        _ArvioRef = Reflex.reflect(rekisteriLuokka);
        assertTrue("tee pakkauseen suosittelija luokka ArvioRekisteri", _RekisteriRef.isPublic());
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void arviorekisterillaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle ArvioRekisteri konstruktori public ArvioRekisteri()",
                _RekisteriRef.constructor().takingNoParams().isPublic());

        String luokanNimi = rekisteriLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);


        assertEquals("Luokalla ArvioRekisteri tulee olla vain yksi konstruktori, nyt konstruktoreja on", 1, c.getConstructors().length);

        _RekisteriRef.constructor().takingNoParams().withNiceError("\n"
                + "Virheeseen johtanut koodi new ArvioRekisteri();\n").invoke();
    }

    @Points(Tehtava.ID + ".3")
    public void eiYlimaaraisiaMuuttujiaArvioRekisterilla() {
        saniteettitarkastus(rekisteriLuokka, 4, "muita oliomuuttujia kuin henkilön arviot sekä elokuvan arviot sisältävät Map-rakenteet");
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void lisaaArvioArvioRekisteri() throws Throwable {
        String metodi = "lisaaArvio";
        String virhe = "tee luokalle ArvioRekisteri metodi public void lisaaArvio(Elokuva elokuva, Arvio arvio)";

        assertTrue(virhe, _RekisteriRef.method(metodi).returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Elokuva(\"Rambo\"), Arvio.HUONO);\n";
        _Elokuva e = newElokuva("Rambo");
        _Rekisteri r = newRekisteri();
        _RekisteriRef.method(r, "lisaaArvio").returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(e, Arvio("HUONO"));
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void annaArviotArvioRekisteri() throws Throwable {
        String metodi = "annaArviot";
        String virhe = "tee luokalle ArvioRekisteri metodi public List<Arvio> annaArviot(Elokuva elokuva)";

        assertTrue(virhe, _RekisteriRef.method(metodi).returning(List.class).taking(_ElokuvaRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Elokuva(\"Rambo\"), Arvio.HUONO);\n"
                + "rek.annaArviot(new Elokuva(\"Rambo\"));\n";
        _Elokuva e = newElokuva("Rambo");
        _Rekisteri rek = newRekisteri();
        _RekisteriRef.method(rek, "lisaaArvio").returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(e, Arvio("HUONO"));

        _RekisteriRef.method(rek, metodi).returning(List.class).taking(_ElokuvaRef.cls()).withNiceError(v).invoke(e);
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void arviotRekisteroityvatElokuvalle() throws Throwable {
        _Rekisteri rek = newRekisteri();

        String v = ""
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.OK);\n"
                + "rek.lisaaArvio(new Elokuva(\"Blue Velvet\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.EI_NAHNYT);\n"
                + "rek.lisaaArvio(new Elokuva(\"Blue Velvet\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(new Elokuva(\"Rambo\"), Arvio.VALTTAVA);\n";
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("OK"), v);
        lisaaArvio(rek, newElokuva("Blue Velvet"), Arvio("HYVA"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("NEUTRAALI"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("HUONO"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("EI_NAHNYT"), v);
        lisaaArvio(rek, newElokuva("Blue Velvet"), Arvio("HYVA"), v);
        lisaaArvio(rek, newElokuva("Rambo"), Arvio("VALTTAVA"), v);

        String w = v + ""
                + "rek.annaArviot(new Elokuva(\"Eraserhead\"));\n";

        List er = annaArviot(rek, newElokuva("Eraserhead"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        List exp = new ArrayList();
        exp.add(Arvio("OK"));
        exp.add(Arvio("NEUTRAALI"));
        exp.add(Arvio("HUONO"));
        exp.add(Arvio("EI_NAHNYT"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "rek.annaArviot(new Elokuva(\"Blue Velvet\"));\n";

        er = annaArviot(rek, newElokuva("Blue Velvet"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("HYVA"));
        exp.add(Arvio("HYVA"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "rek.annaArviot(new Elokuva(\"Rambo\"));\n";

        er = annaArviot(rek, newElokuva("Rambo"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("VALTTAVA"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void elokuvienArviotArvioRekisteri() throws Throwable {
        String metodi = "elokuvienArviot";
        String virhe = "tee luokalle ArvioRekisteri metodi "
                + "public Map<Elokuva, List<Arvio>> elokuvienArviot()";

        assertTrue(virhe, _RekisteriRef.method(metodi).returning(Map.class).takingNoParams().isPublic());

        _Rekisteri rek = newRekisteri();

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Elokuva(\"Rambo\"), Arvio.HUONO);\n"
                + "rek.elokuvienArviot();\n";
        _Elokuva e = newElokuva("Rambo");
        _RekisteriRef.method(rek, "lisaaArvio").returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(e, Arvio("HUONO"));

        _RekisteriRef.method(rek, metodi).returning(Map.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points(Tehtava.ID + ".3")
    public void elokuvienArviotPalautetaan() throws Throwable {
        _Rekisteri rek = newRekisteri();

        String v = ""
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.OK);\n"
                + "rek.lisaaArvio(new Elokuva(\"Blue Velvet\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(new Elokuva(\"Eraserhead\"), Arvio.EI_NAHNYT);\n"
                + "rek.lisaaArvio(new Elokuva(\"Blue Velvet\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(new Elokuva(\"Rambo\"), Arvio.VALTTAVA);\n"
                + "Map<Elokuva, List<Arvio>> arviot = rek.elokuvienArviot();\n";
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("OK"), v);
        lisaaArvio(rek, newElokuva("Blue Velvet"), Arvio("HYVA"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("NEUTRAALI"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("HUONO"), v);
        lisaaArvio(rek, newElokuva("Eraserhead"), Arvio("EI_NAHNYT"), v);
        lisaaArvio(rek, newElokuva("Blue Velvet"), Arvio("HYVA"), v);
        lisaaArvio(rek, newElokuva("Rambo"), Arvio("VALTTAVA"), v);

        Map<_Elokuva, List<_Arvio>> all = elokuvienArviot(rek, v);
        assertTrue("Palautettu map ei saa olla null koodilla\n" + v, all != null);

        assertEquals("Palautetun mapin avainten määrä väärä koodilla " + v + "palautit: " + all + "\n", 3, all.keySet().size());



        String w = v + ""
                + "arviot.get(new Elokuva(\"Eraserhead\"));\n";

        List er = all.get(newElokuva("Eraserhead"));
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w + "", er != null);
        List exp = new ArrayList();
        exp.add(Arvio("OK"));
        exp.add(Arvio("NEUTRAALI"));
        exp.add(Arvio("HUONO"));
        exp.add(Arvio("EI_NAHNYT"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "arviot.get(new Elokuva(\"Blue Velvet\"));\n";

        er = annaArviot(rek, newElokuva("Blue Velvet"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("HYVA"));
        exp.add(Arvio("HYVA"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "arviot.get(new Elokuva(\"Rambo\"));\n";

        er = annaArviot(rek, newElokuva("Rambo"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("VALTTAVA"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);
    }

    /*
     * 
     */
    @Test
    @Points(Tehtava.ID + ".4")
    public void lisaaArvioHenkilolleArvioRekisteri() throws Throwable {
        String metodi = "lisaaArvio";
        String virhe = "tee luokalle ArvioRekisteri metodi public void lisaaArvio(Henkilo henkilo, Elokuva elokuva, Arvio arvio)";

        assertTrue(virhe, _RekisteriRef.method(metodi).returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Henkilo(\"Arto\"), new Elokuva(\"Rambo\"), Arvio.HUONO);\n";
        _Elokuva e = newElokuva("Rambo");
        _Henkilo h = newHenkilo("Arto");
        _Rekisteri r = newRekisteri();
        _RekisteriRef.method(r, "lisaaArvio").returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(h, e, Arvio("HUONO"));
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void haeArvioArvioRekisteri() throws Throwable {
        String metodi = "haeArvio";
        String virhe = "tee luokalle ArvioRekisteri metodi public Arvio haeArvio(Henkilo henkilo, Elokuva elokuva)";

        assertTrue(virhe, _RekisteriRef.method(metodi).returning(_ArvioRef.cls()).taking(_HenkiloRef.cls(), _ElokuvaRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Henkilo(\"Arto\"), new Elokuva(\"Rambo\"), Arvio.HUONO);\n"
                + "rek.haeArvio( new Henkilo(\"Arto\"), new Elokuva(\"Rambo\"));\n";
        _Elokuva e = newElokuva("Rambo");
        _Henkilo h = newHenkilo("Arto");

        _Rekisteri r = newRekisteri();
        _RekisteriRef.method(r, "lisaaArvio").returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(h, e, Arvio("HUONO"));

        _RekisteriRef.method(r, metodi).returning(_ArvioRef.cls()).taking(_HenkiloRef.cls(), _ElokuvaRef.cls()).withNiceError(v).invoke(h, e);
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void henkilonArviotRekisteri() throws Throwable {
        _Rekisteri rek = newRekisteri();
        _Henkilo h1 = newHenkilo("Pekka");
        _Henkilo h2 = newHenkilo("Arto");

        String v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n";

        lisaaArvio(rek, h1, newElokuva("Pulp fiction"), Arvio("OK"), arvioLuokka);
        lisaaArvio(rek, h1, newElokuva("Eraserhead"), Arvio("HYVA"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Eraserhead"), Arvio("HUONO"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Pulp fiction"), Arvio("NEUTRAALI"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Rambo"), Arvio("HYVA"), arvioLuokka);

        String w = v + "rek.haeArvio(h1, new Elokuva(\"Pulp fiction\") );\n";
        assertEquals(w, Arvio("OK"), haeArvio(rek, h1, newElokuva("Pulp fiction"), w));
        w = v + "rek.haeArvio(h1, new Elokuva(\"Pulp fiction\") );\n";
        assertEquals(w, Arvio("HYVA"), haeArvio(rek, h1, newElokuva("Eraserhead"), w));
        w = v + "rek.haeArvio(h2, new Elokuva(\"Eraserhead\") );\n";
        assertEquals(w, Arvio("HUONO"), haeArvio(rek, h2, newElokuva("Eraserhead"), w));
        w = v + "rek.haeArvio(h2, new Elokuva(\"Pulp fiction\") );\n";
        assertEquals(w, Arvio("NEUTRAALI"), haeArvio(rek, h2, newElokuva("Pulp fiction"), w));
        w = v + "rek.haeArvio(h2, new Elokuva(\"Rambo\") );\n";
        assertEquals(w, Arvio("HYVA"), haeArvio(rek, h2, newElokuva("Rambo"), w));
        w = v + "rek.haeArvio(h1, new Elokuva(\"Rambo\") );\n";
        assertEquals(w, Arvio("EI_NAHNYT"), haeArvio(rek, h1, newElokuva("Rambo"), w));
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void annaHenkilonArviotArviorekisteri() throws Throwable {
        String metodi = "annaHenkilonArviot";
        String virhe = "tee luokalle ArvioRekisteri metodi public Map<Elokuva, Arvio> annaHenkilonArviot(Henkilo henkilo)";

        assertTrue(virhe, _RekisteriRef.method(metodi).returning(Map.class).taking(_HenkiloRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Henkilo(\"Arto\"), new Elokuva(\"Rambo\"), Arvio.HUONO);\n"
                + "rek.annaHenkilonArviot( new Henkilo(\"Arto\"));\n";
        _Elokuva e = newElokuva("Rambo");
        _Henkilo h = newHenkilo("Arto");
        _Rekisteri rek = newRekisteri();
        _RekisteriRef.method(rek, "lisaaArvio").returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(h, e, Arvio("HUONO"));

        _RekisteriRef.method(rek, metodi).returning(Map.class).taking(_HenkiloRef.cls()).withNiceError(v).invoke(h);
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void henkilonArviotRekisterista() throws Throwable {
        _Rekisteri rek = newRekisteri();
        _Henkilo h1 = newHenkilo("Pekka");
        _Henkilo h2 = newHenkilo("Arto");

        String v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n";

        lisaaArvio(rek, h1, newElokuva("Pulp fiction"), Arvio("OK"), arvioLuokka);
        lisaaArvio(rek, h1, newElokuva("Eraserhead"), Arvio("HYVA"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Eraserhead"), Arvio("HUONO"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Pulp fiction"), Arvio("NEUTRAALI"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Rambo"), Arvio("HYVA"), arvioLuokka);

        String w = v + ""
                + "Map<Elokuva,Arvio> arviot = rek.annaHenkilonArviot(new Henkilo(\"Pekka\"));\n";

        Map<_Elokuva, _Arvio> er = annaHenkilonArviot(rek, newHenkilo("Pekka"), v);
        assertTrue("Palautettu map ei saa olla null koodilla\n" + w, er != null);
        assertEquals("Palautetun mapin avainten määrä väärä koodilla " + w + " palautit: " + er + "\n", 2, er.keySet().size());
        String w1 = w + "arviot.get(new Elokuva(\"Pulp fiction\"));\n";
        assertEquals(w1, Arvio("OK"), er.get(newElokuva("Pulp fiction")));
        w1 = w + "arviot.get(new Elokuva(\"Eraserhead\"));\n";
        assertEquals(w1, Arvio("HYVA"), er.get(newElokuva("Eraserhead")));

        v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n";

        v += ""
                + "Map<Elokuva,Arvio> arviot = rek.annaHenkilonArviot(new Henkilo(\"Arto\"));\n";
        er = annaHenkilonArviot(rek, newHenkilo("Arto"), v);
        assertTrue("Palautettu map ei saa olla null koodilla\n" + w, er != null);
        assertEquals("Palautetun mapin avainten määrä väärä koodilla " + w + " palautit: " + er, 3, er.keySet().size());
        String w2 = v + "arviot.get(new Elokuva(\"Pulp fiction\"));\n";
        assertEquals(w2, Arvio("NEUTRAALI"), er.get(newElokuva("Pulp fiction")));
        w2 = v + "arviot.get(new Elokuva(\"Eraserhead\"));\n";
        assertEquals(w2, Arvio("HUONO"), er.get(newElokuva("Eraserhead")));
        w2 = v + "arviot.get(new Elokuva(\"Rambo\"));\n";
        assertEquals(w2, Arvio("HYVA"), er.get(newElokuva("Rambo")));

        v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n";

 v += ""
                + "Map<Elokuva,Arvio> arviot = rek.annaHenkilonArviot(new Henkilo(\"Jukka\"));\n";
        er = annaHenkilonArviot(rek, newHenkilo("Jukka"), v);
        assertTrue("Jos henkilöllä ei ole arvioita, palautettu map ei saa olla null\n" + v, er != null);
        assertEquals("Jos henkilöllä ei ole arvioita, palautetun mapin pitää olla tyhjä " + v + " palautit: " + er, 0, er.keySet().size());
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void henkilonArviotVaikuttavatElokuvanArvioihinRekisterissa() throws Throwable {
        _Rekisteri rek = newRekisteri();
        _Henkilo h1 = newHenkilo("Pekka");
        _Henkilo h2 = newHenkilo("Arto");
        _Henkilo h3 = newHenkilo("Jukka");

        String v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "Henkilo h3 = new Henkilo(\"Jukka\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h3, new Elokuva(\"Eraserhead\"), Arvio.NEUTRAALI);\n";

        lisaaArvio(rek, h1, newElokuva("Pulp fiction"), Arvio("OK"), arvioLuokka);
        lisaaArvio(rek, h1, newElokuva("Eraserhead"), Arvio("HYVA"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Eraserhead"), Arvio("HUONO"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Pulp fiction"), Arvio("NEUTRAALI"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Rambo"), Arvio("HYVA"), arvioLuokka);
        lisaaArvio(rek, h3, newElokuva("Eraserhead"), Arvio("NEUTRAALI"), arvioLuokka);

        String w = v + ""
                + "rek.annaArviot(new Elokuva(\"Eraserhead\"));\n";

        List er = annaArviot(rek, newElokuva("Eraserhead"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        List exp = new ArrayList();
        exp.add(Arvio("HYVA"));
        exp.add(Arvio("NEUTRAALI"));
        exp.add(Arvio("HUONO"));
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "rek.annaArviot(new Elokuva(\"Pulp fiction\"));\n";

        er = annaArviot(rek, newElokuva("Pulp fiction"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("NEUTRAALI"));
        exp.add(Arvio("OK"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);

        w = v + ""
                + "rek.annaArviot(new Elokuva(\"Rambo\"));\n";

        er = annaArviot(rek, newElokuva("Rambo"), v);
        assertTrue("Palautettu lista ei saa olla null koodilla\n" + w, er != null);
        exp = new ArrayList();
        exp.add(Arvio("HYVA"));
        Collections.sort(exp);
        Collections.sort(er);
        assertEquals("Palautettu listan pituus väärä koodilla\n" + w + "palautit: " + er, exp.size(), er.size());
        assertEquals("Palautettu listan sisältö väärä koodilla\n" + w + "palautit: " + er, exp, er);
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void arvioijat() throws Throwable {
        String metodi = "arvioijat";
        String virhe = "tee luokalle ArvioRekisteri metodi public List<Henkilo> arvioijat()";

        assertTrue(virhe, _RekisteriRef.method(metodi).returning(List.class).takingNoParams().isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Henkilo(\"Arto\"), new Elokuva(\"Rambo\"), Arvio.HUONO);\n"
                + "rek.arvioijat();\n";
        _Elokuva e = newElokuva("Rambo");
        _Henkilo h = newHenkilo("Arto");
        _Rekisteri r = newRekisteri();
        _RekisteriRef.method(r, "lisaaArvio").returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(h, e, Arvio("HUONO"));

        _RekisteriRef.method(r, metodi).returning(List.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points(Tehtava.ID + ".4")
    public void arvioijatToimii() throws Throwable {
        _Rekisteri rek = newRekisteri();
        _Henkilo h1 = newHenkilo("Pekka");
        _Henkilo h2 = newHenkilo("Arto");

        String v = ""
                + "Rekisteri rek = new Rekisteri();\n"
                + "Henkilo h1 = new Henkilo(\"Pekka\");\n"
                + "Henkilo h2 = new Henkilo(\"Arto\");\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Pulp fiction\"), Arvio.OK);\n"
                + "rek.lisaaArvio(h1, new Elokuva(\"Eraserhead\"), Arvio.HYVA);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Eraserhead\"), Arvio.HUONO);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Pulp fiction\"), Arvio.NEUTRAALI);\n"
                + "rek.lisaaArvio(h2, new Elokuva(\"Rambo\"), Arvio.HYVA);\n"
                + "rek.arvioijat();\n";

        lisaaArvio(rek, h1, newElokuva("Pulp fiction"), Arvio("OK"), arvioLuokka);
        lisaaArvio(rek, h1, newElokuva("Eraserhead"), Arvio("HYVA"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Eraserhead"), Arvio("HUONO"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Pulp fiction"), Arvio("NEUTRAALI"), arvioLuokka);
        lisaaArvio(rek, h2, newElokuva("Rambo"), Arvio("HYVA"), arvioLuokka);
        List<_Henkilo> at = arvioijat(rek, v);
        assertTrue("Palautettu lista ei saa olla null koodilla " + v, at != null);
        assertEquals("Palautetun listan koko väärä koodilla " + v + "palautit: " + at + "\n", 2, at.size());
        List<_Henkilo> l = new ArrayList<_Henkilo>();
        l.add(newHenkilo("Pekka"));
        l.add(newHenkilo("Arto"));

        assertTrue("Palautetun lista väärä koodilla " + v + "palautit: " + at, samat(l, at));
    }

    private boolean samat(List<_Henkilo> a, List<_Henkilo> b) {
        for (_Henkilo _henkilo : b) {
            if (!a.contains(_henkilo)) {
                return false;
            }
        }

        return true;
    }
    /*
     * 
     */

    @Test
    @Points(Tehtava.ID + ".5")
    public void henkiloComparator() throws Throwable {
        Class henkiloComparatorClass = ReflectionUtils.findClass(henkiloComparatorLuokka);

        assertNotNull("Olethan luonut pakkaukseen suosittelija.comparator luokan HenkiloComparator?", henkiloComparatorClass);
        assertTrue("Toteuttaahan luokka " + henkiloComparatorLuokka + " rajapinnan Comparator<Henkilo>?", Comparator.class.isAssignableFrom(henkiloComparatorClass));

        try {
            Reflex.reflect(henkiloComparatorClass).constructor().taking(Map.class).isPublic();
        } catch (AssertionError ae) {
            fail("Onhan luokalla " + s(henkiloComparatorLuokka) + " konstruktori public " + s(henkiloComparatorLuokka) + "(Map<Henkilo, Integer> henkiloidenSamuus)?");
        }

        _Henkilo pekka = newHenkilo("Pekka");
        _Henkilo arto = newHenkilo("Arto");
        _Henkilo mikael = newHenkilo("Mikael");

        Map arviot = new HashMap();
        arviot.put(pekka, 42);
        arviot.put(arto, 1);
        arviot.put(mikael, 33);

        String v = "Map<Henkilo, Integer> map = new HashMap<Henkilo, Integer>();\n"
                + "Henkilo pekka = new Henkilo(\"Pekka\");\n"
                + "Henkilo arto = new Henkilo(\"Arto\");\n"
                + "Henkilo mikael = new Henkilo(\"Mikael\");\n"
                + "map.put(pekka, 42);\n"
                + "map.put(arto, 1);\n"
                + "map.put(mikael, 33)\n"
                + "HenkiloComparator comp = new HenkiloComparator(map);\n";

        Comparator henkiloComparator = (Comparator) Reflex.reflect(henkiloComparatorClass).constructor().taking(Map.class).invoke(arviot);
        _HComp hc = (_HComp) henkiloComparator;

        String w = v + "comp.compare(arto, pekka);\n";
        int ero = compareH(hc, arto, pekka, w);
        assertTrue(w + "odotettiin positiivista, palautit: " + ero, ero > 0);

        w = v + "comp.compare(pekka, arto);\n";
        ero = compareH(hc, pekka, arto, w);
        assertTrue(w + "odotettiin negatiivista, palautit: " + ero, ero < 0);

        w = v + "comp.compare(arto, mikael);\n";
        ero = compareH(hc, arto, mikael, w);
        assertTrue(w + "odotettiin positiivista, palautit: " + ero, ero > 0);

        w = v + "comp.compare(mikale, arto);\n";
        ero = compareH(hc, mikael, arto, w);
        assertTrue(w + "odotettiin negatiivista, palautit: " + ero, ero < 0);

        w = v + "comp.compare(pekka, mikael);\n";
        ero = compareH(hc, pekka, mikael, w);
        assertTrue(w + "odotettiin negatiivista, palautit: " + ero, ero < 0);

        w = v + "comp.compare(mikale, pekka);\n";
        ero = compareH(hc, mikael, pekka, w);
        assertTrue(w + "odotettiin positiivista, palautit: " + ero, ero > 0);
    }

    @Test
    @Points(Tehtava.ID + ".6")
    public void elokuvaComparator() throws Throwable {
        Class elokuvaComparatorClass = ReflectionUtils.findClass(elokuvaComparatorLuokka);

        assertNotNull("Olethan luonut pakkaukseen suosittelija.comparator luokan HenkiloComparator?", elokuvaComparatorClass);
        assertTrue("Toteuttaahan luokka " + elokuvaComparatorLuokka + " rajapinnan Comparator<Elokuva>?", Comparator.class.isAssignableFrom(elokuvaComparatorClass));

        try {
            Reflex.reflect(elokuvaComparatorClass).constructor().taking(Map.class).requirePublic();
        } catch (AssertionError ae) {
            fail("Onhan luokalla " + s(elokuvaComparatorLuokka) + " konstruktori public " + s(elokuvaComparatorLuokka) + "(Map<Elokuva, List<Arvio>> arviot)?");
        }

        String v = "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "Elokuva saksiKasi = new Elokuva(\"Saksikäsi\");\n"
                + "Elokuva eraserhead = new Elokuva(\"Eraserhead\");\n"
                + "Elokuva haifisch = new Elokuva(\"Haifisch\");\n"
                + "Henkilo pekka = new Henkilo(\"Pekka\");\n"
                + "rek.lisaaArvio(pekka, eraserhead, Arvio.OK);\n"
                + "rek.lisaaArvio(pekka, saksiKasi, Arvio.HUONO);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n";

        _Rekisteri rek = newRekisteri();
        _Elokuva saksiKasi = newElokuva("Saksikäsi");
        _Elokuva eraserhead = newElokuva("Eraserhead");
        _Elokuva haifisch = newElokuva("Haifisch");
        _Henkilo pekka = newHenkilo("Pekka");

        lisaaArvio(rek, pekka, saksiKasi, Arvio("HUONO"), v);
        lisaaArvio(rek, pekka, eraserhead, Arvio("OK"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, saksiKasi, Arvio("OK"), v);
        lisaaArvio(rek, saksiKasi, Arvio("OK"), v);

        Map kaikkiArviot = elokuvienArviot(rek, v);

        v += "ElokuvaComparator comp = new ElokuvaComparator( rek.elokuvienArviot() );\n";

        Comparator elokuvaComparator = (Comparator) Reflex.reflect(elokuvaComparatorClass).constructor().taking(Map.class).invoke(kaikkiArviot);
        _EComp hc = (_EComp) elokuvaComparator;

        String w = v + "comp.compare(eraserhead, saksikasi);\n";
        int ero = compareE(hc, eraserhead, saksiKasi, w);
        assertTrue(w + "odotettiin negatiivista, palautit: " + ero, ero < 0);

        w = v + "comp.compare(saksikasi, eraserhead);\n";
        ero = compareE(hc, saksiKasi, eraserhead, w);
        assertTrue(w + "odotettiin positiivista palautit: " + ero, ero > 0);

        w = v + "comp.compare(saksikasi, haifisch);\n";
        ero = compareE(hc, saksiKasi, haifisch, w);
        assertTrue(w + "odotettiin negatiivista palautit: " + ero, ero < 0);

        w = v + "comp.compare(haifisch, saksikasi);\n";
        ero = compareE(hc, haifisch, saksiKasi, w);
        assertTrue(w + "odotettiin positiivista palautit: " + ero, ero > 0);

        w = v + "comp.compare(eraserhead, haifisch);\n";
        ero = compareE(hc, eraserhead, haifisch, w);
        assertTrue(w + "odotettiin negatiivista palautit: " + ero, ero < 0);

        w = v + "comp.compare( haifisch, eraserhead);\n";
        ero = compareE(hc, haifisch, eraserhead, w);
        assertTrue(w + "odotettiin positiivista palautit: " + ero, ero > 0);
    }

    /*
     * 
     */
    @Test
    @Points(Tehtava.ID + ".7")
    public void onLuokkaSuosittelija() {
        String luokanNimi = suosittelijaLuokka;
        _SuosittelijaRef = Reflex.reflect(luokanNimi);
        assertTrue("tee pakkauseen suosittelija luokka Suosittelija", _SuosittelijaRef.isPublic());
    }

    @Test
    @Points(Tehtava.ID + ".7")
    public void eiYlimaaraisiaOliomuuttujia() {
        saniteettitarkastus(suosittelijaLuokka, 3, "muuta kuin arviorekisterin muistavan oliomuuttujan");
    }

    @Test
    @Points(Tehtava.ID + ".7")
    public void suosittelijallaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle Suosittelija konstruktori public Suosittelija(ArvioRekisteri arvioRekisteri)",
                _SuosittelijaRef.constructor().taking(_RekisteriRef.cls()).isPublic());

        String luokanNimi = suosittelijaLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);

        assertEquals("Luokalla Suosittelija tulee olla vain yksi konstruktori, nyt konstruktoreja on", 1, c.getConstructors().length);

        _SuosittelijaRef.constructor().taking(_RekisteriRef.cls()).withNiceError("\nVirheeseen johtanut koodi new Suosittelija(\"new ArvioRekisteri()\"); ").invoke(newRekisteri());
    }

    @Test
    @Points(Tehtava.ID + ".7")
    public void suosittelijallaMetodiSuositteElokuva() throws Throwable {
        String metodi = "suositteleElokuva";
        String virhe = "tee luokalle Suosittelija metodi public Elokuva suositteleElokuva(Henkilo henkilo)";

        assertTrue(virhe, _SuosittelijaRef.method(metodi).returning(_ElokuvaRef.cls()).taking(_HenkiloRef.cls()).isPublic());

        String v = "Virhe koodilla:\n"
                + "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "rek.lisaaArvio( new Elokuva(\"Rambo\"), Arvio.OK);\n"
                + "Suosittelija netflix = new Suosittelija(rek);\n"
                + "netflix.suositteleElokuva(new Henkilo(\"Arto\"));\n";
        _Elokuva e = newElokuva("Rambo");
        _Rekisteri r = newRekisteri();
        _RekisteriRef.method(r, "lisaaArvio").returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(e, Arvio("OK"));

        _Suosittelija s = newSuosittelija(r);
        assertFalse("Suositeltuna elokuvana palautettiin null koodilla" + v, suosittele(s, newHenkilo("arto"), v) == null);
        assertEquals(v, e, suosittele(s, newHenkilo("arto"), v));
    }

    @Test
    @Points(Tehtava.ID + ".7")
    public void suositellaanOikeinJosHenkiloEiItseArvostellutMitaan() throws Throwable {
        String v = "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "Elokuva saksiKasi = new Elokuva(\"Saksikäsi\");\n"
                + "Elokuva eraserhead = new Elokuva(\"Eraserhead\");\n"
                + "Elokuva haifisch = new Elokuva(\"Haifisch\");\n"
                + "Henkilo pekka = new Henkilo(\"Pekka\");\n"
                + "rek.lisaaArvio(pekka, eraserhead, Arvio.OK);\n"
                + "rek.lisaaArvio(pekka, saksiKasi, Arvio.HUONO);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n"
                + "Suosittelija suosittelija = new Suosittelija(rek);\n";

        _Rekisteri rek = newRekisteri();
        _Elokuva saksiKasi = newElokuva("Saksikäsi");
        _Elokuva eraserhead = newElokuva("Eraserhead");
        _Elokuva haifisch = newElokuva("Haifisch");
        _Henkilo pekka = newHenkilo("Pekka");

        lisaaArvio(rek, pekka, saksiKasi, Arvio("HUONO"), v);
        lisaaArvio(rek, pekka, eraserhead, Arvio("OK"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, saksiKasi, Arvio("OK"), v);
        lisaaArvio(rek, saksiKasi, Arvio("OK"), v);

        _Suosittelija s = newSuosittelija(rek);

        v += "suosittelija.suositteleElokuva(new Henkilo(\"Arto\"));\n";
        assertEquals(v, newElokuva("Eraserhead"), suosittele(s, newHenkilo("Arto"), v));
    }

    @Test
    @Points(Tehtava.ID + ".7")
    public void suositellaanOikeinJosHenkiloEiItseArvostellutMitaan2() throws Throwable {
        String v = "ArvioRekisteri rek = new ArvioRekisteri();\n"
                + "Elokuva saksiKasi = new Elokuva(\"Saksikäsi\");\n"
                + "Elokuva eraserhead = new Elokuva(\"Eraserhead\");\n"
                + "Elokuva haifisch = new Elokuva(\"Haifisch\");\n"
                + "Henkilo pekka = new Henkilo(\"Pekka\");\n"
                + "rek.lisaaArvio(pekka, eraserhead, Arvio.OK);\n"
                + "rek.lisaaArvio(pekka, saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.OK);\n"
                + "rek.lisaaArvio(saksiKasi, Arvio.HYVA);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n"
                + "rek.lisaaArvio(haifisch, Arvio.HUONO);\n"
                + "Suosittelija suosittelija = new Suosittelija(rek);\n";

        _Rekisteri rek = newRekisteri();
        _Elokuva saksiKasi = newElokuva("Saksikäsi");
        _Elokuva eraserhead = newElokuva("Eraserhead");
        _Elokuva haifisch = newElokuva("Haifisch");
        _Henkilo pekka = newHenkilo("Pekka");

        lisaaArvio(rek, pekka, saksiKasi, Arvio("OK"), v);
        lisaaArvio(rek, pekka, eraserhead, Arvio("OK"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, haifisch, Arvio("HUONO"), v);
        lisaaArvio(rek, saksiKasi, Arvio("OK"), v);
        lisaaArvio(rek, saksiKasi, Arvio("HYVA"), v);

        _Suosittelija s = newSuosittelija(rek);

        v += "suosittelija.suositteleElokuva(new Henkilo(\"Arto\"));\n";
        assertEquals(v, newElokuva("Saksikäsi"), suosittele(s, newHenkilo("Arto"), v));
    }

    /*
     * 
     */
    //suosittelija 2
    /*
     * 
     */
    private int compareE(_EComp ec, _Elokuva h1, _Elokuva h2, String v) throws Throwable {
        Reflex.ClassRef<_EComp> _ElokuvaCompRef = Reflex.reflect(elokuvaComparatorLuokka);
        return _ElokuvaCompRef.method(ec, "compare").returning(int.class).taking(_ElokuvaRef.cls(), _ElokuvaRef.cls()).withNiceError(v).invoke(h1, h2);
    }

    private int compareH(_HComp hc, _Henkilo h1, _Henkilo h2, String v) throws Throwable {
        Reflex.ClassRef<_HComp> _HenkiloCompRef = Reflex.reflect(henkiloComparatorLuokka);
        return _HenkiloCompRef.method(hc, "compare").returning(int.class).taking(_HenkiloRef.cls(), _HenkiloRef.cls()).withNiceError(v).invoke(h1, h2);
    }

    private List<_Henkilo> arvioijat(_Rekisteri rek, String v) throws Throwable {
        return _RekisteriRef.method(rek, "arvioijat").returning(List.class).takingNoParams().withNiceError(v).invoke();
    }

    private Map<_Elokuva, _Arvio> annaHenkilonArviot(_Rekisteri rek, _Henkilo h, String v) throws Throwable {
        return _RekisteriRef.method(rek, "annaHenkilonArviot").returning(Map.class).taking(_HenkiloRef.cls()).withNiceError(v).invoke(h);
    }

    private void lisaaArvio(_Rekisteri rek, _Elokuva e, _Arvio a, String v) throws Throwable {
        _RekisteriRef.method(rek, "lisaaArvio").returningVoid().taking(_ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(e, a);
    }

    private void lisaaArvio(_Rekisteri rek, _Henkilo h, _Elokuva e, _Arvio a, String v) throws Throwable {
        _RekisteriRef.method(rek, "lisaaArvio").returningVoid().taking(_HenkiloRef.cls(), _ElokuvaRef.cls(), _ArvioRef.cls()).withNiceError(v).invoke(h, e, a);
    }

    private _Arvio haeArvio(_Rekisteri rek, _Henkilo h, _Elokuva e, String v) throws Throwable {
        return _RekisteriRef.method(rek, "haeArvio").returning(_ArvioRef.cls()).taking(_HenkiloRef.cls(), _ElokuvaRef.cls()).withNiceError(v).invoke(h, e);
    }

    private List<_Arvio> annaArviot(_Rekisteri rek, _Elokuva e, String v) throws Throwable {
        return (List<_Arvio>) _RekisteriRef.method(rek, "annaArviot").returning(List.class).taking(_ElokuvaRef.cls()).withNiceError(v).invoke(e);
    }

    private Map<_Elokuva, List<_Arvio>> elokuvienArviot(_Rekisteri rek, String v) throws Throwable {

        return (Map<_Elokuva, List<_Arvio>>) _RekisteriRef.method(rek, "elokuvienArviot")
                .returning(Map.class).takingNoParams().withNiceError(v).invoke();
    }

    private _Elokuva suosittele(_Suosittelija s, _Henkilo h, String v) throws Throwable {
        return _SuosittelijaRef.method(s, "suositteleElokuva").returning(_ElokuvaRef.cls()).taking(_HenkiloRef.cls()).withNiceError(v).invoke(h);
    }

    public _Rekisteri luoArvioRekisteri() throws Throwable {
        return _RekisteriRef.constructor().takingNoParams().invoke();
    }

    public _Henkilo newHenkilo(String nimi) throws Throwable {
        return _HenkiloRef.constructor().taking(String.class).invoke(nimi);
    }

    public _Suosittelija newSuosittelija(_Rekisteri rek) throws Throwable {
        return _SuosittelijaRef.constructor().taking(_RekisteriRef.cls()).invoke(rek);
    }

    public _Elokuva newElokuva(String nimi) throws Throwable {
        return _ElokuvaRef.constructor().taking(String.class).invoke(nimi);
    }

    public _Rekisteri newRekisteri() throws Throwable {
        return _RekisteriRef.constructor().takingNoParams().invoke();
    }

    private _Arvio Arvio(String e) {
        String luokanNimi = arvioLuokka;
        Class c = ReflectionUtils.findClass(luokanNimi);
        Object[] vakiot = c.getEnumConstants();

        for (Object vakio : vakiot) {
            if (vakio.toString().equals(e)) {
                return (_Arvio) vakio;
            }
        }

        return null;
    }

    private boolean sis(String tunnus, Object[] vakiot) {
        for (Object vakio : vakiot) {
            if (vakio.toString().equals(tunnus)) {
                return true;
            }
        }
        return false;
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + s(klassName) + " muuttuja " + kentta(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + s(klassName) + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + s(klassName) + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}

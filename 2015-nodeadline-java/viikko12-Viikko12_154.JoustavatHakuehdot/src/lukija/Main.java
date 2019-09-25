package lukija;

import lukija.ehdot.*;

public class Main {

    public static void main(String[] args) {
        String osoite = "http://www.gutenberg.myebook.bg/2/5/5/2554/2554-8.txt";
        GutenbergLukija kirja = new GutenbergLukija(osoite);
    
        Ehto ehto = new SisaltaaSanan("beer");

        for (String rivi : kirja.rivitJoilleVoimassa(ehto)) {
            System.out.println(rivi);
        }
    }
}

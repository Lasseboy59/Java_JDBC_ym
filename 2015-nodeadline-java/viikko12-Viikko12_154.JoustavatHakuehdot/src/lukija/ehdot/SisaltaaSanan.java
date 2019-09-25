package lukija.ehdot;

public class SisaltaaSanan implements Ehto {

    String sana;

    public SisaltaaSanan(String sana) {
        this.sana = sana;
    }

    @Override
    public boolean toteutuu(String rivi) {
        return rivi.contains(sana);
    }
    
    
}

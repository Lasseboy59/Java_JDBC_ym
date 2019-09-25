


import java.util.Date;
 
public class Tuote {

  private int id;
  private String nimi;
  private double hinta;
  private Date julkaisupvm;
  private int tuoteryhmaid;
  private String tuoteryhmaNimi;

  public Tuote(int id, String nimi, double hinta, Date julkaisupvm, int tuoteryhmaid, String tuoteryhmaNimi) {
    this.id = id;
    this.nimi = nimi;
    this.hinta = hinta;
    this.julkaisupvm = julkaisupvm;
    this.tuoteryhmaid = tuoteryhmaid;
    this.tuoteryhmaNimi = tuoteryhmaNimi;
  }

  public void setTuoteryhmaNimi(String tuoteryhmaNimi) {
    this.tuoteryhmaNimi = tuoteryhmaNimi;
  }

  public String getTuoteryhmaNimi() {
    return tuoteryhmaNimi;
  }

  public double getHinta() {
    return hinta;
  }

  public void setHinta(double hinta) {
    this.hinta = hinta;
  }

  public int getId() {
    //System.out.println("Tuotteen " +this.getNimi() + " ID: " + this.id);
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getJulkaisupvm() {
    return julkaisupvm;
  }

  public void setJulkaisupvm(Date julkaisupvm) {
    this.julkaisupvm = julkaisupvm;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public int getTuoteryhmaid() {
    //System.out.println("Tuotteen " +this.getNimi() + " ID: " + this.getId() + " Tuoteryhm‰ID: " + this.tuoteryhmaid);
    return tuoteryhmaid;
  }

  public void setTuoteryhmaid(int tuoteryhmaid) {
    this.tuoteryhmaid = tuoteryhmaid;
  }

  public String toString() {
    return "Id: " + id + ", nimi: " + nimi + ", hinta: " + hinta + ", julkaisupvm: " + julkaisupvm + ", tuoteryhma(" + tuoteryhmaid + "): " + tuoteryhmaNimi;
  }
}
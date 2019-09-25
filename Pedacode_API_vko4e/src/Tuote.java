// luokka m‰‰rittelee tuotteen ominaisuudet
class Tuote {

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  private String code;
  private String name;
  private double price;

  public Tuote(String c, String n, double p) {
    this.code = c;
    this.name = n;
    this.price = p;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setSalary(int salary) {
    this.price = salary;
  }

  @Override
  public String toString() {
    return "Tuote{" + "code=" + code + ", name=" + name + ", price=" + price + '}';
  }

}

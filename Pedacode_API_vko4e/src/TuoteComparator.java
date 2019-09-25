// Komparaattoriluokat
import java.util.Comparator;


public class TuoteComparator {

  public TuoteComparator() {
  }
}
class MyNameComp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e1, Tuote e2) {
    return e1.getName().compareTo(e2.getName());
  }
}

class MyNameCompUp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e2, Tuote e1) {
    return e1.getName().compareTo(e2.getName());
  }
}

class MyCodeComp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e1, Tuote e2) {
    return e1.getCode().compareTo(e2.getCode());
  }
}

class MyCodeCompUp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e2, Tuote e1) {
    return e1.getCode().compareTo(e2.getCode());
  }
}

class MyPriceComp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e1, Tuote e2) {
    if (e1.getPrice() == e2.getPrice()) {
      return 0;
    }
    if (e1.getPrice() < e2.getPrice()) {
      return 1;
    } else {
      return -1;
    }
  }
}

class MyPriceCompUp implements Comparator<Tuote> {

  @Override
  public int compare(Tuote e2, Tuote e1) {
    if (e1.getPrice() == e2.getPrice()) {
      return 0;
    }
    if (e1.getPrice() < e2.getPrice()) {
      return 1;
    } else {
      return -1;
    }
  }
}

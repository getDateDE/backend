package de.getdate.backend.data;

public class EqualsUtil {
  private EqualsUtil() {
  }

  public static boolean compareEquals(Object first, Object second) {
    if (first.getClass() != second.getClass()) {
      return false;
    }

    if (first instanceof Comparable && second instanceof Comparable) {
      return ((Comparable)first).compareTo(second) == 0;
    }

    return first.equals(second);
  }

}

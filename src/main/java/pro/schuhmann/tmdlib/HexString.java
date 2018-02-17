package pro.schuhmann.tmdlib;

/**
 * This class is actually a normal String - It just exists to create a "difference" between a normal String
 * and a String, which contains only hexadecimal characters.
 * Yep. I know that I'm a noob. But hey, provide me a better solution. I'm sure you know one.
 */
public class HexString {

  private String hexString;

  /**
   * Create a new HexString.
   *
   * @param s A String.
   */
  HexString(String s)
  {
    hexString = s;
  }

  @Override
  public String toString()
  {
    return hexString;
  }

  public boolean equals(HexString hex) {
    return toString().equals(hex.toString());
  }
}

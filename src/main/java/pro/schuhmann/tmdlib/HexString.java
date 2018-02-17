package pro.schuhmann.tmdlib;

/**
 * This class is actually a normal String - It just exists to create a "difference" between a normal String
 * and a hexadecimal String, which only contains hexadecimal characters.
 * Yep. I know that's a bad solution. But hey, provide me a better one, I'm pleased to know!
 */
public class HexString {

  private final String hexString;

  /**
   * Create a new HexString.
   *
   * @param s A String.
   */
  HexString(String s)
  {
    hexString = s;
  }

  /**
   * Convert this HexString to a {@link String}.
   *
   * @return A String.
   */
  @Override
  public String toString()
  {
    return hexString;
  }

  /**
   * Compare to HexString objects.
   *
   * @param hex Another HexString object.
   * @return {@code true} if both HexStrings are equal, {@code false} if not.
   */
  public boolean equals(HexString hex) {
    return toString().equals(hex.toString());
  }
}

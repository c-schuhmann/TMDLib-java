package pro.schuhmann.tmdlib.enums;

/**
 * An enumeration of the different public key types. The length of the public key depends on the key type.
 */
public enum PublicKeyType {
  RSA_4096(0x34),
  RSA_2048(0x34),
  ELLIPTIC_CURVE(0x3C);

  int paddingSize;

  PublicKeyType(int paddingSize) {
    this.paddingSize = paddingSize;
  }

  /**
   * Get the public key type by it's key type value.
   *
   * @param value Key type value as int.
   * @return The public key type or {@code null}.
   */
  public static PublicKeyType getByValue(int value) {
    switch (value) {
      case 0x0: return RSA_4096;
      case 0x1: return RSA_2048;
      case 0x2: return ELLIPTIC_CURVE;
    }

    return null;
  }

  /**
   * Get the padding size of the public key.
   *
   * @return The padding size.
   */
  public int getPaddingSize() {
    return paddingSize;
  }

}

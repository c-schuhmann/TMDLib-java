package pro.schuhmann.tmdlib.enums;

/**
 * An enumeration of the different signature types. All signature types are identified by the value of the first four
 * bytes of a TMD file. The length and the padding of the signature depends on the signature type.
 */
public enum SignatureType {

  RSA_4096_SHA1      (0x200, 0x3C), // Unused for 3DS
  RSA_2048_SHA1      (0x100, 0x3C), // Unused for 3DS
  ELLIPTIC_CURVE_SHA1(0x3C , 0x40), // Unused for 3DS
  RSA_4096_SHA256    (0x200, 0x3C),
  RSA_2048_SHA256    (0x100, 0x3C),
  ECDSA_SHA256       (0x3C , 0x40);

  private final int signatureSize;
  private final int paddingSize;

  SignatureType(int signatureSize, int paddingSize) {
    this.signatureSize = signatureSize;
    this.paddingSize   = paddingSize;
  }

  public static SignatureType getByValue(int value)
  {
    switch(value)
    {
      case 0x010000:
        return RSA_4096_SHA1;
      case 0x010001:
        return RSA_2048_SHA1;
      case 0x010002:
        return ELLIPTIC_CURVE_SHA1;
      case 0x010003:
        return RSA_4096_SHA256;
      case 0x010004:
        return RSA_2048_SHA256;
      case 0x010005:
        return ECDSA_SHA256;
    }

    return null;
  }

  public int getSignatureDataSize() {
    return 0x4 + getPaddingSize() + getSignatureSize();
  }

  public int getSignatureSize()
  {
    return signatureSize;
  }

  private int getPaddingSize()
  {
    return paddingSize;
  }
}

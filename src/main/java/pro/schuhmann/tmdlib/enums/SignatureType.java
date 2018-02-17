package pro.schuhmann.tmdlib.enums;

public enum SignatureType {

  RSA_4096_SHA1      (0x010000, 0x200, 0x3C),
  RSA_2048_SHA1      (0x010001, 0x100, 0x3C),
  ELLIPTIC_CURVE_SHA1(0x010002, 0x3C , 0x40),
  RSA_4096_SHA256    (0x010003, 0x200, 0x3C),
  RSA_2048_SHA256    (0x010004, 0x100, 0x3C),
  ECDSA_SHA256       (0x010005, 0x3C , 0x40);

  private final int value;
  private final int signatureSize;
  private final int paddingSize;

  SignatureType(int value, int signatureSize, int paddingSize) {
    this.value         = value;
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

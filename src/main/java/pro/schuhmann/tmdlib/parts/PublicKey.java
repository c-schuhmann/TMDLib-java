package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.PublicKeyType;

import java.io.IOException;

/**
 * Retrieve information about Public Keys by creating an instance from this class.
 */
public class PublicKey {

  private PublicKeyType publicKeyType;
  private HexString modulus;   // ONLY available for RSA public keys!
  private Integer publicExponent;  // ONLY available for RSA public keys!
  private HexString publicKey; // ONLY available for ECC public keys!

  /**
   * Create a new public key object.
   *
   * @param tmdFile A {@link TmdFileReader} pointing to a TMD file.
   * @param keyOffsetInFile The offset in the TMD file, where the public key is located.
   * @param keyType The type of the public key.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public PublicKey(TmdFileReader tmdFile, int keyOffsetInFile, PublicKeyType keyType) throws IOException {
    this.publicKeyType = keyType;
    switch (keyType) {
      case RSA_4096:
        this.modulus        = tmdFile.getHexString(keyOffsetInFile, 0x200);
        this.publicExponent = tmdFile.getInt(keyOffsetInFile + 0x200);
        break;
      case RSA_2048:
        this.modulus        = tmdFile.getHexString(keyOffsetInFile, 0x100);
        this.publicExponent = tmdFile.getInt(keyOffsetInFile + 0x100);
        break;
      case ELLIPTIC_CURVE:
        this.publicKey      = tmdFile.getHexString(keyOffsetInFile, 0x3C);
        break;
    }
  }

  /**
   * Get the public key type.
   *
   * @return The public key type.
   */
  public PublicKeyType getPublicKeyType() {
    return publicKeyType;
  }

  /**
   * Get the public key modulus. ONLY available for RSA based public keys!
   *
   * @return The public key modulus or {@code null}, if the key type doesn't match.
   */
  public HexString getModulus() {
    return modulus;
  }

  /**
   * Get the public exponent. ONLY available for RSA based public keys!
   *
   * @return The public exponent or {@code null}, if the key type doesn't match.
   */
  public Integer getPublicExponent() {
    return publicExponent;
  }

  /**
   * Get the public key. ONLY available for ECC based public keys!
   *
   * @return The public key or {@code null}, if the key type doesn't match.
   */
  public HexString getPublicKey() {
    return publicKey;
  }
}
package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.PublicKeyType;

import java.io.IOException;

/**
 * Retrieve information about Certificates by creating an instance from this class.
 */
public class Certificate {

  private final SignatureData signatureData;
  private final String issuer;
  private final String name;
  private final PublicKey publicKey;

  /**
   * Create a new certificate object.
   *
   * @param tmdFile A {@link TmdFileReader} pointing to a TMD file.
   * @param certificateOffsetInFile The offset in the TMD file, where the certificate is located.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public Certificate(TmdFileReader tmdFile, int certificateOffsetInFile) throws IOException {
     // The offsets of the other parts of the certificate depend on the signature data size.
    this.signatureData = new SignatureData(tmdFile, certificateOffsetInFile);
    int newOffset = signatureData.getSignatureType().getSignatureDataSize() + certificateOffsetInFile;
    this.issuer        = tmdFile.getString(newOffset, 0x40);

    PublicKeyType pkt  = PublicKeyType.getByValue(tmdFile.getInt(newOffset + 0x40));
    if (pkt == null)
      throw new NullPointerException("The public key type couldn't be identified! Make sure the given file is valid.");

    this.name          = tmdFile.getString(newOffset + 0x44, 0x40);
    this.publicKey     = new PublicKey(tmdFile, newOffset + 0x88, pkt);
  }

  /**
   * Get the signature data of the certificate.
   *
   * @return The signature data.
   */
  public SignatureData getSignatureData() {
    return signatureData;
  }

  /**
   * Get the issuer of the certificate.
   *
   * @return the certificate issuer.
   */
  public String getIssuer() {
    return issuer;
  }

  /**
   * Get the name of the certificate.
   *
   * @return The certificate name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the public key of the certificate.
   *
   * @return The public key.
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }
}

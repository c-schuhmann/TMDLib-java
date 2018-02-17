package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.SignatureType;

import java.io.IOException;

/**
 * Retrieve information about the Signature Data by creating an instance from this class.
 */
public class SignatureData {

  private SignatureType signatureType;
  private HexString signature;

  /**
   * Create a new signature data object. These objects are only created by the
   * {@link pro.schuhmann.tmdlib.TMD} constructor.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public SignatureData(TmdFileReader tmdFile) throws IOException {
    this.signatureType = SignatureType.getByValue(tmdFile.getInt(0));

    if (signatureType == null)
      throw new NullPointerException("The signature type couldn't be identified! Make sure your TMD file is valid.");

    this.signature     = tmdFile.getHexString(4, signatureType.getSignatureSize());
  }

  /**
   * Get the signature type of the signature.
   *
   * @return The {@link SignatureType}.
   */
  public SignatureType getSignatureType() {
    return signatureType;
  }

  /**
   * Get the signature of the TMD. The hash for the signature is calculated over the header of the TMD.
   *
   * @return A HexString containing the signature of the TMD.
   */
  public HexString getSignature() {
    return signature;
  }
}

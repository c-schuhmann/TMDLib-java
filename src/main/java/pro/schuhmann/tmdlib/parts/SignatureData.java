package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.SignatureType;

import java.io.IOException;

/**
 * Retrieve information about Signature Data by creating an instance from this class.
 */
public class SignatureData {

  private SignatureType signatureType;
  private HexString signature;

  /**
   * Create a new signature data object.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public SignatureData(TmdFileReader tmdFile, int signatureDataOffsetInFile) throws IOException {
    this.signatureType = SignatureType.getByValue(tmdFile.getInt(signatureDataOffsetInFile));

    if (signatureType == null)
      throw new NullPointerException("The signature type couldn't be identified! Make sure the given file is valid.");

    this.signature = tmdFile.getHexString(signatureDataOffsetInFile + 0x4, signatureType.getSignatureSize());
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
   * Get the signature.
   *
   * @return A HexString containing the signature.
   */
  public HexString getSignature() {
    return signature;
  }
}

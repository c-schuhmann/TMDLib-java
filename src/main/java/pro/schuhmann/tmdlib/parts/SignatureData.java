package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.SignatureType;

import java.io.IOException;

public class SignatureData {

  private SignatureType signatureType;
  private HexString signature;

  public SignatureData(TmdFileReader tmdFile) throws IOException {
    this.signatureType = SignatureType.getByValue(tmdFile.getInt(0));
    this.signature     = tmdFile.getHexString(4, signatureType.getSignatureSize());
  }

  public SignatureType getSignatureType() {
    return signatureType;
  }

  public HexString getSignature() {
    return signature;
  }

  public void testPrintlnAll() {
    System.out.println("----- Signature Data -----");
    System.out.println("Signature Type: " + signatureType);
    System.out.println("Signature: " + signature);
  }
}

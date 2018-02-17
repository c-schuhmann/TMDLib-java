package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

public class Header {

  private String signatureIssuer;
  private byte version;
  private byte caCrlVersion;
  private byte signerCrlVersion;
  private long systemVersion; //TODO: More investigation - Minimum system version?
  private HexString titleId;
  private int titleType; //TODO: More investigation - Possibly relating to Title ID -> UID Title type?
  private short groupId;
  private int saveDataSize;
  private int srlPrivateSaveDataSize;
  private byte srlFlag;
  private int accessRights;
  private short titleVersion;
  private short contentCount;
  private short bootContent;
  private HexString ContentInfoRecordsHash;

  public Header(TmdFileReader tmdFile, final int headerOffsetInFile) throws IOException {
    this.signatureIssuer        = tmdFile.getString(headerOffsetInFile, 0x40);
    this.version                = tmdFile.getByte(headerOffsetInFile + 0x40);
    this.caCrlVersion           = tmdFile.getByte(headerOffsetInFile + 0x41);
    this.signerCrlVersion       = tmdFile.getByte(headerOffsetInFile + 0x42);
    this.systemVersion          = tmdFile.getLong(headerOffsetInFile + 0x44);
    this.titleId                = tmdFile.getHexString(headerOffsetInFile + 0x4C, 0x8);
    this.titleType              = tmdFile.getInt(headerOffsetInFile + 0x54);
    this.groupId                = tmdFile.getShort(headerOffsetInFile + 0x58);
    this.saveDataSize           = tmdFile.getInt(headerOffsetInFile + 0x5A);
    this.srlPrivateSaveDataSize = tmdFile.getInt(headerOffsetInFile + 0x5E);
    this.srlFlag                = tmdFile.getByte(headerOffsetInFile + 0x66);
    this.accessRights           = tmdFile.getInt(headerOffsetInFile + 0x98);
    this.titleVersion           = tmdFile.getShort(headerOffsetInFile + 0x9C);
    this.contentCount           = tmdFile.getShort(headerOffsetInFile + 0x9E);
    this.bootContent            = tmdFile.getShort(headerOffsetInFile + 0xA0);
    this.ContentInfoRecordsHash = tmdFile.getHexString(headerOffsetInFile + 0xA4, 0x20);
  }

  public String getSignatureIssuer() {
    return signatureIssuer;
  }

  public byte getVersion() {
    return version;
  }

  public byte getCaCrlVersion() {
    return caCrlVersion;
  }

  public byte getSignerCrlVersion() {
    return signerCrlVersion;
  }

  public long getSystemVersion() {
    return systemVersion;
  }

  public HexString getTitleId() {
    return titleId;
  }

  public int getTitleType() {
    return titleType;
  }

  public short getGroupId() {
    return groupId;
  }

  public int getSaveDataSize() {
    return saveDataSize;
  }

  public int getSrlPrivateSaveDataSize() {
    return srlPrivateSaveDataSize;
  }

  public byte getSrlFlag() {
    return srlFlag;
  }

  public int getAccessRights() {
    return accessRights;
  }

  public short getTitleVersion() {
    return titleVersion;
  }

  public short getContentCount() {
    return contentCount;
  }

  public short getBootContent() {
    return bootContent;
  }

  public HexString getContentInfoRecordsHash() {
    return ContentInfoRecordsHash;
  }

  public void testPrintlnAll() {
    System.out.println("----- Header -----");
    System.out.println("Signature Issuer: " + signatureIssuer);
    System.out.println("Version: " + version);
    System.out.println("ca_crl_version: " + caCrlVersion);
    System.out.println("signer_crl_version: " + signerCrlVersion);
    System.out.println("System Version: " + systemVersion);
    System.out.println("Title ID: " + titleId);
    System.out.println("Title Type: " + titleType);
    System.out.println("Group ID: " + groupId);
    System.out.println("Save Data Size (Bytes): " + saveDataSize);
    System.out.println("SRL Private Save Data Size (Bytes): " + srlPrivateSaveDataSize);
    System.out.println("SRL Flag: " + srlFlag);
    System.out.println("Access Rights: " + accessRights);
    System.out.println("Title Version: " + titleVersion);
    System.out.println("Content Count: " + contentCount);
    System.out.println("Boot Content: " + bootContent);
    System.out.println("SHA-256 Hash of the Content Info Records: " + ContentInfoRecordsHash);
  }
}

package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

/**
 * Retrieve information about the TMD Header by creating an instance from this class.
 */
public class Header {

  private final String signatureIssuer;
  private final byte version;
  private final byte caCrlVersion;
  private final byte signerCrlVersion;
  private final long systemVersion;
  private final HexString titleId;
  private final int titleType;
  private final short groupId;
  private final int saveDataSize;
  private final int srlPrivateSaveDataSize;
  private final byte srlFlag;
  private final int accessRights;
  private final short titleVersion;
  private final short contentCount;
  private final short bootContent;
  private final HexString ContentInfoRecordsHash;

  /**
   * Create a new header object. These objects are only created by the {@link pro.schuhmann.tmdlib.TMD} constructor.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   * @param headerOffsetInFile The offset in the TMD file, where the header is located.
   * @throws IOException An error occurred while reading the TMD file.
   */
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

  /**
   * Get the signature issuer of the TMD.
   *
   * @return The signature issuer.
   */
  public String getSignatureIssuer() {
    return signatureIssuer;
  }

  /**
   * Get the version of the TMD. <br>
   * This is not the title version! See {@link #getTitleVersion()}.
   *
   * @return The TMD version.
   */
  public byte getVersion() {
    return version;
  }

  /**
   * Get the ca_crl_version of the TMD. <br>
   * TODO: More investigation: CRL *could* stand for
   * <a href="https://en.wikipedia.org/wiki/Certificate_revocation_list">Certificate revocation list</a>
   *
   * @return The ca_crl_version.
   */
  public byte getCaCrlVersion() {
    return caCrlVersion;
  }

  /**
   * Get the signer_crl_version of the TMD. <br>
   * TODO: More investigation: CRL *could* stand for
   * <a href="https://en.wikipedia.org/wiki/Certificate_revocation_list">Certificate revocation list</a>
   *
   * @return The signer_crl_version.
   */
  public byte getSignerCrlVersion() {
    return signerCrlVersion;
  }

  /**
   * Get the system version of the TMD. <br>
   * TODO: More investigation - Minimum system version?
   *
   * @return The system version.
   */
  public long getSystemVersion() {
    return systemVersion;
  }

  /**
   * Get the title ID.
   *
   * @return The title ID.
   */
  public HexString getTitleId() {
    return titleId;
  }

  /**
   * Get the title type. <br>
   * TODO: More investigation - Possibly relating to Title ID: UID Title type?
   *
   * @return the title type.
   */
  public int getTitleType() {
    return titleType;
  }

  /**
   * Get the group ID of the title.
   *
   * @return The group ID.
   */
  public short getGroupId() {
    return groupId;
  }

  /**
   * Get the save data size of the title. (Also SRL Public Save Data Size)
   *
   * @return The save data size in bytes.
   * @see <a href="http://dsibrew.org/wiki/DSi_Cartridge_Header">DSiBrew: DSi Cartridge Header</a>
   */
  public int getSaveDataSize() {
    return saveDataSize;
  }

  /**
   * Get the SRL private save data size.
   *
   * @return The SRL private save data size.
   * @see <a href="http://dsibrew.org/wiki/DSi_Cartridge_Header">DSiBrew: DSi Cartridge Header</a>
   */
  public int getSrlPrivateSaveDataSize() {
    return srlPrivateSaveDataSize;
  }

  /**
   * Get the SRL flag.
   *
   * @return The SRL flag.
   * @see <a href="http://dsibrew.org/wiki/DSi_Cartridge_Header">DSiBrew: DSi Cartridge Header</a>
   */
  public byte getSrlFlag() {
    return srlFlag;
  }

  /**
   * Get the access rights. <br>
   * TODO: More investigation: Possibly relating to <a href="https://3dbrew.org/wiki/NCCH/Extended_Header#Storage_Info">
   *   NCCH Extended Header Storage Info</a>
   *
   * @return The access rights.
   */
  public int getAccessRights() {
    return accessRights;
  }

  /**
   * Get the title version.
   *
   * @return The title version.
   */
  public short getTitleVersion() {
    return titleVersion;
  }

  /**
   * Get the number of contents in the title.
   *
   * @return The content count.
   */
  public short getContentCount() {
    return contentCount;
  }

  /**
   * Get the index number of the boot content.
   *
   * @return The boot content index number.
   */
  public short getBootContent() {
    return bootContent;
  }

  /**
   * Get the SHA256 hash of the content info records.
   *
   * @return A HexString containing a SHA256 hash.
   */
  public HexString getContentInfoRecordsHash() {
    return ContentInfoRecordsHash;
  }
}

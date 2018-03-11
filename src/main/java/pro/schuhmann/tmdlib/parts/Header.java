package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;
import pro.schuhmann.tmdlib.enums.TitleType;

import java.io.IOException;
import java.util.HashSet;

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
  private final HashSet<TitleType> titleType;
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
    this.titleType              = TitleType.getbyValue(tmdFile.getInt(headerOffsetInFile + 0x54));
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
   * <a href="https://en.wikipedia.org/wiki/Certificate_revocation_list">Certificate revocation list</a>
   *
   * @return The ca_crl_version.
   */
  public byte getCaCrlVersion() {
    return caCrlVersion;
  }

  /**
   * Get the signer_crl_version of the TMD. <br>
   * <a href="https://en.wikipedia.org/wiki/Certificate_revocation_list">Certificate revocation list</a>
   *
   * @return The signer_crl_version.
   */
  public byte getSignerCrlVersion() {
    return signerCrlVersion;
  }

  /**
   * Get the system version of the TMD. <br>
   * Authors Note: All ~1700 3DS TMDs I have tested return 0; Not a single exception.
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
   * <b>Note:</b> This field seems to be for Wii titles only. If this method is called on other TMDs,
   * it will (possibly) always return {@code UNKNOWN_CT}.
   *
   * @return the title type.
   */
  public HashSet<TitleType> getTitleType() {
    return titleType;
  }

  /**
   * Get the group ID of the title. <br>
   * <b>Note:</b> <b>Note:</b> This field seems to be for Wii titles only. If this method is called on other TMDs,
   * it will (possibly) always return {@code 0}.
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
   * Authors note: All ~1700 3DS TMDs I have tested return 0; Not a single exception.
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

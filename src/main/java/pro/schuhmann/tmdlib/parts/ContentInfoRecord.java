package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

/**
 * Retrieve information about Content Info Records by creating an instance from this class.
 */
public class ContentInfoRecord {
  private short contentIndexOffset;
  private short contentCommandCount;
  private HexString nextContentRecordsHash;

  /**
   * Create a new content info record object. These objects are only created by the
   * {@link pro.schuhmann.tmdlib.TMD} constructor.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   * @param contentInfoOffsetInFile The offset in the TMD file, where the content info records are located.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public ContentInfoRecord(TmdFileReader tmdFile, final int contentInfoOffsetInFile) throws IOException{
    this.contentIndexOffset     = tmdFile.getShort(contentInfoOffsetInFile);
    this.contentCommandCount    = tmdFile.getShort(contentInfoOffsetInFile + 0x2);
    this.nextContentRecordsHash = tmdFile.getHexString(contentInfoOffsetInFile + 0x4, 0x20);
  }

  /**
   * Get the content index offset of the content info record.
   *
   * @return The content index offset.
   */
  public short getContentIndexOffset() {
    return contentIndexOffset;
  }

  /**
   * Get the content command count of the content info record.
   *
   * @return The content command count.
   */
  public short getContentCommandCount() {
    return contentCommandCount;
  }

  /**
   * Get the SHA256 hash of the next [n] content records that have not been hashed yet. <br>
   * [n] = Content command count.
   *
   * @return A HexString containing a SHA256 hash.
   */
  public HexString getNextContentRecordsHash() {
    return nextContentRecordsHash;
  }

}

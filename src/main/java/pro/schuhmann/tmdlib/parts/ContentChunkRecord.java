package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

/**
 * Retrieve information about Content Chunk Records by creating an instance from this class.
 */
public class ContentChunkRecord {

  private int contentId;
  private short contentIndex; //TODO: Create enum for content index
  private short contentType;  //TODO: Create enum (+ set?) for content type
  private long contentSize;
  private HexString sha256hash;

  /**
   * Create a new content chunk record object.
   *
   * @param tmdFile A {@link TmdFileReader} pointing to a TMD file.
   * @param contentChunkOffsetInFile The offset in the TMD file, where the content chunk records are located.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public ContentChunkRecord(TmdFileReader tmdFile, int contentChunkOffsetInFile) throws IOException {
    this.contentId    = tmdFile.getInt(contentChunkOffsetInFile);
    this.contentIndex = tmdFile.getShort(contentChunkOffsetInFile + 0x4);
    this.contentType  = tmdFile.getShort(contentChunkOffsetInFile + 0x6);
    this.contentSize  = tmdFile.getLong(contentChunkOffsetInFile + 0x8);
    this.sha256hash   = tmdFile.getHexString(contentChunkOffsetInFile + 0x10, 0x20);
  }

  /**
   * Get the content ID of the content chunk record.
   *
   * @return The contend ID.
   */
  public int getContentId() {
    return contentId;
  }

  /**
   * Get the content index of the content chunk record.
   *
   * @return The content index.
   */
  public short getContentIndex() {
    return contentIndex;
  }

  /**
   * Get the content type of the content chunk record.
   *
   * @return The content type.
   */
  public short getContentType() {
    return contentType;
  }

  /**
   * Get the content size of the content chunk record.
   *
   * @return The content size.
   */
  public long getContentSize() {
    return contentSize;
  }

  /**
   * Get the SHA256 hash of the content chunk record.
   *
   * @return A HexString containing a SHA256 hash.
   */
  public HexString getSha256hash() {
    return sha256hash;
  }
}
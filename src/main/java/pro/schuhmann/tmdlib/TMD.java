package pro.schuhmann.tmdlib;

import pro.schuhmann.tmdlib.parts.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieve information about TMD files by creating an instance from this class.
 * NOTE: ONLY 3DS TMD files are officially supported at this time!
 *
 * TODO: Write some Unit tests. Some time. In the future. Maybe.
 *
 * @see <a href="https://3dbrew.org/wiki/Title_metadata">3dbrew: Title Metadata</a>
 */
public class TMD {

  private SignatureData signatureData;
  private Header header;
  private List<ContentInfoRecord> contentInfoRecords;
  private List<ContentChunkRecord> contentChunkRecords;
  private Certificate[] certificates;

  /**
   * Create a new TMD object.
   *
   * @param tmdFile A {@link File} object pointing to the TMD file.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public TMD(File tmdFile) throws IOException {
    TmdFileReader tmdFileReader = new TmdFileReader(tmdFile);
    setup(tmdFileReader);
  }

  /**
   * Setup method for creating a new TMD instance. Only called by TMD constructor.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   * @throws IOException An error occurred while reading the TMD file.
   */
  private void setup(TmdFileReader tmdFile) throws IOException {
    /*
     * --- Signature Data ---
     *
     * The signature data is the only part in a TMD file, which has a variable size, depending on
     * it's signature type. The other parts of the TMD file all have a fixed size, but the
     * location in the file depends on the signature data length.
     */

    this.signatureData = new SignatureData(tmdFile, 0);
    final int signatureDataSize = signatureData.getSignatureType().getSignatureDataSize();

    /*
     * --- Header ---
     *
     * The header mostly contains information about the title itself. The header area in the file starts
     * at offset "Signature Data Size" and has a size of "0xC4".
     */

    this.header = new Header(tmdFile, signatureDataSize);

    /*
     * --- Content Info Records ---
     *
     * The content info records contain information about contents, like it's index offset. The content info area
     * in the file starts at offset "Signature Data Size + 0xC4" and has a size of "0x24*64".
     * A TMD file can contain up to 64 content info records but usually the first one is used.
     *
     * Note: The count of the content info records is independent from the content count.
     *
     * NOTE: I only *assume* that content info records follow the index strictly.
     * (like record 0, record 1, record 2, record 3...)
     */

    this.contentInfoRecords = new ArrayList<>();
    for (int infoRecordIndex = 0; infoRecordIndex < 64; infoRecordIndex++) {
      // If content command count of the record equals 0: record (and following) must be empty -> break;
      int offset = signatureDataSize + 0xC4 + infoRecordIndex * 0x24;
      if (tmdFile.getShort(offset + 0x2) != 0) {
        contentInfoRecords.add(new ContentInfoRecord(tmdFile, offset));
      } else {
        break;
      }
    }

    /*
     * --- Content Chunk Records ---
     *
     * The content chunk records contain information about content chunks, like it's index offset and content type.
     * The content chunk area in the file starts at offset "Signature Data Size + 0x9C4" and has a size of
     * "0x30*ContentCount". The content count is defined in the TMD header.
     */

    this.contentChunkRecords = new ArrayList<>();
    for (int chunkRecordIndex = 0; chunkRecordIndex < header.getContentCount(); chunkRecordIndex++) {
      int offset = signatureDataSize + 0x9C4 + chunkRecordIndex * 0x30;
      contentChunkRecords.add(new ContentChunkRecord(tmdFile, offset));
    }

    /*
     * --- Certificates ---
     *
     * If the TMD file is obtained from Nintendo's CDN, then it will have two certificates appended at the end of the
     * file. These two certificates together always have a size of 0x700 bytes, so the first certificate must start at
     * offset "EndOfFile - 0x700". The first certificate has a size of 0x300 bytes, so the second certificate must start
     * at offset "EndOfFile - 0x400".
     *
     * Type of the first certificate:  RSA_2048_SHA256 (0x010004)
     * Type of the second certificate: RSA_4096_SHA256 (0x010003)
     */

    int certificateOffset = tmdFile.getFileLength() - 0x700;
    // Check whether the certificates are available:
    if (tmdFile.getInt(certificateOffset) == 0x010004 && tmdFile.getInt(certificateOffset + 0x300) == 0x010003) {
      // Certificates are available!
      certificates    = new Certificate[2];
      certificates[0] = new Certificate(tmdFile, certificateOffset);
      certificates[1] = new Certificate(tmdFile, certificateOffset + 0x300);
    }
  }

  /**
   * Get the {@link SignatureData} of the TMD.
   *
   * @return A {@link SignatureData} object.
   */
  public SignatureData getSignatureData() {
    return signatureData;
  }

  /**
   * Get the {@link Header} of the TMD.
   *
   * @return A {@link Header} object.
   */
  public Header getHeader() {
    return header;
  }

  /**
   * Get all {@link ContentInfoRecord} objects from the TMD.
   *
   * @return A list of {@link ContentInfoRecord} objects.
   */
  public List<ContentInfoRecord> getContentInfoRecords() {
    return contentInfoRecords;
  }

  /**
   * Get all {@link ContentChunkRecord} objects from the TMD.
   *
   * @return A list of {@link ContentChunkRecord} objects.
   */
  public List<ContentChunkRecord> getContentChunkRecords() {
    return contentChunkRecords;
  }

  /**
   * Get the certificates of the TMD, if available.
   *
   * @return Two certificates or {@code null}, if not available.
   */
  public Certificate[] getCertificates() {
    return certificates;
  }
}
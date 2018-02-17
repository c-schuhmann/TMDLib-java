package pro.schuhmann.tmdlib;

import pro.schuhmann.tmdlib.parts.ContentChunkRecord;
import pro.schuhmann.tmdlib.parts.ContentInfoRecord;
import pro.schuhmann.tmdlib.parts.Header;
import pro.schuhmann.tmdlib.parts.SignatureData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieve information about TMD files by creating an instance from this class.
 * NOTE: ONLY 3DS TMD files are officially supported at this time!
 *
 * @see <a href="https://3dbrew.org/wiki/Title_metadata">3dbrew: Title Metadata</a>
 */
public class TMD {

  private SignatureData signatureData;
  private Header header;
  private List<ContentInfoRecord> contentInfoRecords;
  private List<ContentChunkRecord> contentChunkRecords;

  /**
   * Create a new instance of a TMD object.
   *
   * @param tmdFile A {@link File} object pointing to the TMD file.
   */
  public TMD(File tmdFile) throws IOException {
    TmdFileReader tmdFileReader = new TmdFileReader(tmdFile);
    setup(tmdFileReader);
  }

  /**
   * Setup method for creating a new TMD instance. Only called by TMD constructor.
   *
   * @param tmdFile A TmdFileReader pointing to a TMD file.
   */
  private void setup(TmdFileReader tmdFile) throws IOException {
    /*
     * --- Signature Data ---
     *
     * The signature data is the only part in a TMD file, which has a variable size, depending on
     * it's signature type. The other parts of the TMD file all have a fixed size, but the
     * location in the file depends on the signature data length.
     */
    this.signatureData = new SignatureData(tmdFile);
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
  }

  /**
   * Get the {@link SignatureData} of the TMD.
   *
   * @return A {@link SignatureData} object.
   */
  public SignatureData getSignatureData() {
    return signatureData;
  }

  public Header getHeader() {
    return header;
  }

  public List<ContentInfoRecord> getContentInfoRecords() {
    return contentInfoRecords;
  }

  public List<ContentChunkRecord> getContentChunkRecords() {
    return contentChunkRecords;
  }

  public void testPrintlnAll() {
    signatureData.testPrintlnAll();
    System.out.println();
    header.testPrintlnAll();
    System.out.println();
    for (ContentInfoRecord cir : contentInfoRecords) {
      cir.testPrintlnAll();
    }
  }
}
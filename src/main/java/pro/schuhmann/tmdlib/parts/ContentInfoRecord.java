package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

public class ContentInfoRecord {
  private short contentIndexOffset;
  private short contentCommandCount;
  private HexString nextContentRecordsHash;

  public ContentInfoRecord(TmdFileReader tmdFile, final int contentInfoOffsetInFile) throws IOException{
    this.contentIndexOffset     = tmdFile.getShort(contentInfoOffsetInFile);
    this.contentCommandCount    = tmdFile.getShort(contentInfoOffsetInFile + 0x2);
    this.nextContentRecordsHash = tmdFile.getHexString(contentInfoOffsetInFile + 0x4, 0x20);
  }

  public short getContentIndexOffset() {
    return contentIndexOffset;
  }

  public short getContentCommandCount() {
    return contentCommandCount;
  }

  public HexString getNextContentRecordsHash() {
    return nextContentRecordsHash;
  }

  public void testPrintlnAll() {
    System.out.println("----- Content Info Record -----");
    System.out.println("Content Index Offset: " + contentIndexOffset);
    System.out.println("Content Command Count: " + contentCommandCount);
    System.out.println("Next Content Records Hash: " + nextContentRecordsHash);
  }
}

package pro.schuhmann.tmdlib.parts;

import pro.schuhmann.tmdlib.HexString;
import pro.schuhmann.tmdlib.TmdFileReader;

import java.io.IOException;

public class ContentChunkRecord {

  private int contentId;
  private short contentIndex; //TODO: Create enum for content index
  private short contentType;  //TODO: Create enum (+ set?) for content type
  private long contentSize;
  private HexString sha256hash;

  public ContentChunkRecord(TmdFileReader tmdFile, int contentChunkOffsetInFile) throws IOException {
    this.contentId    = tmdFile.getInt(contentChunkOffsetInFile);
    this.contentIndex = tmdFile.getShort(contentChunkOffsetInFile + 0x4);
    this.contentType  = tmdFile.getShort(contentChunkOffsetInFile + 0x6);
    this.contentSize  = tmdFile.getLong(contentChunkOffsetInFile + 0x8);
    this.sha256hash   = tmdFile.getHexString(contentChunkOffsetInFile + 0x10, 0x20);
  }

  public int getContentId() {
    return contentId;
  }

  public short getContentIndex() {
    return contentIndex;
  }

  public short getContentType() {
    return contentType;
  }

  public long getContentSize() {
    return contentSize;
  }

  public HexString getSha256hash() {
    return sha256hash;
  }

  public void testPrintlnAll() {
    System.out.println("----- Content Chunk Record -----");
    System.out.println("Content ID: " + contentId);
    System.out.println("Content Index: " + contentIndex);
    System.out.println("Content Type: " + contentType);
    System.out.println("Content Size: " + contentSize);
    System.out.println("SHA256 Hash: " + sha256hash);
  }
}
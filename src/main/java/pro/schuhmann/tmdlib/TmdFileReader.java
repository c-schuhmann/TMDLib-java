package pro.schuhmann.tmdlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Read different data from a TMD file, like integers, bytes or Strings.
 */
public class TmdFileReader {

  private RandomAccessFile tmdFile;

  /**
   * Create a new TmdFileReader.
   *
   * @param f A {@link File} pointing to a TMD file.
   * @throws FileNotFoundException TMD file couldn't be found.
   */
  TmdFileReader(File f) throws FileNotFoundException {
    this.tmdFile = new RandomAccessFile(f, "r");
  }

  /**
   * Read a byte from the TMD file.
   *
   * @param index The location of the byte in the file.
   * @return A byte.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public byte getByte(int index) throws IOException {
    tmdFile.seek(index);
    return tmdFile.readByte();
  }

  /**
   * Read a short from the TMD file.
   *
   * @param index The location of the short in the file.
   * @return A short.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public short getShort(int index) throws IOException {
    tmdFile.seek(index);
    return tmdFile.readShort();
  }

  /**
   * Read an int from the TMD file.
   *
   * @param index The location of the int in the file.
   * @return An int.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public int getInt(int index) throws IOException {
    tmdFile.seek(index);
    return tmdFile.readInt();
  }

  /**
   * Read a long from the TMD file.
   *
   * @param index The location of the long in the file.
   * @return A long.
   * @throws IOException An error occurred while reading the TMD file.
   */
  public long getLong(int index) throws IOException {
    tmdFile.seek(index);
    return tmdFile.readLong();
  }

  /**
   * Read a hexadecimal String from a file.
   * Partly stolen from: <a href="https://stackoverflow.com/a/9855338">Stackoverflow</a> & modified.
   *
   * @param index  The start of the hexadecimal String in the file. (eg. offset 0x140)
   * @param length The length of the hexadecimal String in the file. (eg. 40 bytes)
   * @return A HexString in format "0x0123456789ABCDEF".
   */
  public HexString getHexString(long index, int length) throws IOException{
    byte[] bytes = readByteArray(index, length);
    if (bytes.length == 0)
      throw new IllegalArgumentException();

    final char[] hexArray = "0123456789ABCDEF".toCharArray();
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new HexString(new String(hexChars));
  }

  /**
   * Read a String from a File.
   *
   * @param index  The start of the String in the file. (eg. offset 0x140)
   * @param length The length of the String in the file. (eg. 40 bytes)
   * @return A string.
   */
  public String getString(long index, int length) throws IOException {
    byte[] bytes = readByteArray(index, length);
    if (bytes.length == 0)
      throw new IllegalArgumentException();

    String newString = "";
    for (byte b : bytes)
    {
      newString += (char) (b & 0xFF);
    }

    return newString.trim();
  }

  private byte[] readByteArray(long index, int length) throws IOException {
    byte[] bytes = new byte[length];
    tmdFile.seek(index);
    tmdFile.read(bytes, 0, length);
    return bytes;
  }
}

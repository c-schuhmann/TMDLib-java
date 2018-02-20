package pro.schuhmann.tmdlib.enums;

import java.util.HashSet;

/**
 * An enumeration of the different content type flags.
 */
public enum ContentTypeFlag {
  ENCRYPTED,
  DISC,
  CFM,
  OPTIONAL,
  SHARED;

  /**
   * Get the content type flags by value.
   *
   * @param value Content type flasgs value as short.
   * @return A {@link HashSet} containing content type flags.
   */
  public static HashSet<ContentTypeFlag> getbyValue(short value) {
    HashSet<ContentTypeFlag> enumSet = new HashSet<>();

    if ((value & 0x8000) != 0) {
      enumSet.add(SHARED);
    }
    if ((value & 0x4000) != 0) {
      enumSet.add(OPTIONAL);
    }
    if ((value & 4) != 0) {
      enumSet.add(CFM);
    }
    if ((value & 2) != 0) {
      enumSet.add(DISC);
    }
    if ((value & 1) != 0) {
      enumSet.add(ENCRYPTED);
    }

    return enumSet;
  }
}

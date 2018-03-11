package pro.schuhmann.tmdlib.enums;

import java.util.HashSet;

/**
 * An enumeration of all possible title types.
 */
public enum TitleType {
  DEFAULT,
  UNKNOWN_0X4,
  DATA,
  UNKNOWN_0X10,
  MAYBE_WFS,
  UNKNOWN_CT;

  public static HashSet<TitleType> getbyValue(int value) {
    HashSet<TitleType> enumSet = new HashSet<>();

    if ((value & 0x1) != 0) {
      enumSet.add(DEFAULT);
    }
    if ((value & 0x4) != 0) {
      enumSet.add(UNKNOWN_0X4);
    }
    if ((value & 0x8) != 0) {
      enumSet.add(DATA);
    }
    if ((value & 0x10) != 0) {
      enumSet.add(UNKNOWN_0X10);
    }
    if ((value & 0x20) != 0) {
      enumSet.add(MAYBE_WFS);
    }
    if ((value & 0x40) != 0) {
      enumSet.add(UNKNOWN_CT);
    }

    return enumSet;
  }
}

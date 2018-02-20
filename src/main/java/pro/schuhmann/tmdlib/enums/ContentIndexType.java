package pro.schuhmann.tmdlib.enums;

/**
 * An enumeration of the different content index types.
 */
public enum ContentIndexType {
  MAIN_CONTENT,
  HOME_MENU_MANUAL,
  DLP_CHILD_CONTAINER;

  /**
   * Get the content index type by it's type value.
   *
   * @param value Content index type value as int.
   * @return The content index type or {@code null}.
   */
  public static ContentIndexType getByValue(short value) {
    switch (value) {
      case 0x0: return MAIN_CONTENT;
      case 0x1: return HOME_MENU_MANUAL;
      case 0x2: return DLP_CHILD_CONTAINER;
    }

    return null;
  }
}

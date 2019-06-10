package com.bandwidth.engineering.correlator.enums.cache;

import com.bandwidth.engineering.correlator.common.enums.ValuedEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Status as reported in tables from Iconectiv (e.g. the LERG, LCADS).
 */
public enum IconectivStatus implements ValuedEnum
{
  /** Active for entire time period */
  ACTIVE_NOW(null, 0),
  /** New (Established) on the specified date. */
  NEW("E", 3),
  /** Delete on the specified date. */
  DELETE("D", 1),
  /** Change (Merge) on the specified date. */
  CHANGE("M", 2);

  private static final Map<String, IconectivStatus> valueMap = new HashMap<>(values().length * 2);

  static
  {
    for (final IconectivStatus iconectivStatus : IconectivStatus.values())
    {
      valueMap.put(iconectivStatus.value, iconectivStatus);
    }
  }

  private final int sortOrder;
  private final String value;


  IconectivStatus(final String value, final int sortOrder)
  {
    this.value = value;
    this.sortOrder = sortOrder;
  }


  /**
   * Retrieve the proper enumerated value for the status as reported in tables from Iconectiv.
   *
   * @param character
   * 		Character stored (or null).
   *
   * @return Appropriate enumerated value or null if no match.
   */
  public static IconectivStatus from(final String character)
  {
    return character == null ? ACTIVE_NOW : valueMap.get(character);
  }


  /**
   * Retrieve the character that Iconectiv uses to denote this status.
   *
   * @return Character for this status.
   */
  @Override
  public String getValue()
  {
    return value;
  }


  /**
   * Return the order in which these statuses should be respected when on the same date.
   *
   * @return Sort order.
   */
  public int getSortOrder() { return sortOrder; }
}

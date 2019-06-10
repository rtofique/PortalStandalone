package com.bandwidth.engineering.correlator.common.enums;

/**
 * Interface for an enumerated value that returns a string value suitable for display purposes, for storage purposes, or
 * to match some external input.
 */
public interface ValuedEnum
{
  /**
   * Retrieve a string value as the standard text representation of this value.
   *
   * @return String representation of this enumerated value.
   */
  String getValue();

}

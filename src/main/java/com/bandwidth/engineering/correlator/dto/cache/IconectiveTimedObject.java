package com.bandwidth.engineering.correlator.dto.cache;

import com.bandwidth.engineering.correlator.enums.cache.IconectivStatus;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Abstract for an object that may change over time
 */
abstract class IconectivTimedObject implements Serializable, Comparable<IconectivTimedObject>
{
  private final IconectivStatus recordStatus;
  private final LocalDateTime effectiveDate;


  IconectivTimedObject(final IconectivStatus recordStatus, final LocalDateTime effectiveDate)
  {
    this.recordStatus = recordStatus;
    this.effectiveDate = effectiveDate;
  }


  @Override
  public int compareTo(@Nonnull final IconectivTimedObject t)
  {
    if (effectiveDate == null || t.effectiveDate == null)
      return 0;

    if (Objects.equals(effectiveDate, t.effectiveDate))
      return Integer.compare(recordStatus.getSortOrder(), t.recordStatus.getSortOrder());

    return effectiveDate.compareTo(t.effectiveDate);
  }


  /**
   * Retrieve the status of this record.
   *
   * @return Null if the record is active throughout the period. E=establish (new). M=merge (changed), D=delete.
   */
  public IconectivStatus getRecordStatus() { return recordStatus; }


  /**
   * Retrieve the date on which the status takes effect if the status is non-null.
   *
   * @return Effective date of the record.
   */
  @SuppressWarnings("TypeMayBeWeakened")
  public LocalDateTime getEffectiveDate() { return effectiveDate; }


  @Override
  public int hashCode()
  {
    return effectiveDate.hashCode();
  }


  @Override
  public boolean equals(final Object obj)
  {
    if (obj == null || obj.getClass() != getClass())
      return false;

    final IconectivTimedObject o = (IconectivTimedObject) obj;

    return effectiveDate == null && o.effectiveDate == null || !(effectiveDate == null || o.effectiveDate == null) && Objects.equals(effectiveDate, o.effectiveDate);

  }
}

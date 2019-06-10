package com.bandwidth.engineering.correlator.dto.cache;

import com.bandwidth.engineering.correlator.enums.cache.IconectivStatus;

import java.time.LocalDateTime;

/**
 * Representation of information about a rate center, with the NPA, NXX, and block ID combined into a single key for
 * easy access. A NANPA number should either match in its first seven digits or with the first six and a "A" for the
 * seventh, signifying the default value if no more specific value is specified.
 */
public class RateCenter extends IconectivTimedObject
{
  private final String lata;
  private final String ocn;
  private final String aocn;
  private final String rateCenter;
  private final String state;
  private final String ocnOverall;


  public RateCenter(final String lata, final String ocn, final String aocn, final String rateCenter, final String state, final String ocnOverall, final IconectivStatus recordStatus, final LocalDateTime effectiveDate)
  {
    super(recordStatus, effectiveDate);
    this.lata = lata;
    this.ocn = ocn;
    this.aocn = aocn;
    this.rateCenter = rateCenter;
    this.state = state;
    this.ocnOverall = ocnOverall;
  }


  /**
   * LATA associated with the key.
   *
   * @return Local access and transport area.
   */
  public String getLata() { return lata; }


  /**
   * OCN associated with the key.
   *
   * @return Operating company number.
   */
  public String getOcn() { return ocn; }


  /**
   * AOCN associated with the key.
   *
   * @return Administrative operating carrier number.
   */
  public String getAocn() { return aocn; }


  /**
   * Rate center abbreviation associated with the key.
   *
   * @return Rate center.
   */
  public String getRateCenter() { return rateCenter; }


  /**
   * State (or province or territory) associated with the rate center.
   *
   * @return State, province, or territory.
   */
  public String getState() { return state; }


  /**
   * Overall OCN for the associated OCN.
   *
   * @return Overall operating company number.
   */
  public String getOcnOverall() { return ocnOverall; }
}

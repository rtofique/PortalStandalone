package main.java.rest;

public class NumberRecord {

  private final long id;

  private final String block;
  private final String rateCenter;
  private final String region;

  public NumberRecord(long id, String block, String rateCenter, String region)
  {
    this.id = id;
    this.block = block;
    this.rateCenter = rateCenter;
    this.region = region;
  }


  public long getId() {
    return id;
  }

  public String getBlock() {
    return block;
  }

  public String getRateCenter() {
    return rateCenter;
  }

  public String getRegion() {
    return region;
  }



}

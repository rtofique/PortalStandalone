package number_lookup.number_records.number_lookup.number_records;

/**
 * Abstract class for the standard number record queries
 */

public abstract class NumberRecord implements IQuery {

  private final String query;


  public NumberRecord(String query)
  {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  /**
   * This value is defined in the record subclasses and is used to pass information to the end point on how to deserialize the record
   * @return
   */
  public abstract String getJsonType();

}

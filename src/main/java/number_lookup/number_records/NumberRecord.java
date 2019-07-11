package number_lookup.number_records;

/**
 * Abstract class for the standard number record queries
 */

public abstract class NumberRecord implements IQuery {

  private final String query;
  private final String id;

  public NumberRecord(String query, String id)
  {
    this.id = id;
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public String getId() { return id; }

  /**s
   * This value is defined in the record subclasses and is used to pass information to the end point on how to deserialize the record
   * @return
   */
  public abstract String getJsonType();

}

package parsing;

public class NumberRecord implements IQuery {

  private final String query;

  public NumberRecord(String query)
  {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }
}

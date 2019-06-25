package number_lookup.query_parsing;

import org.springframework.http.HttpStatus;

/**
 * A wrapper class which will be returned to QueryController to help it determine what response code to return. I wanted to keep the number_lookup controller as simple and devoid of program logic as possible.
 * The wrapper helps ensure that by keeping JSON output formation out of its hands and the controller will simply return the JSON string that it receives from this wrapper and use the httpResponse field
 * to determine th response code.
 */



public class QueryResultWrapper {

  final private String resultBody;
  final private HttpStatus httpResponse;

  public QueryResultWrapper(String resultBody, HttpStatus httpResponse)
  {
    this.resultBody = resultBody;
    this.httpResponse = httpResponse;
  }

  public String getResultBody() {
    return resultBody;
  }

  public HttpStatus getHttpResponse() {
    return httpResponse;
  }
}

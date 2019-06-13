package parsing;

import org.springframework.http.HttpStatus;

/**
 * A wrapper class which will be returned to QueryController to help it determine what response code to return. I wanted to keep the rest controller as simple and devoid of program logic as possible.
 * The wrapper helps ensure that by keeping JSON output formation out of its hands and the controller will simply return the JSON string that it receives from this wrapper and use the httpResponse field
 * to determine th response code.
 */



public class QueryResultWrapper {

  final private String jsonResult;
  final private HttpStatus httpResponse;

  public QueryResultWrapper(String jsonResult, HttpStatus httpResponse)
  {
    this.jsonResult = jsonResult;
    this.httpResponse = httpResponse;
  }

  public String getJsonResult() {
    return jsonResult;
  }

  public HttpStatus getHttpResponse() {
    return httpResponse;
  }
}

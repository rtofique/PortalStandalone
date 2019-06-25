package number_lookup.query_parsing;

/**
 * Exception for invalid query entered by the user
 */

public class InvalidInputException extends Exception {


    public InvalidInputException(String input)
    {
      super(input);
    }
}

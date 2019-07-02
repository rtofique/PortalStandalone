package number_lookup;

/**
 * Main entry point for queries made to system
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.swagger.annotations.ApiOperation;
import number_lookup.query_parsing.QueryResultWrapper;
import number_lookup.query_parsing.QueryProcessor;

/**
 * REST API class which configures routing paths and makes external calls to handle the queries
 */

//return the right error codes
    //look for 200 error code that classifies for partial request success
    //if cant find any one- then complete failure

@RestController
@Component
public class NumberLookupController {


    @Autowired
    QueryProcessor queryProcessor;



    private final String INVALID_INPUT_ERROR = "The given input is invalid. It needs to be in the format (XXXXXXX) or (XXXXXXA), where X = a number from 1-9";

    @ApiOperation(value = "Get all the data stored in Bandwidth's database for this number")
    @GetMapping(path="/number")
    public ResponseEntity<String> getQuery(@RequestParam(value="PhoneNumber") String userNumberQuery)
    {
        QueryResultWrapper wrapper = queryProcessor.queryInput(userNumberQuery);
        return new ResponseEntity<>(wrapper.getResultBody(), wrapper.getHttpResponse());
    }



}

package rest;

/**
 * Main entry point for queries made to system
 */

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.ignite.cache.query.ScanQuery;
import java.util.Iterator;

/**
 * REST API class which configures routing paths and makes external calls to handle the queries
 */


@RestController
@Component
public class QueryController {


    //Autowiring annotation works by simply looking for a type of object that has been created. Use @Qualifier to distinguish between different objects
    /*
      All external calls to handle the query requests will be made through the queryProcessor internal API
     */
    @Autowired
    QueryProcessor queryProcessor;


    private final String INVALID_INPUT_ERROR = "The given input is invalid. It needs to be in the format (XXXXXXX) or (XXXXXXA), where X = a number from 1-9";


    @GetMapping(path="/number")
    public String getQuery(@RequestParam(value="npa") String npa, @RequestParam(value="nxx") String nxx)
    {
        try
        {
            return queryProcessor.queryNumber(npa + nxx);
        }
        catch(InvalidInputException e)
        {
            return INVALID_INPUT_ERROR;
        }
    }

    @GetMapping(path="/numberpost")
    public String setQuery(@RequestParam(value="npa") String npa, @RequestParam(value="nxx") String nxx)
    {
      return "Does Not Support POST queries";
    }

}

package main.java.rest;

/**
 * Main entry point for queries made to system
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QueryController {

    @GetMapping(path="/number")
    public String query(@RequestParam(value="npa") String npa, @RequestParam(value="nxx") String nxx)
    {
      //do a lookup based on the npa and nxx provided
      return npa + "-" + nxx;
    }
}

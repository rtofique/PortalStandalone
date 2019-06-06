package main.java.rest;

/**
 * Main entry point for queries made to system
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;




@RestController
public class QueryController {


    private final String CACHE_NAME = "myCache";

    //only for testing on local, to be removed when working solely on REST
    IgniteClientManager igniteClient = null;

    @GetMapping(path="/number")
    public String getQuery(@RequestParam(value="npa") String npa, @RequestParam(value="nxx") String nxx)
    {
      initializeClient();
      //do a lookup based on the npa and nxx provided
      return getFromCache(npa);
    }

    @GetMapping(path="/numberpost")
    public String setQuery(@RequestParam(value="npa") String npa, @RequestParam(value="nxx") String nxx)
    {
      initializeClient();
      putInCache(npa, nxx);
      return "successful";
    }

    private void putInCache(String npa, String nxx)
    {
      ClientCache<String, String> cache = igniteClient.getClient().getOrCreateCache(CACHE_NAME);
      cache.put(npa, nxx);
    }

    private String getFromCache(String npa)
    {
      ClientCache<String, String> cache = igniteClient.getClient().getOrCreateCache(CACHE_NAME);
      return cache.get(npa);
    }

    private void initializeClient()
    {
      if(igniteClient == null)
      {
        IgniteClientManager manager = new IgniteClientManager();
        this.igniteClient = manager;
      }
    }

}

package rest;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.time.LocalDateTime;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;

public class TestRest {

  static IgniteClient ignite;
  static int numRecords;

  private static String CACHE_NAME = "Test_Cache";

  private void populateCache()
  {
    ClientCache<String, RateCenter> cache = ignite.getOrCreateCache(CACHE_NAME);
    for(int i = 0; i < numRecords; i++)
    {
      RateCenter rc = new RateCenter("ABC" + i, "DFG" + i, "HIJ" + i, "LMN" + i, "OP" + i, "QR" + i, null,
          LocalDateTime.of(2019, 06, 23, 5, 30));

      String phoneNumber = "999999";
      cache.put(phoneNumber + i, rc);

    }
  }

  private void compareResults()
  {
    ClientCache<String, RateCenter> cache = ignite.cache(CACHE_NAME);
    for(int i = 0; i < numRecords; i++)
    {

    }
  }

}

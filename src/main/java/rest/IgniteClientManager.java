package rest;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.client.ClientCache;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * Creates a Thin igniteClient connectipn to an ignite server running on the address field
 *  Works as a Spring singleton
 */


@Configuration
public class IgniteClientManager
{

  //currently a place holder value that will be configured to actually read from an application file
  //query each address until you pass
  private final String address = "192.168.111.67:10800";


  /**
   *
   * @return
   * creates and gets an ignite client instance
   * the Bean annotation should be put on functions that returns objects so that they are put in Spring's Bean factory
   */
  @Bean
  public IgniteClient getIgniteInstance()
  {
    ClientConfiguration clientCfg = new ClientConfiguration().setAddresses(address);
    return Ignition.startClient(clientCfg);
  }

}

package number_lookup.number_records.number_lookup.query_parsing;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Creates a Thin igniteClient connection to an ignite server running on the address field
 *  Works as a Spring singleton
 */


@Configuration
public class IgniteClientManager
{

  //currently a place holder value that will be configured to actually read from an application file
  //query each address until you pass
  //@value is not being initialized right now due to the constructor not completing

  @Value("${cache.addresses}")
  private String address;

  private IgniteClient client = null;

  private static Logger logger = LoggerFactory.getLogger(IgniteClientManager.class);


  //do min work in constructor
  public IgniteClientManager()
  {}

  private void setupClient()
  {
    final ClientConfiguration clientCfg = new ClientConfiguration().setAddresses(address);
    client = Ignition.startClient(clientCfg);

  }

  public IgniteClient getClient()
  {
    logger.info("Ignite address: " + address + " ********************************");
    if(client == null)
    {
      setupClient();
    }
    return client;
  }

  /**
   *
   * @return
   * creates and gets an ignite client instance
   * the Bean annotation should be put on functions that returns objects so that they are put in Spring's Bean factory
   */
  @Bean
  public IgniteClientManager getIgniteManager()
  {
    return this;
  }

}
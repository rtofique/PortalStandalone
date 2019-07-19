package number_lookup.query_parsing;

import java.util.List;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientException;
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

  @Value("#{'${cache.addresses}'.split(',')}")
  private List<String> addresses;

  private IgniteClient client = null;

  private static Logger logger = LoggerFactory.getLogger(IgniteClientManager.class);


  public IgniteClientManager()
  {}

  private void setupClient()
  {
    final ClientConfiguration clientCfg = new ClientConfiguration().setAddresses(addresses.toArray(new String[addresses.size()]));

    client = Ignition.startClient(clientCfg);

  }

  public IgniteClient getClient()
  {
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

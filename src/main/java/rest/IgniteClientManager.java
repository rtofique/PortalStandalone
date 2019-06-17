package rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

  private IgniteClient client = null;

  public IgniteClientManager()
  {
    final ClientConfiguration clientCfg = new ClientConfiguration().setAddresses(address);
    initializeConnectionInSeparateThread(clientCfg);
  }

  private void initializeConnectionInSeparateThread(ClientConfiguration clientCfg)
  {
      Runnable r = new Runnable() {
      @Override
      public void run() {
        client = Ignition.startClient(clientCfg);
      }
    };

    ExecutorService executor = Executors.newCachedThreadPool();
    executor.submit(r);
  }

  public IgniteClient getClient() throws UninitializedClientException
  {
    if(client == null) throw new UninitializedClientException("Client has not been initialized yet");
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

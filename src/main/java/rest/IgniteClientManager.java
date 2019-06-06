package main.java.rest;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.client.ClientCache;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;





/**
 * Creates a Thin igniteClient connectipn to an ignite server running on the address field
 *
 */



public class IgniteClientManager
{

  //currently a place holder value that will be configured to actually read from a file
  private final String address = "127.0.0.1:11211";

  private IgniteClient client;


  public IgniteClientManager()
  {
    this.client = createInstance();
  }

  public IgniteClient createInstance()
  {
    ClientConfiguration clientCfg = new ClientConfiguration().setAddresses(address);
    return Ignition.startClient(clientCfg);
  }


  public IgniteClient getClient()
  {
    return client;
  }

}
